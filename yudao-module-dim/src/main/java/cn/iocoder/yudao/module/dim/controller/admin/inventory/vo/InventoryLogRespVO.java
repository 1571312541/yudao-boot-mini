package cn.iocoder.yudao.module.dim.controller.admin.inventory.vo;

import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 库存日志 Response VO")
@Data
public class InventoryLogRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "物品ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("物品ID")
    private Long itemId;

    @Schema(description = "批次ID", example = "1")
    @ExcelProperty("批次ID")
    private Long batchId;

    @Schema(description = "仓库ID", example = "1")
    @ExcelProperty("仓库ID")
    private Long warehouseId;

    @Schema(description = "操作类型：1-采购入库 2-盘盈入库 3-领用出库 4-盘亏出库 5-调拨入库 6-调拨出库", example = "1")
    @ExcelProperty("操作类型")
    private Integer operationType;

    @Schema(description = "操作单号", requiredMode = Schema.RequiredMode.REQUIRED, example = "RK20260101001")
    @ExcelProperty("操作单号")
    private String operationNo;

    @Schema(description = "操作数量", example = "100")
    @ExcelProperty("操作数量")
    private BigDecimal quantity;

    @Schema(description = "单价（折扣后）", example = "25.00")
    @ExcelProperty("单价")
    private BigDecimal unitPrice;

    @Schema(description = "原价（折扣前）", example = "30.00")
    @ExcelProperty("原价")
    private BigDecimal originalPrice;

    @Schema(description = "折扣率(%)", example = "83.33")
    @ExcelProperty("折扣率")
    private BigDecimal discountRate;

    @Schema(description = "金额", example = "2500.00")
    @ExcelProperty("金额")
    private BigDecimal totalAmount;

    @Schema(description = "操作前库存", example = "0")
    @ExcelProperty("操作前库存")
    private BigDecimal beforeQuantity;

    @Schema(description = "操作后库存", example = "100")
    @ExcelProperty("操作后库存")
    private BigDecimal afterQuantity;

    @Schema(description = "审核状态：0-待审核 1-已通过 2-已驳回", example = "0")
    @ExcelProperty("审核状态")
    private Integer auditStatus;

    @Schema(description = "审核人ID", example = "1")
    private Long auditUserId;

    @Schema(description = "审核时间")
    private LocalDateTime auditTime;

    @Schema(description = "审核备注", example = "同意入库")
    private String auditRemark;

    @Schema(description = "申请人ID", example = "1")
    @ExcelProperty("申请人ID")
    private Long applicantId;

    @Schema(description = "申请部门ID", example = "1")
    @ExcelProperty("申请部门ID")
    private Long applicantDeptId;

    @Schema(description = "用途", example = "日常办公使用")
    @ExcelProperty("用途")
    private String purpose;

    @Schema(description = "使用地点", example = "办公室A301")
    @ExcelProperty("使用地点")
    private String usageLocation;

    @Schema(description = "备注", example = "备注信息")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
