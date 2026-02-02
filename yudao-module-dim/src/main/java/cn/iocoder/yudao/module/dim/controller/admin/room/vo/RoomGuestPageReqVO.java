package cn.iocoder.yudao.module.dim.controller.admin.room.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 住客分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class RoomGuestPageReqVO extends PageParam {

    @Schema(description = "房间ID", example = "1")
    private Long roomId;

    @Schema(description = "住客姓名", example = "张三")
    private String guestName;

    @Schema(description = "证件号码", example = "110101199001011234")
    private String idNumber;

    @Schema(description = "住宿状态", example = "0")
    private Integer status;

    @Schema(description = "入住日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] checkInDate;

}
