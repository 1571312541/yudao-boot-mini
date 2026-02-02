package cn.iocoder.yudao.module.dim.controller.admin.visitor.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "管理后台 - 访客区域分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class VisitorAreaPageReqVO extends PageParam {

    @Schema(description = "区域名称")
    private String areaName;

    @Schema(description = "区域IP")
    private String areaIp;

}
