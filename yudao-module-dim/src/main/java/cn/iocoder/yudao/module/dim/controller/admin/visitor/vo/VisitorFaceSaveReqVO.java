package cn.iocoder.yudao.module.dim.controller.admin.visitor.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "管理后台 - 访客人脸保存 Request VO")
@Data
public class VisitorFaceSaveReqVO {

    @Schema(description = "访客ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "访客ID不能为空")
    private Long visitorId;

    @Schema(description = "人脸图片列表(base64格式)")
    private List<String> faceImages;

}
