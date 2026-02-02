package cn.iocoder.yudao.module.dim.controller.admin.inventory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 库存统计相关 VO
 */
public class InventoryStatisticsVO {

    /**
     * 概览统计
     */
    @Schema(description = "管理后台 - 库存统计概览")
    @Data
    public static class OverviewVO {

        @Schema(description = "物品总数")
        private Integer totalItems;

        @Schema(description = "物资数量")
        private Integer materialCount;

        @Schema(description = "耗材数量")
        private Integer consumableCount;

        @Schema(description = "库存总量")
        private BigDecimal totalQuantity;

        @Schema(description = "库存总金额")
        private BigDecimal totalAmount;

        @Schema(description = "低库存预警数")
        private Integer lowStockCount;

        @Schema(description = "高库存预警数")
        private Integer highStockCount;

        @Schema(description = "待审核数")
        private Integer pendingAuditCount;
    }

    /**
     * 库存趋势数据
     */
    @Schema(description = "管理后台 - 库存趋势数据")
    @Data
    public static class TrendVO {

        @Schema(description = "日期列表")
        private List<String> dates;

        @Schema(description = "入库数量")
        private List<BigDecimal> inQuantities;

        @Schema(description = "出库数量")
        private List<BigDecimal> outQuantities;
    }

    /**
     * 分类分布
     */
    @Schema(description = "管理后台 - 分类分布数据")
    @Data
    public static class CategoryDistVO {

        @Schema(description = "分类名称")
        private String name;

        @Schema(description = "物品数量")
        private Integer count;

        @Schema(description = "库存金额")
        private BigDecimal amount;
    }

    /**
     * 月度出入库统计
     */
    @Schema(description = "管理后台 - 月度出入库统计")
    @Data
    public static class MonthlyOperationVO {

        @Schema(description = "月份列表")
        private List<String> months;

        @Schema(description = "入库金额")
        private List<BigDecimal> inAmounts;

        @Schema(description = "出库金额")
        private List<BigDecimal> outAmounts;
    }

}
