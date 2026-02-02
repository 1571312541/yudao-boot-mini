package cn.iocoder.yudao.module.dim.dal.mysql.inventory;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.dim.controller.admin.inventory.vo.InventoryItemPageReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.inventory.InventoryItemDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 库存物品 Mapper
 */
@Mapper
public interface InventoryItemMapper extends BaseMapperX<InventoryItemDO> {

    default PageResult<InventoryItemDO> selectPage(InventoryItemPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<InventoryItemDO>()
                .likeIfPresent(InventoryItemDO::getCode, reqVO.getCode())
                .likeIfPresent(InventoryItemDO::getName, reqVO.getName())
                .eqIfPresent(InventoryItemDO::getType, reqVO.getType())
                .eqIfPresent(InventoryItemDO::getCategoryId, reqVO.getCategoryId())
                .eqIfPresent(InventoryItemDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(InventoryItemDO::getStatus, reqVO.getStatus())
                .orderByDesc(InventoryItemDO::getId));
    }

    default List<InventoryItemDO> selectListByCategoryId(Long categoryId) {
        return selectList(new LambdaQueryWrapperX<InventoryItemDO>()
                .eq(InventoryItemDO::getCategoryId, categoryId));
    }

    default List<InventoryItemDO> selectListByType(Integer type) {
        return selectList(new LambdaQueryWrapperX<InventoryItemDO>()
                .eq(InventoryItemDO::getType, type)
                .eq(InventoryItemDO::getStatus, 0));
    }

    default InventoryItemDO selectByCode(String code) {
        return selectOne(new LambdaQueryWrapperX<InventoryItemDO>()
                .eq(InventoryItemDO::getCode, code));
    }

    /**
     * 查询库存预警物品（库存量低于最小值或高于最大值）
     */
    default List<InventoryItemDO> selectAlertItems() {
        return selectList(new LambdaQueryWrapperX<InventoryItemDO>()
                .eq(InventoryItemDO::getStatus, 0)
                .and(wrapper -> wrapper
                        .apply("quantity < min_quantity")
                        .or()
                        .apply("quantity > max_quantity")));
    }

}
