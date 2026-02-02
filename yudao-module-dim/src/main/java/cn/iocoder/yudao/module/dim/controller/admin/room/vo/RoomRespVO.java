package cn.iocoder.yudao.module.dim.controller.admin.room.vo;

import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 房间 Response VO")
@Data
public class RoomRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "楼层ID", example = "1")
    private Long floorId;

    @Schema(description = "楼层名称", example = "一楼")
    @ExcelProperty("楼层")
    private String floorName;

    @Schema(description = "房间号", example = "101")
    @ExcelProperty("房间号")
    private String roomNumber;

    @Schema(description = "房间类型", example = "0")
    @ExcelProperty("房间类型")
    private Integer roomType;

    @Schema(description = "床位数", example = "2")
    @ExcelProperty("床位数")
    private Integer bedCount;

    @Schema(description = "面积", example = "20.5")
    @ExcelProperty("面积")
    private BigDecimal area;

    @Schema(description = "价格", example = "100.00")
    @ExcelProperty("价格")
    private BigDecimal price;

    @Schema(description = "房间状态", example = "0")
    @ExcelProperty("状态")
    private Integer status;

    @Schema(description = "设施配置")
    private String facilities;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String remarks;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
