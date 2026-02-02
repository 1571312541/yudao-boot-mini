package cn.iocoder.yudao.module.dim.controller.admin.asset.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 资产采购审核 Request VO")
@Data
public class AssetPurchaseAuditReqVO {

    @Schema(description = "采购单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "采购单ID不能为空")
    private Long id;

    @Schema(description = "是否通过", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @NotNull(message = "审核结果不能为空")
    private Boolean approved;

    @Schema(description = "审核备注", example = "同意采购")
    private String remark;

}
