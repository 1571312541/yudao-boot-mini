package cn.iocoder.yudao.module.dim.controller.admin.room.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 住宿日志分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class StayLogPageReqVO extends PageParam {

    @Schema(description = "房间ID", example = "1")
    private Long roomId;

    @Schema(description = "住客记录ID", example = "1")
    private Long guestId;

    @Schema(description = "操作类型", example = "0")
    private Integer operationType;

    @Schema(description = "操作时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] operationTime;

}
