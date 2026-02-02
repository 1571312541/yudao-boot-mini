package cn.iocoder.yudao.module.dim.controller.admin.inventory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 库存盘点结果 VO
 */
@Schema(description = "管理后台 - 库存盘点结果 Response VO")
@Data
public class InventoryCheckRespVO {

    @Schema(description = "盘点结果明细")
    private List<CheckResult> results;

    @Schema(description = "盘盈数量")
    private int profitCount;

    @Schema(description = "盘亏数量")
    private int lossCount;

    @Schema(description = "相符数量")
    private int matchCount;

    /**
     * 单项盘点结果
     */
    @Data
    public static class CheckResult {

        @Schema(description = "物品ID")
        private Long itemId;

        @Schema(description = "物品编码")
        private String itemCode;

        @Schema(description = "物品名称")
        private String itemName;

        @Schema(description = "系统库存")
        private BigDecimal systemQuantity;

        @Schema(description = "实际库存")
        private BigDecimal actualQuantity;

        @Schema(description = "差异数量（正数盘盈，负数盘亏）")
        private BigDecimal differenceQuantity;

        @Schema(description = "盘点状态：0-相符 1-盘盈 2-盘亏")
        private Integer checkStatus;

        @Schema(description = "生成的日志ID（如有差异）")
        private Long logId;
    }

}
