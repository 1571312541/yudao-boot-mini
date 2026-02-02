package cn.iocoder.yudao.module.dim.controller.admin.visitor.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 访客卡信息 Response VO")
@Data
public class VisitorCardRespVO {

    @Schema(description = "主键", example = "1")
    private Long id;

    @Schema(description = "访客ID", example = "1")
    private Long visitorId;

    @Schema(description = "卡号", example = "CARD001")
    private String cardId;

    @Schema(description = "二维码", example = "QR001")
    private String qrCode;

    @Schema(description = "区域ID", example = "1")
    private Long areaId;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
