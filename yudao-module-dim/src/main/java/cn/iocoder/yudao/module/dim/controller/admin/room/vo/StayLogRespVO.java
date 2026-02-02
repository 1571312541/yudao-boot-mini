package cn.iocoder.yudao.module.dim.controller.admin.room.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 住宿日志 Response VO")
@Data
public class StayLogRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "房间ID", example = "1")
    private Long roomId;

    @Schema(description = "房间号", example = "101")
    private String roomNumber;

    @Schema(description = "住客记录ID", example = "1")
    private Long guestId;

    @Schema(description = "住客姓名", example = "张三")
    private String guestName;

    @Schema(description = "操作类型", example = "0")
    private Integer operationType;

    @Schema(description = "操作人ID", example = "1")
    private Long operatorId;

    @Schema(description = "操作人姓名", example = "管理员")
    private String operatorName;

    @Schema(description = "操作时间")
    private LocalDateTime operationTime;

    @Schema(description = "备注")
    private String remarks;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
