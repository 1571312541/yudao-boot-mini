package cn.iocoder.yudao.module.dim.controller.admin.inventory.vo;

import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 库存物品导入 VO
 */
@Schema(description = "管理后台 - 库存物品导入 Request VO")
@Data
public class InventoryItemImportVO {

    @ExcelProperty("物品编码")
    private String code;

    @ExcelProperty("物品名称")
    private String name;

    @ExcelProperty("物品类型")
    private Integer type;

    @ExcelProperty("分类ID")
    private Long categoryId;

    @ExcelProperty("规格型号")
    private String spec;

    @ExcelProperty("计量单位")
    private String unit;

    @ExcelProperty("品牌")
    private String brand;

    @ExcelProperty("生产厂家")
    private String manufacturer;

    @ExcelProperty("最小预警值")
    private BigDecimal minQuantity;

    @ExcelProperty("最大预警值")
    private BigDecimal maxQuantity;

    @ExcelProperty("单价")
    private BigDecimal unitPrice;

    @ExcelProperty("备注")
    private String remark;

}
