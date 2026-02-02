package cn.iocoder.yudao.module.dim.controller.admin.inventory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 库存分类新增/修改 Request VO")
@Data
public class InventoryCategorySaveReqVO {

    @Schema(description = "主键", example = "1")
    private Long id;

    @Schema(description = "父级ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    @NotNull(message = "父级ID不能为空")
    private Long parentId;

    @Schema(description = "分类名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "办公用品")
    @NotBlank(message = "分类名称不能为空")
    private String name;

    @Schema(description = "分类类型：1-物资 2-耗材 0-通用", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "分类类型不能为空")
    private Integer type;

    @Schema(description = "排序", example = "0")
    private Integer sort;

    @Schema(description = "状态：0-正常 1-停用", example = "0")
    private Integer status;

}
