package cn.iocoder.yudao.module.dim.controller.admin.vehicle.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 用车记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class VehicleUsagePageReqVO extends PageParam {

    @Schema(description = "车辆ID", example = "1")
    private Long vehicleId;

    @Schema(description = "用车人姓名", example = "张三")
    private String userName;

    @Schema(description = "状态", example = "0")
    private Integer status;

    @Schema(description = "开始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] startTime;

}
