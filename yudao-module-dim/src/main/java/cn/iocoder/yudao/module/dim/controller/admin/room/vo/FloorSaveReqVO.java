package cn.iocoder.yudao.module.dim.controller.admin.room.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 楼层新增/修改 Request VO")
@Data
public class FloorSaveReqVO {

    @Schema(description = "主键", example = "1")
    private Long id;

    @Schema(description = "楼栋ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "楼栋不能为空")
    private Long buildingId;

    @Schema(description = "楼层名称", example = "一楼")
    private String name;

    @Schema(description = "楼层编号", example = "1")
    private Integer floorNumber;

    @Schema(description = "房间数量", example = "10")
    private Integer roomCount;

    @Schema(description = "状态", example = "0")
    private Integer status;

    @Schema(description = "排序", example = "1")
    private Integer sort;

    @Schema(description = "备注")
    private String remarks;

}
