package cn.iocoder.yudao.module.dim.controller.admin.room.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "管理后台 - 金额统计响应 VO")
@Data
public class AmountStatisticsRespVO {

    @Schema(description = "总金额", example = "50000.00")
    private BigDecimal totalAmount;

    @Schema(description = "已结算金额", example = "45000.00")
    private BigDecimal settledAmount;

    @Schema(description = "未结算金额", example = "5000.00")
    private BigDecimal unsettledAmount;

    @Schema(description = "按单位统计")
    private List<DeptAmount> deptAmounts;

    @Data
    public static class DeptAmount {

        @Schema(description = "单位名称", example = "综合部")
        private String deptName;

        @Schema(description = "金额", example = "10000.00")
        private BigDecimal amount;

        @Schema(description = "人次", example = "20")
        private Integer count;

    }

}
