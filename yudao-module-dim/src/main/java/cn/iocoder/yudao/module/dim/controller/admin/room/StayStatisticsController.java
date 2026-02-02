package cn.iocoder.yudao.module.dim.controller.admin.room;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.AmountStatisticsRespVO;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.YearlyStatisticsRespVO;
import cn.iocoder.yudao.module.dim.service.room.StayStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Tag(name = "管理后台 - 住宿统计")
@RestController
@RequestMapping("/dim/stay-statistics")
@Validated
public class StayStatisticsController {

    @Resource
    private StayStatisticsService stayStatisticsService;

    @GetMapping("/yearly")
    @Operation(summary = "年度统计")
    @Parameter(name = "year", description = "年份", required = true, example = "2026")
    @PreAuthorize("@ss.hasPermission('dim:stay-statistics:query')")
    public CommonResult<YearlyStatisticsRespVO> getYearlyStatistics(@RequestParam("year") Integer year) {
        return success(stayStatisticsService.getYearlyStatistics(year));
    }

    @GetMapping("/amount")
    @Operation(summary = "金额统计")
    @PreAuthorize("@ss.hasPermission('dim:stay-statistics:query')")
    public CommonResult<AmountStatisticsRespVO> getAmountStatistics(
            @RequestParam(value = "startTime", required = false)
            @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND) LocalDateTime startTime,
            @RequestParam(value = "endTime", required = false)
            @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND) LocalDateTime endTime) {
        return success(stayStatisticsService.getAmountStatistics(startTime, endTime));
    }

    @GetMapping("/export-yearly")
    @Operation(summary = "导出年度统计")
    @Parameter(name = "year", description = "年份", required = true, example = "2026")
    @PreAuthorize("@ss.hasPermission('dim:stay-statistics:export')")
    public void exportYearlyStatistics(@RequestParam("year") Integer year,
                                       HttpServletResponse response) throws IOException {
        YearlyStatisticsRespVO statistics = stayStatisticsService.getYearlyStatistics(year);
        ExcelUtils.write(response, "年度住宿统计-" + year + ".xls", "数据",
                YearlyStatisticsRespVO.MonthData.class, statistics.getMonthlyData());
    }

    @GetMapping("/export-amount")
    @Operation(summary = "导出金额统计")
    @PreAuthorize("@ss.hasPermission('dim:stay-statistics:export')")
    public void exportAmountStatistics(
            @RequestParam(value = "startTime", required = false)
            @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND) LocalDateTime startTime,
            @RequestParam(value = "endTime", required = false)
            @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND) LocalDateTime endTime,
            HttpServletResponse response) throws IOException {
        AmountStatisticsRespVO statistics = stayStatisticsService.getAmountStatistics(startTime, endTime);
        ExcelUtils.write(response, "金额统计.xls", "按单位统计",
                AmountStatisticsRespVO.DeptAmount.class,
                statistics.getDeptAmounts() != null ? statistics.getDeptAmounts() : Collections.emptyList());
    }

}
