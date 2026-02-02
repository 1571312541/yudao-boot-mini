package cn.iocoder.yudao.module.dim.controller.admin.fire.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 消防设备巡检 Request VO")
@Data
public class FireEquipmentCheckReqVO {

    @Schema(description = "设备ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "设备ID不能为空")
    private Long equipmentId;

    @Schema(description = "巡检结果:0正常,1故障", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    @NotNull(message = "巡检结果不能为空")
    private Integer checkResult;

    @Schema(description = "巡检备注", example = "设备运行正常")
    private String checkRemark;

}
