package cn.iocoder.yudao.module.dim.controller.admin.asset.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Schema(description = "管理后台 - 资产分类新增/修改 Request VO")
@Data
public class AssetCategorySaveReqVO {

    @Schema(description = "主键", example = "1")
    private Long id;

    @Schema(description = "父级ID", example = "0")
    private Long parentId;

    @Schema(description = "分类名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "办公设备")
    @NotBlank(message = "分类名称不能为空")
    private String name;

    @Schema(description = "排序", example = "0")
    private Integer sort;

    @Schema(description = "状态", example = "1")
    private Integer status;

}
