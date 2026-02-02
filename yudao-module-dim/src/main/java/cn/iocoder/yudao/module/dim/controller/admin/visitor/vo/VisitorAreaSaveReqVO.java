package cn.iocoder.yudao.module.dim.controller.admin.visitor.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Schema(description = "管理后台 - 访客区域新增/修改 Request VO")
@Data
public class VisitorAreaSaveReqVO {

    @Schema(description = "主键", example = "1")
    private Long id;

    @Schema(description = "区域名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "一楼大厅")
    @NotBlank(message = "区域名称不能为空")
    private String areaName;

    @Schema(description = "区域IP", example = "192.168.1.100")
    private String areaIp;

    @Schema(description = "备注")
    private String remarks;

}
