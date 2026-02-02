package cn.iocoder.yudao.module.dim.service.inventory;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.dim.controller.admin.inventory.vo.InventoryItemImportVO;
import cn.iocoder.yudao.module.dim.controller.admin.inventory.vo.InventoryItemPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.inventory.vo.InventoryItemSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.inventory.InventoryItemDO;
import cn.iocoder.yudao.module.dim.dal.mysql.inventory.InventoryItemMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.dim.enums.ErrorCodeConstants.*;

/**
 * 库存物品 Service 实现类
 */
@Service
@Validated
public class InventoryItemServiceImpl implements InventoryItemService {

    @Resource
    private InventoryItemMapper inventoryItemMapper;

    @Override
    public Long createItem(InventoryItemSaveReqVO createReqVO) {
        // 校验编码唯一
        validateCodeUnique(null, createReqVO.getCode());
        // 插入
        InventoryItemDO item = BeanUtils.toBean(createReqVO, InventoryItemDO.class);
        if (item.getQuantity() == null) {
            item.setQuantity(BigDecimal.ZERO);
        }
        if (item.getLockedQuantity() == null) {
            item.setLockedQuantity(BigDecimal.ZERO);
        }
        if (item.getStatus() == null) {
            item.setStatus(0);
        }
        inventoryItemMapper.insert(item);
        return item.getId();
    }

    @Override
    public void updateItem(InventoryItemSaveReqVO updateReqVO) {
        // 校验存在
        validateItemExists(updateReqVO.getId());
        // 校验编码唯一
        validateCodeUnique(updateReqVO.getId(), updateReqVO.getCode());
        // 更新
        InventoryItemDO updateObj = BeanUtils.toBean(updateReqVO, InventoryItemDO.class);
        inventoryItemMapper.updateById(updateObj);
    }

    @Override
    public void deleteItem(Long id) {
        // 校验存在
        InventoryItemDO item = inventoryItemMapper.selectById(id);
        if (item == null) {
            throw exception(INVENTORY_ITEM_NOT_EXISTS);
        }
        // 校验库存为0才能删除
        if (item.getQuantity() != null && item.getQuantity().compareTo(BigDecimal.ZERO) > 0) {
            throw exception(INVENTORY_ITEM_HAS_STOCK);
        }
        // 删除
        inventoryItemMapper.deleteById(id);
    }

    private void validateItemExists(Long id) {
        if (inventoryItemMapper.selectById(id) == null) {
            throw exception(INVENTORY_ITEM_NOT_EXISTS);
        }
    }

    private void validateCodeUnique(Long id, String code) {
        InventoryItemDO item = inventoryItemMapper.selectByCode(code);
        if (item != null && !item.getId().equals(id)) {
            throw exception(INVENTORY_ITEM_CODE_DUPLICATE);
        }
    }

    @Override
    public InventoryItemDO getItem(Long id) {
        return inventoryItemMapper.selectById(id);
    }

    @Override
    public PageResult<InventoryItemDO> getItemPage(InventoryItemPageReqVO pageReqVO) {
        return inventoryItemMapper.selectPage(pageReqVO);
    }

    @Override
    public List<InventoryItemDO> getItemListByType(Integer type) {
        return inventoryItemMapper.selectListByType(type);
    }

    @Override
    public void updateItemQuantity(Long id, BigDecimal deltaQuantity) {
        InventoryItemDO item = inventoryItemMapper.selectById(id);
        if (item == null) {
            throw exception(INVENTORY_ITEM_NOT_EXISTS);
        }
        BigDecimal newQuantity = item.getQuantity().add(deltaQuantity);
        if (newQuantity.compareTo(BigDecimal.ZERO) < 0) {
            throw exception(INVENTORY_STOCK_INSUFFICIENT);
        }
        // 更新库存和库存金额
        InventoryItemDO updateObj = new InventoryItemDO();
        updateObj.setId(id);
        updateObj.setQuantity(newQuantity);
        if (item.getUnitPrice() != null) {
            updateObj.setTotalAmount(newQuantity.multiply(item.getUnitPrice()));
        }
        inventoryItemMapper.updateById(updateObj);
    }

    @Override
    public List<InventoryItemDO> getAlertItems() {
        return inventoryItemMapper.selectAlertItems();
    }

    @Override
    public ItemImportRespVO importItems(List<InventoryItemImportVO> importList, boolean updateSupport) {
        ItemImportRespVO result = new ItemImportRespVO();

        if (CollUtil.isEmpty(importList)) {
            return result;
        }

        for (int i = 0; i < importList.size(); i++) {
            InventoryItemImportVO importVO = importList.get(i);
            int rowNum = i + 2; // Excel 行号从2开始（第1行是表头）
            try {
                // 校验必填字段
                if (StrUtil.isBlank(importVO.getCode())) {
                    result.failMessages.add("第 " + rowNum + " 行：物品编码不能为空");
                    continue;
                }
                if (StrUtil.isBlank(importVO.getName())) {
                    result.failMessages.add("第 " + rowNum + " 行：物品名称不能为空");
                    continue;
                }
                if (importVO.getType() == null) {
                    result.failMessages.add("第 " + rowNum + " 行：物品类型不能为空");
                    continue;
                }
                if (StrUtil.isBlank(importVO.getUnit())) {
                    result.failMessages.add("第 " + rowNum + " 行：计量单位不能为空");
                    continue;
                }

                // 根据编码查找是否已存在
                InventoryItemDO existItem = inventoryItemMapper.selectByCode(importVO.getCode());

                if (existItem == null) {
                    // 新增
                    InventoryItemDO item = BeanUtils.toBean(importVO, InventoryItemDO.class);
                    item.setQuantity(BigDecimal.ZERO);
                    item.setLockedQuantity(BigDecimal.ZERO);
                    item.setTotalAmount(BigDecimal.ZERO);
                    item.setStatus(0);
                    inventoryItemMapper.insert(item);
                    result.createCount++;
                } else if (updateSupport) {
                    // 更新
                    InventoryItemDO updateObj = BeanUtils.toBean(importVO, InventoryItemDO.class);
                    updateObj.setId(existItem.getId());
                    inventoryItemMapper.updateById(updateObj);
                    result.updateCount++;
                } else {
                    result.failMessages.add("第 " + rowNum + " 行：物品编码已存在");
                }
            } catch (Exception e) {
                result.failMessages.add("第 " + rowNum + " 行：导入失败 - " + e.getMessage());
            }
        }

        return result;
    }

}
