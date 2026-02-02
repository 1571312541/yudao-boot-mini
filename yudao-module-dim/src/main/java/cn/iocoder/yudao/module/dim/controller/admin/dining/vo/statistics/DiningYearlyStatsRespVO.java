package cn.iocoder.yudao.module.dim.controller.admin.dining.vo.statistics;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 年度统计 Response VO
 * 返回指定年份每月的用餐汇总
 */
@Schema(description = "管理后台 - 年度统计 Response VO")
@Data
public class DiningYearlyStatsRespVO {

    @Schema(description = "年份", example = "2026")
    private Integer year;

    @Schema(description = "每月统计列表")
    private List<DiningMonthlyStatVO> monthlyStats;

    @Schema(description = "年度汇总")
    private DiningStatsSummaryVO summary;

}
