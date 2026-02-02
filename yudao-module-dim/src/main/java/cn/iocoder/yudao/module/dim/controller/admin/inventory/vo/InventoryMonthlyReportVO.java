package cn.iocoder.yudao.module.dim.controller.admin.inventory.vo;

import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 月度库存汇总报表 VO
 */
@Schema(description = "管理后台 - 月度库存汇总报表 Response VO")
@Data
public class InventoryMonthlyReportVO {

    @Schema(description = "物品ID")
    private Long itemId;

    @Schema(description = "物品编码")
    @ExcelProperty("物品编码")
    private String itemCode;

    @Schema(description = "物品名称")
    @ExcelProperty("物品名称")
    private String itemName;

    @Schema(description = "物品类型")
    @ExcelProperty("物品类型")
    private String typeName;

    @Schema(description = "分类名称")
    @ExcelProperty("分类")
    private String categoryName;

    @Schema(description = "计量单位")
    @ExcelProperty("单位")
    private String unit;

    @Schema(description = "年月")
    @ExcelProperty("年月")
    private String yearMonth;

    @Schema(description = "期初数量")
    @ExcelProperty("期初数量")
    private BigDecimal openingQty;

    @Schema(description = "期初金额")
    @ExcelProperty("期初金额")
    private BigDecimal openingAmount;

    @Schema(description = "入库数量")
    @ExcelProperty("入库数量")
    private BigDecimal inQty;

    @Schema(description = "入库金额")
    @ExcelProperty("入库金额")
    private BigDecimal inAmount;

    @Schema(description = "出库数量")
    @ExcelProperty("出库数量")
    private BigDecimal outQty;

    @Schema(description = "出库金额")
    @ExcelProperty("出库金额")
    private BigDecimal outAmount;

    @Schema(description = "期末数量")
    @ExcelProperty("期末数量")
    private BigDecimal closingQty;

    @Schema(description = "期末金额")
    @ExcelProperty("期末金额")
    private BigDecimal closingAmount;

}
