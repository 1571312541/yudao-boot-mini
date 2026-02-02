package cn.iocoder.yudao.module.dim.controller.admin.dining.vo.statistics;

import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 费用统计导出 VO
 */
@Schema(description = "管理后台 - 费用统计导出 VO")
@Data
public class DiningExpenseStatVO {

    @Schema(description = "用户名称", example = "张三")
    @ExcelProperty("住客名称")
    private String userName;

    @Schema(description = "单位名称", example = "综合部")
    @ExcelProperty("单位名称")
    private String deptName;

    @Schema(description = "早餐报餐次数", example = "10")
    @ExcelProperty("早餐报餐次数")
    private Integer breakfastRegCount;

    @Schema(description = "午餐报餐次数", example = "15")
    @ExcelProperty("午餐报餐次数")
    private Integer lunchRegCount;

    @Schema(description = "晚餐报餐次数", example = "12")
    @ExcelProperty("晚餐报餐次数")
    private Integer dinnerRegCount;

    @Schema(description = "早餐就餐次数", example = "8")
    @ExcelProperty("早餐就餐次数")
    private Integer breakfastUsedCount;

    @Schema(description = "午餐就餐次数", example = "14")
    @ExcelProperty("午餐就餐次数")
    private Integer lunchUsedCount;

    @Schema(description = "晚餐就餐次数", example = "10")
    @ExcelProperty("晚餐就餐次数")
    private Integer dinnerUsedCount;

    @Schema(description = "总金额", example = "480.00")
    @ExcelProperty("总金额")
    private BigDecimal totalAmount;

    public DiningExpenseStatVO() {
        this.breakfastRegCount = 0;
        this.lunchRegCount = 0;
        this.dinnerRegCount = 0;
        this.breakfastUsedCount = 0;
        this.lunchUsedCount = 0;
        this.dinnerUsedCount = 0;
        this.totalAmount = BigDecimal.ZERO;
    }

}
