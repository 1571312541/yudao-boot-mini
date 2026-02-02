package cn.iocoder.yudao.module.dim.controller.admin.inventory.vo;

import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 库存物品 Response VO")
@Data
public class InventoryItemRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "物品编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "WZ000001")
    @ExcelProperty("物品编码")
    private String code;

    @Schema(description = "物品名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "A4纸")
    @ExcelProperty("物品名称")
    private String name;

    @Schema(description = "物品类型：1-物资 2-耗材", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("物品类型")
    private Integer type;

    @Schema(description = "分类ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("分类ID")
    private Long categoryId;

    @Schema(description = "仓库ID", example = "1")
    @ExcelProperty("仓库ID")
    private Long warehouseId;

    @Schema(description = "规格型号", example = "500张/包")
    @ExcelProperty("规格型号")
    private String spec;

    @Schema(description = "计量单位", requiredMode = Schema.RequiredMode.REQUIRED, example = "包")
    @ExcelProperty("计量单位")
    private String unit;

    @Schema(description = "品牌", example = "得力")
    @ExcelProperty("品牌")
    private String brand;

    @Schema(description = "生产厂家", example = "得力集团")
    @ExcelProperty("生产厂家")
    private String manufacturer;

    @Schema(description = "当前库存量", example = "100")
    @ExcelProperty("当前库存量")
    private BigDecimal quantity;

    @Schema(description = "锁定库存量", example = "0")
    @ExcelProperty("锁定库存量")
    private BigDecimal lockedQuantity;

    @Schema(description = "最小库存预警值", example = "10")
    @ExcelProperty("最小预警值")
    private BigDecimal minQuantity;

    @Schema(description = "最大库存预警值", example = "1000")
    @ExcelProperty("最大预警值")
    private BigDecimal maxQuantity;

    @Schema(description = "单价", example = "25.00")
    @ExcelProperty("单价")
    private BigDecimal unitPrice;

    @Schema(description = "库存金额", example = "2500.00")
    @ExcelProperty("库存金额")
    private BigDecimal totalAmount;

    @Schema(description = "状态：0-正常 1-停用", example = "0")
    @ExcelProperty("状态")
    private Integer status;

    @Schema(description = "备注", example = "常用办公耗材")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "图片URL", example = "https://example.com/a4.jpg")
    private String imageUrl;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
