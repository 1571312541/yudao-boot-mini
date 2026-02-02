package cn.iocoder.yudao.module.dim.controller.admin.fire.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 消防设备巡检日志 Response VO")
@Data
public class FireEquipmentCheckLogRespVO {

    @Schema(description = "主键", example = "1")
    private Long id;

    @Schema(description = "设备ID", example = "1")
    private Long equipmentId;

    @Schema(description = "巡检结果:0正常,1故障", example = "0")
    private Integer checkResult;

    @Schema(description = "巡检备注")
    private String checkRemark;

    @Schema(description = "巡检时间")
    private LocalDateTime checkTime;

    @Schema(description = "巡检人ID")
    private Long checkerId;

    @Schema(description = "巡检人姓名")
    private String checkerName;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
