package cn.iocoder.yudao.module.dim.controller.admin.room.vo;

import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 住宿结算 Response VO")
@Data
public class StaySettlementRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "住客记录ID", example = "1")
    private Long guestId;

    @Schema(description = "住客姓名", example = "张三")
    @ExcelProperty("住客姓名")
    private String guestName;

    @Schema(description = "房间ID", example = "1")
    private Long roomId;

    @Schema(description = "房间号", example = "101")
    @ExcelProperty("房间号")
    private String roomNumber;

    @Schema(description = "入住日期")
    @ExcelProperty("入住日期")
    private LocalDateTime checkInDate;

    @Schema(description = "退房日期")
    @ExcelProperty("退房日期")
    private LocalDateTime checkOutDate;

    @Schema(description = "住宿天数", example = "3")
    @ExcelProperty("住宿天数")
    private Integer stayDays;

    @Schema(description = "房间单价", example = "200.00")
    @ExcelProperty("房间单价")
    private BigDecimal roomPrice;

    @Schema(description = "总金额", example = "600.00")
    @ExcelProperty("总金额")
    private BigDecimal totalAmount;

    @Schema(description = "支付状态", example = "0")
    @ExcelProperty("支付状态")
    private Integer paymentStatus;

    @Schema(description = "结算时间")
    @ExcelProperty("结算时间")
    private LocalDateTime paymentTime;

    @Schema(description = "开票状态", example = "0")
    @ExcelProperty("开票状态")
    private Integer invoiceStatus;

    @Schema(description = "开票时间")
    @ExcelProperty("开票时间")
    private LocalDateTime invoiceTime;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String remarks;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
