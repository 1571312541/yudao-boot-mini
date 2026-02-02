package cn.iocoder.yudao.module.dim.service.inventory;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.dim.controller.admin.inventory.vo.InventoryItemImportVO;
import cn.iocoder.yudao.module.dim.controller.admin.inventory.vo.InventoryItemPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.inventory.vo.InventoryItemSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.inventory.InventoryItemDO;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 库存物品 Service 接口
 */
public interface InventoryItemService {

    /**
     * 创建物品
     */
    Long createItem(@Valid InventoryItemSaveReqVO createReqVO);

    /**
     * 更新物品
     */
    void updateItem(@Valid InventoryItemSaveReqVO updateReqVO);

    /**
     * 删除物品
     */
    void deleteItem(Long id);

    /**
     * 获得物品
     */
    InventoryItemDO getItem(Long id);

    /**
     * 获得物品分页
     */
    PageResult<InventoryItemDO> getItemPage(InventoryItemPageReqVO pageReqVO);

    /**
     * 根据类型获得物品列表
     */
    List<InventoryItemDO> getItemListByType(Integer type);

    /**
     * 更新物品库存（内部使用）
     */
    void updateItemQuantity(Long id, BigDecimal deltaQuantity);

    /**
     * 获取库存预警物品列表
     */
    List<InventoryItemDO> getAlertItems();

    /**
     * 批量导入物品
     *
     * @param importList 导入列表
     * @param updateSupport 是否更新已存在的物品
     * @return 导入结果
     */
    ItemImportRespVO importItems(List<InventoryItemImportVO> importList, boolean updateSupport);

    /**
     * 导入结果 VO
     */
    class ItemImportRespVO {
        public int createCount;
        public int updateCount;
        public List<String> failMessages = new ArrayList<>();
    }

}
