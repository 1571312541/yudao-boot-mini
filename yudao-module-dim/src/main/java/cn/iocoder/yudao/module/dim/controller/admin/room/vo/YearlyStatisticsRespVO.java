package cn.iocoder.yudao.module.dim.controller.admin.room.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "管理后台 - 年度统计响应 VO")
@Data
public class YearlyStatisticsRespVO {

    @Schema(description = "年份", example = "2026")
    private Integer year;

    @Schema(description = "月度数据")
    private List<MonthData> monthlyData;

    @Data
    public static class MonthData {

        @Schema(description = "月份", example = "1")
        private Integer month;

        @Schema(description = "入住人次", example = "50")
        private Integer checkInCount;

        @Schema(description = "退房人次", example = "45")
        private Integer checkOutCount;

        @Schema(description = "住宿天数", example = "200")
        private Integer stayDays;

    }

}
