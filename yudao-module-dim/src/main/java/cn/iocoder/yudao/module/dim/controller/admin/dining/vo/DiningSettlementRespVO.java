package cn.iocoder.yudao.module.dim.controller.admin.dining.vo;

import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 餐饮结算 Response VO")
@Data
public class DiningSettlementRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "结算人ID", example = "1")
    private Long userId;

    @Schema(description = "结算人姓名", example = "张三")
    @ExcelProperty("结算人")
    private String userName;

    @Schema(description = "结算人部门", example = "综合部")
    @ExcelProperty("部门")
    private String deptName;

    @Schema(description = "结算开始日期")
    @ExcelProperty("开始日期")
    private LocalDate startDate;

    @Schema(description = "结算结束日期")
    @ExcelProperty("结束日期")
    private LocalDate endDate;

    @Schema(description = "早餐次数", example = "10")
    @ExcelProperty("早餐次数")
    private Integer breakfastCount;

    @Schema(description = "午餐次数", example = "20")
    @ExcelProperty("午餐次数")
    private Integer lunchCount;

    @Schema(description = "晚餐次数", example = "15")
    @ExcelProperty("晚餐次数")
    private Integer dinnerCount;

    @Schema(description = "总金额", example = "500.00")
    @ExcelProperty("总金额")
    private BigDecimal totalAmount;

    @Schema(description = "已付金额", example = "500.00")
    @ExcelProperty("已付金额")
    private BigDecimal paidAmount;

    @Schema(description = "状态", example = "0")
    @ExcelProperty("状态")
    private Integer status;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String remarks;

    @Schema(description = "是否已支付", example = "N")
    @ExcelProperty("是否已支付")
    private String isPaid;

    @Schema(description = "是否已开票", example = "N")
    @ExcelProperty("是否已开票")
    private String isInvoiced;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
