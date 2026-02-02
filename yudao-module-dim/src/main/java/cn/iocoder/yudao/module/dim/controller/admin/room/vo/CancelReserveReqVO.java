package cn.iocoder.yudao.module.dim.controller.admin.room.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 取消预约 Request VO")
@Data
public class CancelReserveReqVO {

    @Schema(description = "住客ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "住客ID不能为空")
    private Long guestId;

    @Schema(description = "备注")
    private String remarks;

}
