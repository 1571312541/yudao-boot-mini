package cn.iocoder.yudao.module.dim.service.dining;

import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.statistics.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 餐饮统计 Service 接口
 */
public interface DiningStatisticsService {

    /**
     * 按人员类型统计用餐数据
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @param mealType  餐别（可选）
     * @return 各人员类型的统计数据
     */
    List<DiningStatsByPersonTypeRespVO> getStatsByPersonType(LocalDate startDate, LocalDate endDate, Integer mealType);

    /**
     * 按单位统计用餐数据
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 各单位的统计数据
     */
    List<DiningStatsByDeptRespVO> getStatsByDept(LocalDate startDate, LocalDate endDate);

    /**
     * 月度统计
     * 返回指定月份每天的用餐汇总
     *
     * @param year  年份
     * @param month 月份
     * @return 月度统计数据
     */
    DiningMonthlyStatsRespVO getMonthlyStats(Integer year, Integer month);

    /**
     * 年度统计
     * 返回指定年份每月的用餐汇总
     *
     * @param year 年份
     * @return 年度统计数据
     */
    DiningYearlyStatsRespVO getYearlyStats(Integer year);

    /**
     * 获取费用统计导出数据
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @param deptName  单位名称（可选）
     * @param userName  用户名称（可选）
     * @return 费用统计数据列表
     */
    List<DiningExpenseStatVO> getExpenseStats(LocalDate startDate, LocalDate endDate, String deptName, String userName);

}
