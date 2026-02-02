package cn.iocoder.yudao.module.dim.controller.admin.dining.vo.price;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "管理后台 - 餐饮价格配置分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class DiningPricePageReqVO extends PageParam {

    @Schema(description = "人员类型（0-外协, 1-实验队, 2-施工队, 3-物业, 4-本所, 5-总部）", example = "0")
    private Integer personType;

    @Schema(description = "餐别（0-早餐 1-午餐 2-晚餐）", example = "0")
    private Integer mealType;

    @Schema(description = "分类（0-客餐 1-桌餐）", example = "0")
    private Integer diningClass;

    @Schema(description = "状态（0-启用 1-停用）", example = "0")
    private Integer status;

}
