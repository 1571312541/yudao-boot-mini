package cn.iocoder.yudao.module.dim.service.asset;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.AssetPurchaseAuditReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.AssetPurchaseItemSaveReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.AssetPurchasePageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.AssetPurchaseSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.asset.AssetDO;
import cn.iocoder.yudao.module.dim.dal.dataobject.asset.AssetPurchaseDO;
import cn.iocoder.yudao.module.dim.dal.dataobject.asset.AssetPurchaseItemDO;
import cn.iocoder.yudao.module.dim.dal.mysql.asset.AssetMapper;
import cn.iocoder.yudao.module.dim.dal.mysql.asset.AssetPurchaseItemMapper;
import cn.iocoder.yudao.module.dim.dal.mysql.asset.AssetPurchaseMapper;
import cn.iocoder.yudao.module.dim.enums.AssetPurchaseStatusEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.dim.enums.ErrorCodeConstants.*;

/**
 * 资产采购 Service 实现类
 */
@Service
@Validated
public class AssetPurchaseServiceImpl implements AssetPurchaseService {

    /** 资产状态：启用 */
    private static final Integer ASSET_STATUS_ENABLE = 1;

    @Resource
    private AssetPurchaseMapper assetPurchaseMapper;

    @Resource
    private AssetPurchaseItemMapper assetPurchaseItemMapper;

    @Resource
    private AssetMapper assetMapper;

