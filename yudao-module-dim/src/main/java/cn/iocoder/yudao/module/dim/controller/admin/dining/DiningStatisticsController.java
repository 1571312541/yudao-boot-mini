package cn.iocoder.yudao.module.dim.controller.admin.dining;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.statistics.*;
import cn.iocoder.yudao.module.dim.service.dining.DiningStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;

/**
 * 餐饮统计 Controller
 */
@Tag(name = "管理后台 - 餐饮统计")
@RestController
@RequestMapping("/dim/dining-statistics")
@Validated
public class DiningStatisticsController {

    @Resource
    private DiningStatisticsService diningStatisticsService;

    @GetMapping("/by-person-type")
    @Operation(summary = "按人员类型统计用餐数据", description = "返回各人员类型的早/午/晚餐人数")
    @Parameters({
            @Parameter(name = "startDate", description = "开始日期", required = true, example = "2026-01-01"),
            @Parameter(name = "endDate", description = "结束日期", required = true, example = "2026-01-31"),
            @Parameter(name = "mealType", description = "餐别（0-早餐 1-午餐 2-晚餐）", example = "1")
    })
    @PreAuthorize("@ss.hasPermission('dim:dining-statistics:query')")
    public CommonResult<List<DiningStatsByPersonTypeRespVO>> getStatsByPersonType(
            @RequestParam("startDate") @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY) LocalDate endDate,
            @RequestParam(value = "mealType", required = false) Integer mealType) {
        List<DiningStatsByPersonTypeRespVO> result = diningStatisticsService.getStatsByPersonType(startDate, endDate, mealType);
        return success(result);
    }

    @GetMapping("/by-dept")
    @Operation(summary = "按单位统计用餐数据", description = "返回各单位的早/午/晚餐人数")
    @Parameters({
            @Parameter(name = "startDate", description = "开始日期", required = true, example = "2026-01-01"),
            @Parameter(name = "endDate", description = "结束日期", required = true, example = "2026-01-31")
    })
    @PreAuthorize("@ss.hasPermission('dim:dining-statistics:query')")
    public CommonResult<List<DiningStatsByDeptRespVO>> getStatsByDept(
            @RequestParam("startDate") @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY) LocalDate endDate) {
        List<DiningStatsByDeptRespVO> result = diningStatisticsService.getStatsByDept(startDate, endDate);
        return success(result);
    }

    @GetMapping("/monthly")
    @Operation(summary = "月度统计", description = "返回指定月份每天的用餐汇总")
    @Parameters({
            @Parameter(name = "year", description = "年份", required = true, example = "2026"),
            @Parameter(name = "month", description = "月份", required = true, example = "1")
    })
    @PreAuthorize("@ss.hasPermission('dim:dining-statistics:query')")
    public CommonResult<DiningMonthlyStatsRespVO> getMonthlyStats(
            @RequestParam("year") Integer year,
            @RequestParam("month") Integer month) {
        DiningMonthlyStatsRespVO result = diningStatisticsService.getMonthlyStats(year, month);
        return success(result);
    }

    @GetMapping("/yearly")
    @Operation(summary = "年度统计", description = "返回指定年份每月的用餐汇总")
    @Parameter(name = "year", description = "年份", required = true, example = "2026")
    @PreAuthorize("@ss.hasPermission('dim:dining-statistics:query')")
    public CommonResult<DiningYearlyStatsRespVO> getYearlyStats(@RequestParam("year") Integer year) {
        DiningYearlyStatsRespVO result = diningStatisticsService.getYearlyStats(year);
        return success(result);
    }

    @GetMapping("/export-expense")
    @Operation(summary = "导出费用统计Excel", description = "导出指定时间范围的费用统计数据")
    @Parameters({
            @Parameter(name = "startDate", description = "开始日期", required = true, example = "2026-01-01"),
            @Parameter(name = "endDate", description = "结束日期", required = true, example = "2026-01-31"),
            @Parameter(name = "deptName", description = "单位名称", example = "综合部"),
            @Parameter(name = "userName", description = "用户名称", example = "张三")
    })
    @PreAuthorize("@ss.hasPermission('dim:dining-statistics:export')")
    public void exportExpenseExcel(HttpServletResponse response,
            @RequestParam("startDate") @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY) LocalDate endDate,
            @RequestParam(value = "deptName", required = false) String deptName,
            @RequestParam(value = "userName", required = false) String userName) throws IOException {
        List<DiningExpenseStatVO> list = diningStatisticsService.getExpenseStats(startDate, endDate, deptName, userName);
        ExcelUtils.write(response, "费用统计.xls", "费用统计", DiningExpenseStatVO.class, list);
    }

    @GetMapping("/export-yearly")
    @Operation(summary = "导出年度统计Excel", description = "导出指定年份的年度统计数据")
    @Parameter(name = "year", description = "年份", required = true, example = "2026")
    @PreAuthorize("@ss.hasPermission('dim:dining-statistics:export')")
    public void exportYearlyExcel(HttpServletResponse response,
            @RequestParam("year") Integer year) throws IOException {
        DiningYearlyStatsRespVO stats = diningStatisticsService.getYearlyStats(year);
        ExcelUtils.write(response, year + "年用餐统计.xls", year + "年", DiningMonthlyStatVO.class, stats.getMonthlyStats());
    }

    @GetMapping("/export-monthly")
    @Operation(summary = "导出月度统计Excel", description = "导出指定月份的月度统计数据")
    @Parameters({
            @Parameter(name = "year", description = "年份", required = true, example = "2026"),
            @Parameter(name = "month", description = "月份", required = true, example = "1")
    })
    @PreAuthorize("@ss.hasPermission('dim:dining-statistics:export')")
    public void exportMonthlyExcel(HttpServletResponse response,
            @RequestParam("year") Integer year,
            @RequestParam("month") Integer month) throws IOException {
        DiningMonthlyStatsRespVO stats = diningStatisticsService.getMonthlyStats(year, month);
        String fileName = year + "年" + month + "月用餐统计.xls";
        String sheetName = year + "年" + month + "月";
        ExcelUtils.write(response, fileName, sheetName, DiningDailyStatVO.class, stats.getDailyStats());
    }

    @GetMapping("/export-by-person-type")
    @Operation(summary = "导出按人员类型统计Excel", description = "导出按人员类型统计的用餐数据")
    @Parameters({
            @Parameter(name = "startDate", description = "开始日期", required = true, example = "2026-01-01"),
            @Parameter(name = "endDate", description = "结束日期", required = true, example = "2026-01-31"),
            @Parameter(name = "mealType", description = "餐别（0-早餐 1-午餐 2-晚餐）", example = "1")
    })
    @PreAuthorize("@ss.hasPermission('dim:dining-statistics:export')")
    public void exportByPersonTypeExcel(HttpServletResponse response,
            @RequestParam("startDate") @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY) LocalDate endDate,
            @RequestParam(value = "mealType", required = false) Integer mealType) throws IOException {
        List<DiningStatsByPersonTypeRespVO> list = diningStatisticsService.getStatsByPersonType(startDate, endDate, mealType);
        ExcelUtils.write(response, "按人员类型用餐统计.xls", "按人员类型统计", DiningStatsByPersonTypeRespVO.class, list);
    }

    @GetMapping("/export-by-dept")
    @Operation(summary = "导出按单位统计Excel", description = "导出按单位统计的用餐数据")
    @Parameters({
            @Parameter(name = "startDate", description = "开始日期", required = true, example = "2026-01-01"),
            @Parameter(name = "endDate", description = "结束日期", required = true, example = "2026-01-31")
    })
    @PreAuthorize("@ss.hasPermission('dim:dining-statistics:export')")
    public void exportByDeptExcel(HttpServletResponse response,
            @RequestParam("startDate") @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY) LocalDate endDate) throws IOException {
        List<DiningStatsByDeptRespVO> list = diningStatisticsService.getStatsByDept(startDate, endDate);
        ExcelUtils.write(response, "按单位用餐统计.xls", "按单位统计", DiningStatsByDeptRespVO.class, list);
    }

}
