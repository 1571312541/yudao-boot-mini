package cn.iocoder.yudao.module.dim.controller.admin.room.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "管理后台 - 楼栋分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class BuildingPageReqVO extends PageParam {

    @Schema(description = "楼栋名称", example = "1号楼")
    private String name;

    @Schema(description = "状态", example = "0")
    private Integer status;

}
