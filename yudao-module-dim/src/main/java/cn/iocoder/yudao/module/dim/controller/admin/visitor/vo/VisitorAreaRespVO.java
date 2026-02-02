package cn.iocoder.yudao.module.dim.controller.admin.visitor.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 访客区域 Response VO")
@Data
public class VisitorAreaRespVO {

    @Schema(description = "主键", example = "1")
    private Long id;

    @Schema(description = "区域名称", example = "一楼大厅")
    private String areaName;

    @Schema(description = "区域IP", example = "192.168.1.100")
    private String areaIp;

    @Schema(description = "备注")
    private String remarks;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
