package cn.iocoder.yudao.module.dim.controller.admin.room.vo;

import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "管理后台 - 房间导入 VO")
@Data
public class RoomImportVO {

    @ExcelProperty("楼栋名称")
    private String buildingName;

    @ExcelProperty("楼层名称")
    private String floorName;

    @ExcelProperty("房间号")
    private String roomNumber;

    @ExcelProperty("房间类型")
    private String roomType; // 单人间/双人间/多人间

    @ExcelProperty("床位数")
    private Integer bedCount;

    @ExcelProperty("面积")
    private BigDecimal area;

    @ExcelProperty("价格")
    private BigDecimal price;

    @ExcelProperty("设施配置")
    private String facilities;

}
