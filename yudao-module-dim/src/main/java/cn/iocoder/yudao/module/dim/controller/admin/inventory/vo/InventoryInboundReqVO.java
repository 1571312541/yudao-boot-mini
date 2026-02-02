package cn.iocoder.yudao.module.dim.controller.admin.inventory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 入库请求 VO
 */
@Schema(description = "管理后台 - 入库 Request VO")
@Data
public class InventoryInboundReqVO {

    @Schema(description = "物品ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "物品ID不能为空")
    private Long itemId;

    @Schema(description = "操作类型：1-采购入库 2-盘盈入库 5-调拨入库", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "操作类型不能为空")
    private Integer operationType;

    @Schema(description = "入库数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    @NotNull(message = "入库数量不能为空")
    @Positive(message = "入库数量必须大于0")
    private BigDecimal quantity;

    @Schema(description = "单价（折扣后）", example = "25.00")
    private BigDecimal unitPrice;

    @Schema(description = "原价（折扣前）", example = "30.00")
    private BigDecimal originalPrice;

    @Schema(description = "折扣率(%)", example = "83.33")
    private BigDecimal discountRate;

    @Schema(description = "供应商", example = "得力文具")
    private String supplier;

    @Schema(description = "生产日期")
    private LocalDate productionDate;

    @Schema(description = "有效期至")
    private LocalDate expiryDate;

    @Schema(description = "用途/备注", example = "采购补充库存")
    private String remark;

}
