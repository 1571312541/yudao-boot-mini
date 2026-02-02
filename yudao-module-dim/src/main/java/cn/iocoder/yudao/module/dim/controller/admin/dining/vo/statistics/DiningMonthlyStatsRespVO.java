package cn.iocoder.yudao.module.dim.controller.admin.dining.vo.statistics;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 月度统计 Response VO
 * 返回指定月份每天的用餐汇总
 */
@Schema(description = "管理后台 - 月度统计 Response VO")
@Data
public class DiningMonthlyStatsRespVO {

    @Schema(description = "年份", example = "2026")
    private Integer year;

    @Schema(description = "月份", example = "1")
    private Integer month;

    @Schema(description = "每日统计列表")
    private List<DiningDailyStatVO> dailyStats;

    @Schema(description = "月度汇总")
    private DiningStatsSummaryVO summary;

}
