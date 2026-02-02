package cn.iocoder.yudao.module.dim.controller.admin.room.vo;

import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 住客 Response VO")
@Data
public class RoomGuestRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "房间ID", example = "1")
    private Long roomId;

    @Schema(description = "房间号", example = "101")
    @ExcelProperty("房间号")
    private String roomNumber;

    @Schema(description = "住客姓名", example = "张三")
    @ExcelProperty("住客姓名")
    private String guestName;

    @Schema(description = "证件类型", example = "0")
    @ExcelProperty("证件类型")
    private Integer idType;

    @Schema(description = "证件号码", example = "110101199001011234")
    @ExcelProperty("证件号码")
    private String idNumber;

    @Schema(description = "联系电话", example = "13800138000")
    @ExcelProperty("联系电话")
    private String phone;

    @Schema(description = "单位/部门", example = "综合部")
    @ExcelProperty("单位/部门")
    private String deptName;

    @Schema(description = "入住日期")
    @ExcelProperty("入住日期")
    private LocalDateTime checkInDate;

    @Schema(description = "预计离店日期")
    @ExcelProperty("预计离店日期")
    private LocalDateTime expectedCheckOutDate;

    @Schema(description = "实际离店日期")
    @ExcelProperty("实际离店日期")
    private LocalDateTime actualCheckOutDate;

    @Schema(description = "住宿状态", example = "0")
    @ExcelProperty("状态")
    private Integer status;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String remarks;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "主住客ID（同住人用）")
    private Long primaryGuestId;

    @Schema(description = "是否主住客")
    private Boolean isPrimary;

}
