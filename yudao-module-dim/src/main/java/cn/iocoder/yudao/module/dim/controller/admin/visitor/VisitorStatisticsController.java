package cn.iocoder.yudao.module.dim.controller.admin.visitor;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.dim.controller.admin.visitor.vo.VisitorMonthStatisticsVO;
import cn.iocoder.yudao.module.dim.controller.admin.visitor.vo.VisitorYearCompareStatisticsVO;
import cn.iocoder.yudao.module.dim.service.visitor.VisitLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 管理后台 - 访客统计分析
 */
@Tag(name = "管理后台 - 访客统计分析")
@RestController
@RequestMapping("/dim/visitor/statistics")
@Validated
public class VisitorStatisticsController {

    @Resource
    private VisitLogService visitLogService;

    @GetMapping("/year")
    @Operation(summary = "获取年度访客统计")
    @Parameter(name = "year", description = "年份", example = "2026")
    @PreAuthorize("@ss.hasPermission('dim:visitor:statistics')")
    public CommonResult<List<VisitorMonthStatisticsVO>> getYearStatistics(
            @RequestParam(value = "year", required = false) Integer year) {
        if (year == null) {
            year = LocalDate.now().getYear();
        }
        return success(visitLogService.countByYear(year));
    }

    @GetMapping("/year-compare")
    @Operation(summary = "获取年度对比统计（当年与去年同期）")
    @Parameter(name = "year", description = "年份", example = "2026")
    @PreAuthorize("@ss.hasPermission('dim:visitor:statistics')")
    public CommonResult<VisitorYearCompareStatisticsVO> getYearCompareStatistics(
            @RequestParam(value = "year", required = false) Integer year) {
        if (year == null) {
            year = LocalDate.now().getYear();
        }
        return success(visitLogService.getYearCompareStatistics(year));
    }

}
