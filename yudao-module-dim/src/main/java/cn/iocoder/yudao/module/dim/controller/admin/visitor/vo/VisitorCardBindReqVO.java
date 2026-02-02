package cn.iocoder.yudao.module.dim.controller.admin.visitor.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 访客卡绑定 Request VO")
@Data
public class VisitorCardBindReqVO {

    @Schema(description = "访客ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "访客ID不能为空")
    private Long visitorId;

    @Schema(description = "卡号", example = "CARD001")
    private String cardId;

    @Schema(description = "二维码", example = "QR001")
    private String qrCode;

    @Schema(description = "区域ID", example = "1")
    private Long areaId;

}