    @Resource
    private AssetLogService assetLogService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createAssetPurchase(AssetPurchaseSaveReqVO createReqVO) {
        // 生成采购单号
        AssetPurchaseDO purchase = BeanUtils.toBean(createReqVO, AssetPurchaseDO.class);
        if (StrUtil.isBlank(purchase.getCode())) {
            purchase.setCode(generatePurchaseCode());
        }
        purchase.setStatus(AssetPurchaseStatusEnum.DRAFT.getValue());

        // 插入采购单
        assetPurchaseMapper.insert(purchase);

        // 批量插入采购明细
        insertPurchaseItems(purchase.getId(), createReqVO.getItems());

        return purchase.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAssetPurchase(AssetPurchaseSaveReqVO updateReqVO) {
        // 校验存在
        AssetPurchaseDO oldPurchase = validateAssetPurchaseExists(updateReqVO.getId());

        // 只有待提交状态才能修改
        if (!AssetPurchaseStatusEnum.DRAFT.getValue().equals(oldPurchase.getStatus())) {
            throw exception(ASSET_PURCHASE_ALREADY_SUBMITTED);
        }

        // 更新采购单
        AssetPurchaseDO updateObj = BeanUtils.toBean(updateReqVO, AssetPurchaseDO.class);
        assetPurchaseMapper.updateById(updateObj);

        // 删除旧明细，插入新明细
        assetPurchaseItemMapper.deleteByPurchaseId(updateReqVO.getId());
        insertPurchaseItems(updateReqVO.getId(), updateReqVO.getItems());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAssetPurchase(Long id) {
        // 校验存在
        AssetPurchaseDO purchase = validateAssetPurchaseExists(id);

        // 只有待提交或已拒绝状态才能删除
        Integer status = purchase.getStatus();
        if (!AssetPurchaseStatusEnum.DRAFT.getValue().equals(status)
                && !AssetPurchaseStatusEnum.REJECTED.getValue().equals(status)) {
            throw exception(ASSET_PURCHASE_STATUS_ERROR);
        }

        // 删除采购单和明细
        assetPurchaseMapper.deleteById(id);
        assetPurchaseItemMapper.deleteByPurchaseId(id);
    }

    @Override
    public AssetPurchaseDO getAssetPurchase(Long id) {
        return assetPurchaseMapper.selectById(id);
    }

    @Override
    public PageResult<AssetPurchaseDO> getAssetPurchasePage(AssetPurchasePageReqVO pageReqVO) {
        return assetPurchaseMapper.selectPage(pageReqVO);
    }

    @Override
    public List<AssetPurchaseItemDO> getAssetPurchaseItemList(Long purchaseId) {
        return assetPurchaseItemMapper.selectListByPurchaseId(purchaseId);
    }

    @Override
    public void submitAssetPurchase(Long id) {
        // 校验存在
        AssetPurchaseDO purchase = validateAssetPurchaseExists(id);

        // 只有待提交状态才能提交
        if (!AssetPurchaseStatusEnum.DRAFT.getValue().equals(purchase.getStatus())) {
            throw exception(ASSET_PURCHASE_STATUS_ERROR);
        }

        // 更新状态为待审核
        AssetPurchaseDO updateObj = new AssetPurchaseDO();
        updateObj.setId(id);
        updateObj.setStatus(AssetPurchaseStatusEnum.PENDING.getValue());
        updateObj.setApplyTime(LocalDateTime.now());
        updateObj.setApplicantId(SecurityFrameworkUtils.getLoginUserId());
        updateObj.setApplicantName(SecurityFrameworkUtils.getLoginUserNickname());
        assetPurchaseMapper.updateById(updateObj);
    }

    @Override
    public void auditAssetPurchase(AssetPurchaseAuditReqVO auditReqVO) {
        // 校验存在
        AssetPurchaseDO purchase = validateAssetPurchaseExists(auditReqVO.getId());

        // 只有待审核状态才能审核
        if (!AssetPurchaseStatusEnum.PENDING.getValue().equals(purchase.getStatus())) {
            throw exception(ASSET_PURCHASE_ALREADY_AUDITED);
        }

        // 更新审核信息
        AssetPurchaseDO updateObj = new AssetPurchaseDO();
        updateObj.setId(auditReqVO.getId());
        updateObj.setStatus(auditReqVO.getApproved()
                ? AssetPurchaseStatusEnum.APPROVED.getValue()
                : AssetPurchaseStatusEnum.REJECTED.getValue());
        updateObj.setAuditTime(LocalDateTime.now());
        updateObj.setAuditorId(SecurityFrameworkUtils.getLoginUserId());
        updateObj.setAuditorName(SecurityFrameworkUtils.getLoginUserNickname());
        updateObj.setAuditRemark(auditReqVO.getRemark());
        assetPurchaseMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void warehouseAssetPurchase(Long id) {
        // 校验存在
        AssetPurchaseDO purchase = validateAssetPurchaseExists(id);

        // 只有已通过状态才能入库
        if (!AssetPurchaseStatusEnum.APPROVED.getValue().equals(purchase.getStatus())) {
            throw exception(ASSET_PURCHASE_STATUS_ERROR);
        }

        // 获取采购明细
        List<AssetPurchaseItemDO> items = assetPurchaseItemMapper.selectListByPurchaseId(id);
        if (CollUtil.isEmpty(items)) {
            throw exception(ASSET_PURCHASE_ITEM_NOT_EXISTS);
        }

        // 逐项入库
        for (AssetPurchaseItemDO item : items) {
            if (Boolean.TRUE.equals(item.getWarehoused())) {
                continue; // 已入库的跳过
            }

            // 创建资产
            AssetDO asset = new AssetDO();
            asset.setName(item.getAssetName());
            asset.setCategoryId(item.getCategoryId());
            asset.setMeasurementUnit(item.getMeasurementUnit());
            asset.setInventory(item.getQuantity());
            asset.setPrice(item.getUnitPrice());
            asset.setPurchaseDate(LocalDateTime.now().toLocalDate());
            asset.setStatus(ASSET_STATUS_ENABLE);
            assetMapper.insert(asset);

            // 更新明细关联资产ID
            AssetPurchaseItemDO updateItem = new AssetPurchaseItemDO();
            updateItem.setId(item.getId());
            updateItem.setAssetId(asset.getId());
            updateItem.setWarehoused(true);
            assetPurchaseItemMapper.updateById(updateItem);

            // 记录入库日志
            assetLogService.createAssetLog(asset.getId(), 0, item.getQuantity(),
                    "采购入库，采购单号：" + purchase.getCode());
        }

        // 更新采购单状态为已入库
        AssetPurchaseDO updateObj = new AssetPurchaseDO();
        updateObj.setId(id);
        updateObj.setStatus(AssetPurchaseStatusEnum.WAREHOUSED.getValue());
        assetPurchaseMapper.updateById(updateObj);
    }

    /**
     * 批量插入采购明细
     */
    private void insertPurchaseItems(Long purchaseId, List<AssetPurchaseItemSaveReqVO> itemVOs) {
        if (CollUtil.isEmpty(itemVOs)) {
            return;
        }
        List<AssetPurchaseItemDO> items = new ArrayList<>(itemVOs.size());
        for (AssetPurchaseItemSaveReqVO itemVO : itemVOs) {
            AssetPurchaseItemDO item = BeanUtils.toBean(itemVO, AssetPurchaseItemDO.class);
            item.setId(null);
            item.setPurchaseId(purchaseId);
            item.setWarehoused(false);
            items.add(item);
        }
        assetPurchaseItemMapper.insertBatch(items);
    }

    /**
     * 校验采购单是否存在
     */
    private AssetPurchaseDO validateAssetPurchaseExists(Long id) {
        AssetPurchaseDO purchase = assetPurchaseMapper.selectById(id);
        if (purchase == null) {
            throw exception(ASSET_PURCHASE_NOT_EXISTS);
        }
        return purchase;
    }

    /**
     * 生成采购单号
     */
    private String generatePurchaseCode() {
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        // 简单实现：日期 + 时间戳后4位
        String suffix = String.format("%04d", System.currentTimeMillis() % 10000);
        return "PO" + dateStr + suffix;
    }

}
