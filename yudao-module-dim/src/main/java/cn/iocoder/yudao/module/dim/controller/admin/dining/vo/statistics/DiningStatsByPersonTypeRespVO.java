package cn.iocoder.yudao.module.dim.controller.admin.dining.vo.statistics;

import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 按人员类型统计用餐数据 Response VO
 */
@Schema(description = "管理后台 - 按人员类型统计用餐数据 Response VO")
@Data
public class DiningStatsByPersonTypeRespVO {

    @Schema(description = "人员类型（0-外协, 1-实验队, 2-施工队, 3-物业, 4-本所, 5-总部）", example = "0")
    private Integer personType;

    @Schema(description = "人员类型名称", example = "外协")
    @ExcelProperty("人员类型")
    private String personTypeName;

    @Schema(description = "早餐人次", example = "50")
    @ExcelProperty("早餐人次")
    private Integer breakfastCount;

    @Schema(description = "午餐人次", example = "80")
    @ExcelProperty("午餐人次")
    private Integer lunchCount;

    @Schema(description = "晚餐人次", example = "60")
    @ExcelProperty("晚餐人次")
    private Integer dinnerCount;

    @Schema(description = "总人次", example = "190")
    @ExcelProperty("总人次")
    private Integer totalCount;

    @Schema(description = "总金额", example = "2850.00")
    @ExcelProperty("总金额")
    private BigDecimal totalAmount;

    public DiningStatsByPersonTypeRespVO() {
        this.breakfastCount = 0;
        this.lunchCount = 0;
        this.dinnerCount = 0;
        this.totalCount = 0;
        this.totalAmount = BigDecimal.ZERO;
    }

    /**
     * 根据人员类型获取名称
     */
    public static String getPersonTypeName(Integer personType) {
        if (personType == null) {
            return "未知";
        }
        switch (personType) {
            case 0:
                return "外协";
            case 1:
                return "实验队";
            case 2:
                return "施工队";
            case 3:
                return "物业";
            case 4:
                return "本所";
            case 5:
                return "总部";
            default:
                return "未知";
        }
    }

}
