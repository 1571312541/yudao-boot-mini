package cn.iocoder.yudao.module.dim.controller.admin.room.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 续住 Request VO")
@Data
public class ExtendStayReqVO {

    @Schema(description = "住客ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "住客ID不能为空")
    private Long guestId;

    @Schema(description = "新预离日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "新预离日期不能为空")
    private LocalDateTime newExpectedCheckOutDate;

    @Schema(description = "备注")
    private String remarks;

}
