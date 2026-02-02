package cn.iocoder.yudao.module.dim.controller.admin.dining.vo.statistics;

import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 按单位统计用餐数据 Response VO
 */
@Schema(description = "管理后台 - 按单位统计用餐数据 Response VO")
@Data
public class DiningStatsByDeptRespVO {

    @Schema(description = "单位名称", example = "综合部")
    @ExcelProperty("单位名称")
    private String deptName;

    @Schema(description = "早餐人次", example = "30")
    @ExcelProperty("早餐人次")
    private Integer breakfastCount;

    @Schema(description = "午餐人次", example = "50")
    @ExcelProperty("午餐人次")
    private Integer lunchCount;

    @Schema(description = "晚餐人次", example = "40")
    @ExcelProperty("晚餐人次")
    private Integer dinnerCount;

    @Schema(description = "总人次", example = "120")
    @ExcelProperty("总人次")
    private Integer totalCount;

    @Schema(description = "总金额", example = "1800.00")
    @ExcelProperty("总金额")
    private BigDecimal totalAmount;

    public DiningStatsByDeptRespVO() {
        this.breakfastCount = 0;
        this.lunchCount = 0;
        this.dinnerCount = 0;
        this.totalCount = 0;
        this.totalAmount = BigDecimal.ZERO;
    }

}
