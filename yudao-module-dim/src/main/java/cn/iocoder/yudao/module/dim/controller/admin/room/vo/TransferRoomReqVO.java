package cn.iocoder.yudao.module.dim.controller.admin.room.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 换房 Request VO")
@Data
public class TransferRoomReqVO {

    @Schema(description = "住客ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "住客ID不能为空")
    private Long guestId;

    @Schema(description = "目标房间ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "目标房间ID不能为空")
    private Long newRoomId;

    @Schema(description = "备注")
    private String remarks;

}
