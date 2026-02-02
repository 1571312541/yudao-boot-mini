package cn.iocoder.yudao.module.dim.controller.admin.vehicle.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 用车记录 Response VO")
@Data
public class VehicleUsageRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "车辆ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long vehicleId;

    @Schema(description = "车牌号", example = "京A12345")
    private String plateNumber;

    @Schema(description = "用车人ID", example = "1")
    private Long userId;

    @Schema(description = "用车人姓名", example = "张三")
    private String userName;

    @Schema(description = "开始时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime startTime;

    @Schema(description = "结束时间")
    private LocalDateTime endTime;

    @Schema(description = "目的地", example = "北京")
    private String destination;

    @Schema(description = "用途", example = "公务出行")
    private String purpose;

    @Schema(description = "行驶里程", example = "100")
    private Integer mileage;

    @Schema(description = "状态", example = "0")
    private Integer status;

    @Schema(description = "备注")
    private String remarks;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
