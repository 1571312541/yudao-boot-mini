package cn.iocoder.yudao.module.dim.controller.admin.dining.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "管理后台 - 餐饮结算新增/修改 Request VO")
@Data
public class DiningSettlementSaveReqVO {

    @Schema(description = "主键", example = "1")
    private Long id;

    @Schema(description = "结算人ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "结算人不能为空")
    private Long userId;

    @Schema(description = "结算人姓名", example = "张三")
    private String userName;

    @Schema(description = "结算人部门", example = "综合部")
    private String deptName;

    @Schema(description = "结算开始日期")
    private LocalDate startDate;

    @Schema(description = "结算结束日期")
    private LocalDate endDate;

    @Schema(description = "早餐次数", example = "10")
    private Integer breakfastCount;

    @Schema(description = "午餐次数", example = "20")
    private Integer lunchCount;

    @Schema(description = "晚餐次数", example = "15")
    private Integer dinnerCount;

    @Schema(description = "总金额", example = "500.00")
    private BigDecimal totalAmount;

    @Schema(description = "已付金额", example = "500.00")
    private BigDecimal paidAmount;

    @Schema(description = "状态", example = "0")
    private Integer status;

    @Schema(description = "备注")
    private String remarks;

    @Schema(description = "是否已支付", example = "N")
    private String isPaid;

    @Schema(description = "是否已开票", example = "N")
    private String isInvoiced;

}
