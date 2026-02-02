package cn.iocoder.yudao.module.dim.controller.admin.visitor.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 访客统计 - 年度对比统计
 */
@Schema(description = "管理后台 - 访客年度对比统计 Response VO")
@Data
public class VisitorYearCompareStatisticsVO {

    @Schema(description = "当前年度统计")
    private List<VisitorMonthStatisticsVO> current;

    @Schema(description = "去年同期统计")
    private List<VisitorMonthStatisticsVO> lastYear;

    @Schema(description = "统计年份", example = "2026")
    private Integer year;

}
