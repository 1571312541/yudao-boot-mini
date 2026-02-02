package cn.iocoder.yudao.module.dim.controller.admin.inventory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * 库存盘点请求 VO
 */
@Schema(description = "管理后台 - 库存盘点 Request VO")
@Data
public class InventoryCheckReqVO {

    @Schema(description = "盘点物品列表", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "盘点物品列表不能为空")
    @Valid
    private List<CheckItem> items;

    @Schema(description = "盘点备注", example = "月度例行盘点")
    private String remark;

    /**
     * 盘点物品项
     */
    @Data
    public static class CheckItem {

        @Schema(description = "物品ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        @NotNull(message = "物品ID不能为空")
        private Long itemId;

        @Schema(description = "实际库存数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "95")
        @NotNull(message = "实际库存数量不能为空")
        private BigDecimal actualQuantity;

        @Schema(description = "单项备注", example = "有5件损坏")
        private String remark;
    }

}
