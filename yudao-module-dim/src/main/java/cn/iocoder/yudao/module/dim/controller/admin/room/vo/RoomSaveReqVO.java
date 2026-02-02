package cn.iocoder.yudao.module.dim.controller.admin.room.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 房间新增/修改 Request VO")
@Data
public class RoomSaveReqVO {

    @Schema(description = "主键", example = "1")
    private Long id;

    @Schema(description = "楼层ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "楼层不能为空")
    private Long floorId;

    @Schema(description = "房间号", requiredMode = Schema.RequiredMode.REQUIRED, example = "101")
    @NotBlank(message = "房间号不能为空")
    private String roomNumber;

    @Schema(description = "房间类型", example = "0")
    private Integer roomType;

    @Schema(description = "床位数", example = "2")
    private Integer bedCount;

    @Schema(description = "面积", example = "20.5")
    private BigDecimal area;

    @Schema(description = "价格", example = "100.00")
    private BigDecimal price;

    @Schema(description = "房间状态", example = "0")
    private Integer status;

    @Schema(description = "设施配置")
    private String facilities;

    @Schema(description = "备注")
    private String remarks;

}
