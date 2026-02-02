package cn.iocoder.yudao.module.dim.controller.admin.dining.vo.price;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 餐饮价格配置新增/修改 Request VO")
@Data
public class DiningPriceSaveReqVO {

    @Schema(description = "主键", example = "1")
    private Long id;

    @Schema(description = "人员类型（0-外协, 1-实验队, 2-施工队, 3-物业, 4-本所, 5-总部）", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    @NotNull(message = "人员类型不能为空")
    private Integer personType;

    @Schema(description = "餐别（0-早餐 1-午餐 2-晚餐）", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    @NotNull(message = "餐别不能为空")
    private Integer mealType;

    @Schema(description = "分类（0-客餐 1-桌餐）", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    @NotNull(message = "分类不能为空")
    private Integer diningClass;

    @Schema(description = "单价(元)", requiredMode = Schema.RequiredMode.REQUIRED, example = "15.00")
    @NotNull(message = "单价不能为空")
    private BigDecimal price;

    @Schema(description = "状态（0-启用 1-停用）", example = "0")
    private Integer status;

    @Schema(description = "备注")
    private String remarks;

}
