package cn.iocoder.yudao.module.dim.controller.admin.asset.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 资产持有新增/修改 Request VO")
@Data
public class AssetHolderSaveReqVO {

    @Schema(description = "主键", example = "1")
    private Long id;

    @Schema(description = "资产ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "资产不能为空")
    private Long assetId;

    @Schema(description = "持有数量", example = "1")
    private Integer quantity;

    @Schema(description = "持有人ID", example = "1")
    private Long holderId;

    @Schema(description = "持有人姓名", example = "张三")
    private String holderName;

    @Schema(description = "部门ID", example = "1")
    private Long deptId;

    @Schema(description = "楼号", example = "1")
    private Integer buildingNo;

    @Schema(description = "楼层", example = "2")
    private Integer floor;

    @Schema(description = "具体位置", example = "201室")
    private String position;

    @Schema(description = "备注")
    private String remarks;

}
