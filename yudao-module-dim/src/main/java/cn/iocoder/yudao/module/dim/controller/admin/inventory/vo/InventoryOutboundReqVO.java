package cn.iocoder.yudao.module.dim.controller.admin.inventory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * 出库请求 VO
 */
@Schema(description = "管理后台 - 出库 Request VO")
@Data
public class InventoryOutboundReqVO {

    @Schema(description = "物品ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "物品ID不能为空")
    private Long itemId;

    @Schema(description = "操作类型：3-领用出库 4-盘亏出库 6-调拨出库", requiredMode = Schema.RequiredMode.REQUIRED, example = "3")
    @NotNull(message = "操作类型不能为空")
    private Integer operationType;

    @Schema(description = "出库数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @NotNull(message = "出库数量不能为空")
    @Positive(message = "出库数量必须大于0")
    private BigDecimal quantity;

    @Schema(description = "申请部门ID", example = "1")
    private Long applicantDeptId;

    @Schema(description = "用途", requiredMode = Schema.RequiredMode.REQUIRED, example = "日常办公使用")
    private String purpose;

    @Schema(description = "使用地点", example = "办公室A301")
    private String usageLocation;

    @Schema(description = "备注", example = "备注信息")
    private String remark;

}
