package cn.iocoder.yudao.module.dim.controller.admin.fire.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "管理后台 - 楼栋配置 Response VO")
@Data
public class BuildingConfigRespVO {

    @Schema(description = "主键", example = "1")
    private Long id;

    @Schema(description = "楼号", example = "1")
    private Integer buildingNo;

    @Schema(description = "楼栋名称", example = "1号楼")
    private String buildingName;

    @Schema(description = "楼层列表", example = "[1,2,3,-1]")
    private List<Integer> floors;

    @Schema(description = "排序", example = "1")
    private Integer sort;

    @Schema(description = "楼栋图标URL")
    private String iconUrl;

    @Schema(description = "平面图URL")
    private String planUrl;

    @Schema(description = "状态:0禁用,1启用", example = "1")
    private Integer status;

    @Schema(description = "设备总数", example = "10")
    private Integer equipmentCount;

    @Schema(description = "正常设备数", example = "8")
    private Integer normalCount;

    @Schema(description = "故障设备数", example = "2")
    private Integer faultCount;

}
