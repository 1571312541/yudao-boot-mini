package cn.iocoder.yudao.module.dim.controller.admin.room.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "管理后台 - 房间分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class RoomPageReqVO extends PageParam {

    @Schema(description = "楼层ID", example = "1")
    private Long floorId;

    @Schema(description = "房间号", example = "101")
    private String roomNumber;

    @Schema(description = "房间类型", example = "0")
    private Integer roomType;

    @Schema(description = "房间状态", example = "0")
    private Integer status;

}
