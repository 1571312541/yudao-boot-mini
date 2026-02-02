package cn.iocoder.yudao.module.dim.controller.admin.dining.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "管理后台 - 餐饮设置分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class DiningPageReqVO extends PageParam {

    @Schema(description = "餐类名称", example = "早餐")
    private String name;

    @Schema(description = "餐类类型", example = "1")
    private Integer mealType;

    @Schema(description = "状态", example = "0")
    private Integer status;

}
