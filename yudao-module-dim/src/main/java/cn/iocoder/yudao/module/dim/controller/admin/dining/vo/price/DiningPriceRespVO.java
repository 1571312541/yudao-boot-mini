package cn.iocoder.yudao.module.dim.controller.admin.dining.vo.price;

import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 餐饮价格配置 Response VO")
@Data
public class DiningPriceRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "人员类型（0-外协, 1-实验队, 2-施工队, 3-物业, 4-本所, 5-总部）", example = "0")
    @ExcelProperty("人员类型")
    private Integer personType;

    @Schema(description = "餐别（0-早餐 1-午餐 2-晚餐）", example = "0")
    @ExcelProperty("餐别")
    private Integer mealType;

    @Schema(description = "分类（0-客餐 1-桌餐）", example = "0")
    @ExcelProperty("分类")
    private Integer diningClass;

    @Schema(description = "单价(元)", example = "15.00")
    @ExcelProperty("单价")
    private BigDecimal price;

    @Schema(description = "状态（0-启用 1-停用）", example = "0")
    @ExcelProperty("状态")
    private Integer status;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String remarks;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
