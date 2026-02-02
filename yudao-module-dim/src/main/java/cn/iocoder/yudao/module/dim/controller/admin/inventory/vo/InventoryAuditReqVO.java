package cn.iocoder.yudao.module.dim.controller.admin.inventory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 审核请求 VO
 */
@Schema(description = "管理后台 - 审核 Request VO")
@Data
public class InventoryAuditReqVO {

    @Schema(description = "日志ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "日志ID不能为空")
    private Long logId;

    @Schema(description = "审核结果：true-通过 false-驳回", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @NotNull(message = "审核结果不能为空")
    private Boolean approved;

    @Schema(description = "审核备注", example = "同意入库")
    private String remark;

}
