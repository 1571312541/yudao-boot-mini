package cn.iocoder.yudao.module.dim.controller.admin.fire.vo;

import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 消防设备 Response VO")
@Data
public class FireEquipmentRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "关联资产ID", example = "100")
    @ExcelProperty("关联资产ID")
    private Long assetId;

    @Schema(description = "设备名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "灭火器")
    @ExcelProperty("设备名称")
    private String name;

    @Schema(description = "设备编号", example = "FE001")
    @ExcelProperty("设备编号")
    private String code;

    @Schema(description = "设备类型:1灭火器,2消火栓,3烟感器,4喷淋头,5其他", example = "1")
    @ExcelProperty("设备类型")
    private Integer type;

    @Schema(description = "数量", example = "2")
    @ExcelProperty("数量")
    private Integer quantity;

    @Schema(description = "计量单位", example = "个")
    @ExcelProperty("计量单位")
    private String measurementUnit;

    @Schema(description = "楼号", example = "1")
    @ExcelProperty("楼号")
    private Integer buildingNo;

    @Schema(description = "楼层", example = "2")
    @ExcelProperty("楼层")
    private Integer floor;

    @Schema(description = "具体位置", example = "走廊左侧")
    @ExcelProperty("具体位置")
    private String position;

    @Schema(description = "安装日期")
    @ExcelProperty("安装日期")
    private LocalDate installDate;

    @Schema(description = "到期日期")
    @ExcelProperty("到期日期")
    private LocalDate expiryDate;

    @Schema(description = "检查日期")
    @ExcelProperty("检查日期")
    private LocalDate checkDate;

    @Schema(description = "下次检查日期")
    @ExcelProperty("下次检查日期")
    private LocalDate nextCheckDate;

    @Schema(description = "维护周期(天)", example = "30")
    @ExcelProperty("维护周期(天)")
    private Integer maintenanceCycle;

    @Schema(description = "状态:0正常,1待检,2故障,3报废", example = "0")
    @ExcelProperty("状态")
    private Integer status;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String remarks;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
