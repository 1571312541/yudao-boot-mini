package cn.iocoder.yudao.module.dim.controller.admin.visitor.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 来访日志 Response VO")
@Data
public class VisitLogRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "访客ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long visitorId;

    @Schema(description = "访客姓名", example = "张三")
    private String visitorName;

    @Schema(description = "状态", example = "0")
    private Integer status;

    @Schema(description = "来访时间")
    private LocalDateTime visitDate;

    @Schema(description = "离场时间")
    private LocalDateTime leaveDate;

    @Schema(description = "来访事由", example = "工程施工")
    private String purpose;

    @Schema(description = "备注")
    private String remarks;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
