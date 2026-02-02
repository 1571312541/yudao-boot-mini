package cn.iocoder.yudao.module.dim.dal.mysql.inventory;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.dim.dal.dataobject.inventory.InventoryMonthlyDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 库存月度汇总 Mapper
 */
@Mapper
public interface InventoryMonthlyMapper extends BaseMapperX<InventoryMonthlyDO> {

    /**
     * 根据年月查询汇总列表
     */
    default List<InventoryMonthlyDO> selectListByYearMonth(String yearMonth) {
        return selectList(new LambdaQueryWrapperX<InventoryMonthlyDO>()
                .eq(InventoryMonthlyDO::getYearMonth, yearMonth)
                .orderByAsc(InventoryMonthlyDO::getItemId));
    }

    /**
     * 根据物品ID和年月查询
     */
    default InventoryMonthlyDO selectByItemIdAndYearMonth(Long itemId, String yearMonth) {
        return selectOne(new LambdaQueryWrapperX<InventoryMonthlyDO>()
                .eq(InventoryMonthlyDO::getItemId, itemId)
                .eq(InventoryMonthlyDO::getYearMonth, yearMonth));
    }

    /**
     * 查询可用年月列表
     */
    default List<String> selectDistinctYearMonths() {
        return selectObjs(new LambdaQueryWrapperX<InventoryMonthlyDO>()
                .select(InventoryMonthlyDO::getYearMonth)
                .groupBy(InventoryMonthlyDO::getYearMonth)
                .orderByDesc(InventoryMonthlyDO::getYearMonth));
    }

}
