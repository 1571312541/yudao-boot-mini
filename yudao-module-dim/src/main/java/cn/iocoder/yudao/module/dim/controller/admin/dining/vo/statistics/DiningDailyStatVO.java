package cn.iocoder.yudao.module.dim.controller.admin.dining.vo.statistics;

import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 每日用餐统计 VO
 */
@Schema(description = "管理后台 - 每日用餐统计 VO")
@Data
public class DiningDailyStatVO {

    @Schema(description = "日期")
    @ExcelProperty("日期")
    private LocalDate date;

    @Schema(description = "早餐人次", example = "20")
    @ExcelProperty("早餐人次")
    private Integer breakfastCount;

    @Schema(description = "午餐人次", example = "35")
    @ExcelProperty("午餐人次")
    private Integer lunchCount;

    @Schema(description = "晚餐人次", example = "25")
    @ExcelProperty("晚餐人次")
    private Integer dinnerCount;

    @Schema(description = "总人次", example = "80")
    @ExcelProperty("总人次")
    private Integer totalCount;

    @Schema(description = "总金额", example = "1200.00")
    @ExcelProperty("总金额")
    private BigDecimal totalAmount;

    public DiningDailyStatVO() {
        this.breakfastCount = 0;
        this.lunchCount = 0;
        this.dinnerCount = 0;
        this.totalCount = 0;
        this.totalAmount = BigDecimal.ZERO;
    }

}
