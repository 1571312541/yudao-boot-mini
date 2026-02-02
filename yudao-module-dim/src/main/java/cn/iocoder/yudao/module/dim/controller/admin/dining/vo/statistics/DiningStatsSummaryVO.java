package cn.iocoder.yudao.module.dim.controller.admin.dining.vo.statistics;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 餐饮统计汇总 VO
 */
@Schema(description = "管理后台 - 餐饮统计汇总 VO")
@Data
public class DiningStatsSummaryVO {

    @Schema(description = "早餐总人次", example = "100")
    private Integer breakfastCount;

    @Schema(description = "午餐总人次", example = "200")
    private Integer lunchCount;

    @Schema(description = "晚餐总人次", example = "150")
    private Integer dinnerCount;

    @Schema(description = "总人次", example = "450")
    private Integer totalCount;

    @Schema(description = "总金额", example = "6750.00")
    private BigDecimal totalAmount;

    public DiningStatsSummaryVO() {
        this.breakfastCount = 0;
        this.lunchCount = 0;
        this.dinnerCount = 0;
        this.totalCount = 0;
        this.totalAmount = BigDecimal.ZERO;
    }

}
