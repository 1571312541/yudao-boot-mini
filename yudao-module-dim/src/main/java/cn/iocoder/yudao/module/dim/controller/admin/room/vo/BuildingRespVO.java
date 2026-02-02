package cn.iocoder.yudao.module.dim.controller.admin.room.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 楼栋 Response VO")
@Data
public class BuildingRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "楼栋名称", example = "1号楼")
    private String name;

    @Schema(description = "楼栋编号", example = "B001")
    private String code;

    @Schema(description = "楼层数", example = "6")
    private Integer floorCount;

    @Schema(description = "地址", example = "园区东侧")
    private String address;

    @Schema(description = "状态", example = "0")
    private Integer status;

    @Schema(description = "排序", example = "1")
    private Integer sort;

    @Schema(description = "备注")
    private String remarks;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
