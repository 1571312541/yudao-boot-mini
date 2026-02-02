package cn.iocoder.yudao.module.dim.controller.admin.dining.vo.statistics;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 未结算费用信息 VO
 * 供住宿模块退房时调用
 */
@Schema(description = "管理后台 - 未结算餐饮费用 VO")
@Data
public class DiningUnsettledExpenseVO {

    @Schema(description = "早餐次数", example = "5")
    private Integer breakfastCount;

    @Schema(description = "午餐次数", example = "10")
    private Integer lunchCount;

    @Schema(description = "晚餐次数", example = "8")
    private Integer dinnerCount;

    @Schema(description = "总金额", example = "345.00")
    private BigDecimal totalAmount;

    @Schema(description = "关联的报餐登记ID列表")
    private List<Long> registrationIds;

    public DiningUnsettledExpenseVO() {
        this.breakfastCount = 0;
        this.lunchCount = 0;
        this.dinnerCount = 0;
        this.totalAmount = BigDecimal.ZERO;
    }

}
