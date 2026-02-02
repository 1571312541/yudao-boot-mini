package cn.iocoder.yudao.module.dim.controller.admin.dining.vo.statistics;

import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 每月用餐统计 VO（用于年度报表）
 */
@Schema(description = "管理后台 - 每月用餐统计 VO")
@Data
public class DiningMonthlyStatVO {

    @Schema(description = "月份", example = "1")
    @ExcelProperty("月份")
    private Integer month;

    @Schema(description = "早餐人次", example = "600")
    @ExcelProperty("早餐人次")
    private Integer breakfastCount;

    @Schema(description = "午餐人次", example = "900")
    @ExcelProperty("午餐人次")
    private Integer lunchCount;

    @Schema(description = "晚餐人次", example = "700")
    @ExcelProperty("晚餐人次")
    private Integer dinnerCount;

    @Schema(description = "总人次", example = "2200")
    @ExcelProperty("总人次")
    private Integer totalCount;

    @Schema(description = "总金额", example = "33000.00")
    @ExcelProperty("总金额")
    private BigDecimal totalAmount;

    public DiningMonthlyStatVO() {
        this.breakfastCount = 0;
        this.lunchCount = 0;
        this.dinnerCount = 0;
        this.totalCount = 0;
        this.totalAmount = BigDecimal.ZERO;
    }

}
