package cn.iocoder.yudao.module.dim.controller.admin.inventory.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "管理后台 - 库存分类分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class InventoryCategoryPageReqVO extends PageParam {

    @Schema(description = "分类名称", example = "办公用品")
    private String name;

    @Schema(description = "分类类型：1-物资 2-耗材 0-通用", example = "1")
    private Integer type;

    @Schema(description = "状态：0-正常 1-停用", example = "0")
    private Integer status;

}
