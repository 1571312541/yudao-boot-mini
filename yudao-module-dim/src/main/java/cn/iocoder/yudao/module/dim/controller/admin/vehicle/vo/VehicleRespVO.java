package cn.iocoder.yudao.module.dim.controller.admin.vehicle.vo;

import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 公务车辆 Response VO")
@Data
public class VehicleRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "车辆名称", example = "奥迪A6")
    @ExcelProperty("车辆名称")
    private String name;

    @Schema(description = "车牌号", requiredMode = Schema.RequiredMode.REQUIRED, example = "京A12345")
    @ExcelProperty("车牌号")
    private String plateNumber;

    @Schema(description = "品牌型号", example = "奥迪A6L")
    @ExcelProperty("品牌型号")
    private String brand;

    @Schema(description = "颜色", example = "黑色")
    @ExcelProperty("颜色")
    private String color;

    @Schema(description = "购置日期")
    @ExcelProperty("购置日期")
    private LocalDate purchaseDate;

    @Schema(description = "驾驶人", example = "张三")
    @ExcelProperty("驾驶人")
    private String driver;

    @Schema(description = "驾驶人电话", example = "13800138000")
    @ExcelProperty("驾驶人电话")
    private String driverPhone;

    @Schema(description = "所属部门", example = "综合办")
    @ExcelProperty("所属部门")
    private String department;

    @Schema(description = "类型", example = "0")
    @ExcelProperty("类型")
    private Integer type;

    @Schema(description = "状态", example = "0")
    @ExcelProperty("状态")
    private Integer status;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String remarks;

    @Schema(description = "分管领导", example = "李局长")
    @ExcelProperty("分管领导")
    private String leadingOfficial;

    @Schema(description = "维保周期", example = "6个月")
    @ExcelProperty("维保周期")
    private String maintenancePeriod;

    @Schema(description = "维保里程（公里）", example = "5000")
    @ExcelProperty("维保里程")
    private Integer maintenanceMileage;

    @Schema(description = "累计行驶里程（公里）", example = "12000")
    @ExcelProperty("累计里程")
    private Integer totalMileage;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
