package cn.iocoder.yudao.module.dim.controller.admin.inventory.vo;

import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 库存批次 Response VO")
@Data
public class InventoryBatchRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "物品ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("物品ID")
    private Long itemId;

    @Schema(description = "批次号", requiredMode = Schema.RequiredMode.REQUIRED, example = "PH20260101001")
    @ExcelProperty("批次号")
    private String batchNo;

    @Schema(description = "批次库存量", example = "100")
    @ExcelProperty("批次库存量")
    private BigDecimal quantity;

    @Schema(description = "入库单价", example = "25.00")
    @ExcelProperty("入库单价")
    private BigDecimal unitPrice;

    @Schema(description = "生产日期")
    @ExcelProperty("生产日期")
    private LocalDate productionDate;

    @Schema(description = "有效期至")
    @ExcelProperty("有效期至")
    private LocalDate expiryDate;

    @Schema(description = "供应商", example = "得力文具")
    @ExcelProperty("供应商")
    private String supplier;

    @Schema(description = "状态：0-正常 1-已清空", example = "0")
    @ExcelProperty("状态")
    private Integer status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("入库时间")
    private LocalDateTime createTime;

}
