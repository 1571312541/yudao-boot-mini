package cn.iocoder.yudao.module.dim.controller.admin.room.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 住客新增/修改 Request VO")
@Data
public class RoomGuestSaveReqVO {

    @Schema(description = "主键", example = "1")
    private Long id;

    @Schema(description = "房间ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "房间不能为空")
    private Long roomId;

    @Schema(description = "住客姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotBlank(message = "住客姓名不能为空")
    private String guestName;

    @Schema(description = "证件类型", example = "0")
    private Integer idType;

    @Schema(description = "证件号码", example = "110101199001011234")
    private String idNumber;

    @Schema(description = "联系电话", example = "13800138000")
    private String phone;

    @Schema(description = "单位/部门", example = "综合部")
    private String deptName;

    @Schema(description = "入住日期")
    private LocalDateTime checkInDate;

    @Schema(description = "预计离店日期")
    private LocalDateTime expectedCheckOutDate;

    @Schema(description = "实际离店日期")
    private LocalDateTime actualCheckOutDate;

    @Schema(description = "住宿状态", example = "0")
    private Integer status;

    @Schema(description = "备注")
    private String remarks;

    @Schema(description = "同住人列表（入住时可一并添加同住人）")
    private List<CoGuestVO> coGuests;

    /**
     * 同住人信息
     */
    @Data
    public static class CoGuestVO {
        @Schema(description = "住客姓名", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "同住人姓名不能为空")
        private String guestName;

        @Schema(description = "证件类型", example = "0")
        private Integer idType;

        @Schema(description = "证件号码")
        private String idNumber;

        @Schema(description = "联系电话")
        private String phone;

        @Schema(description = "单位/部门")
        private String deptName;
    }

}
