package cn.iocoder.yudao.module.dim.service.inventory;

import cn.iocoder.yudao.module.dim.controller.admin.inventory.vo.InventoryMonthlyReportVO;

import java.util.List;

/**
 * 库存报表 Service 接口
 */
public interface InventoryReportService {

    /**
     * 获取月度汇总报表
     *
     * @param yearMonth 年月
     * @param type 物品类型（可选）
     * @return 汇总数据列表
     */
    List<InventoryMonthlyReportVO> getMonthlyReport(String yearMonth, Integer type);

    /**
     * 获取可用年月列表
     *
     * @return 年月列表
     */
    List<String> getAvailableYearMonths();

    /**
     * 重新计算指定年月的汇总数据
     *
     * @param yearMonth 年月
     */
    void recalculateMonthlyData(String yearMonth);

}
