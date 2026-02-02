package cn.iocoder.yudao.module.dim.controller.admin.visitor.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 来访日志分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class VisitLogPageReqVO extends PageParam {

    @Schema(description = "访客ID", example = "1")
    private Long visitorId;

    @Schema(description = "状态", example = "0")
    private Integer status;

    @Schema(description = "来访时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] visitDate;

}
