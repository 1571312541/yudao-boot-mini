package cn.iocoder.yudao.module.dim.controller.admin.vehicle.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 公务车辆分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class VehiclePageReqVO extends PageParam {

    @Schema(description = "车辆名称", example = "奥迪A6")
    private String name;

    @Schema(description = "车牌号", example = "京A12345")
    private String plateNumber;

    @Schema(description = "状态", example = "0")
    private Integer status;

    @Schema(description = "类型", example = "0")
    private Integer type;

    @Schema(description = "驾驶人", example = "张三")
    private String driver;

    @Schema(description = "分管领导", example = "李局长")
    private String leadingOfficial;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
