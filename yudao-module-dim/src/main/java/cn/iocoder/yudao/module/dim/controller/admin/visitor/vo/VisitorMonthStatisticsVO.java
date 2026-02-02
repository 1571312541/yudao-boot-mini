package cn.iocoder.yudao.module.dim.controller.admin.visitor.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 访客统计 - 月度统计项
 */
@Schema(description = "管理后台 - 访客月度统计 Response VO")
@Data
public class VisitorMonthStatisticsVO {

    @Schema(description = "月份", example = "1")
    private Integer month;

    @Schema(description = "访客总数", example = "100")
    private Long total;

}
