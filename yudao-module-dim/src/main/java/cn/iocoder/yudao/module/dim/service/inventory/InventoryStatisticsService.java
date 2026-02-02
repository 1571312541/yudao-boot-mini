package cn.iocoder.yudao.module.dim.service.inventory;

import cn.iocoder.yudao.module.dim.controller.admin.inventory.vo.InventoryStatisticsVO;

import java.util.List;

/**
 * 库存统计 Service 接口
 */
public interface InventoryStatisticsService {

    /**
     * 获取统计概览
     */
    InventoryStatisticsVO.OverviewVO getOverview();

    /**
     * 获取库存趋势（最近N天）
     */
    InventoryStatisticsVO.TrendVO getTrend(Integer days);

    /**
     * 获取分类分布
     */
    List<InventoryStatisticsVO.CategoryDistVO> getCategoryDistribution(Integer type);

    /**
     * 获取月度出入库统计（最近N月）
     */
    InventoryStatisticsVO.MonthlyOperationVO getMonthlyOperation(Integer months);

}
