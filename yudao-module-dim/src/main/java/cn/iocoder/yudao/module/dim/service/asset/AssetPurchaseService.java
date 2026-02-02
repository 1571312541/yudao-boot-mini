package cn.iocoder.yudao.module.dim.service.asset;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.AssetPurchaseAuditReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.AssetPurchasePageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.AssetPurchaseSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.asset.AssetPurchaseDO;
import cn.iocoder.yudao.module.dim.dal.dataobject.asset.AssetPurchaseItemDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 资产采购 Service 接口
 */
public interface AssetPurchaseService {

    /**
     * 创建资产采购单
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createAssetPurchase(@Valid AssetPurchaseSaveReqVO createReqVO);

    /**
     * 更新资产采购单
     *
     * @param updateReqVO 更新信息
     */
    void updateAssetPurchase(@Valid AssetPurchaseSaveReqVO updateReqVO);

    /**
     * 删除资产采购单
     *
     * @param id 编号
     */
    void deleteAssetPurchase(Long id);

    /**
     * 获得资产采购单
     *
     * @param id 编号
     * @return 资产采购单
     */
    AssetPurchaseDO getAssetPurchase(Long id);

    /**
     * 获得资产采购单分页
     *
     * @param pageReqVO 分页查询
     * @return 资产采购单分页
     */
    PageResult<AssetPurchaseDO> getAssetPurchasePage(AssetPurchasePageReqVO pageReqVO);

    /**
     * 获取采购单明细列表
     *
     * @param purchaseId 采购单ID
     * @return 明细列表
     */
    List<AssetPurchaseItemDO> getAssetPurchaseItemList(Long purchaseId);

    /**
     * 提交采购单审核
     *
     * @param id 采购单ID
     */
    void submitAssetPurchase(Long id);

    /**
     * 审核采购单
     *
     * @param auditReqVO 审核信息
     */
    void auditAssetPurchase(@Valid AssetPurchaseAuditReqVO auditReqVO);

    /**
     * 采购入库
     *
     * @param id 采购单ID
     */
    void warehouseAssetPurchase(Long id);

}
