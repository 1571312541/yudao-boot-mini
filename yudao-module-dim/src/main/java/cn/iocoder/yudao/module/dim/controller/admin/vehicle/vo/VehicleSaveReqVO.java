package cn.iocoder.yudao.module.dim.controller.admin.vehicle.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Schema(description = "管理后台 - 公务车辆新增/修改 Request VO")
@Data
public class VehicleSaveReqVO {

    @Schema(description = "主键", example = "1")
    private Long id;

    @Schema(description = "车辆名称", example = "奥迪A6")
    private String name;

    @Schema(description = "车牌号", requiredMode = Schema.RequiredMode.REQUIRED, example = "京A12345")
    @NotBlank(message = "车牌号不能为空")
    private String plateNumber;

    @Schema(description = "品牌型号", example = "奥迪A6L")
    private String brand;

    @Schema(description = "颜色", example = "黑色")
    private String color;

    @Schema(description = "购置日期")
    private LocalDate purchaseDate;

    @Schema(description = "驾驶人", example = "张三")
    private String driver;

    @Schema(description = "驾驶人电话", example = "13800138000")
    private String driverPhone;

    @Schema(description = "所属部门", example = "综合办")
    private String department;

    @Schema(description = "类型", example = "0")
    private Integer type;

    @Schema(description = "状态", example = "0")
    private Integer status;

    @Schema(description = "备注")
    private String remarks;

    @Schema(description = "分管领导", example = "李局长")
    private String leadingOfficial;

    @Schema(description = "维保周期", example = "6个月")
    private String maintenancePeriod;

    @Schema(description = "维保里程（公里）", example = "5000")
    private Integer maintenanceMileage;

    @Schema(description = "累计行驶里程（公里）", example = "12000")
    private Integer totalMileage;

}
