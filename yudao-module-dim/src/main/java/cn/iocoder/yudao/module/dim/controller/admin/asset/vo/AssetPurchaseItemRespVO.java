package cn.iocoder.yudao.module.dim.controller.admin.asset.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 资产采购明细 Response VO")
@Data
public class AssetPurchaseItemRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "采购单ID", example = "1")
    private Long purchaseId;

    @Schema(description = "资产名称", example = "打印机")
    private String assetName;

    @Schema(description = "资产分类ID", example = "1")
    private Long categoryId;

    @Schema(description = "资产分类名称", example = "办公设备")
    private String categoryName;

    @Schema(description = "规格型号", example = "HP LaserJet Pro")
    private String specification;

    @Schema(description = "计量单位", example = "台")
    private String measurementUnit;

    @Schema(description = "采购数量", example = "5")
    private Integer quantity;

    @Schema(description = "单价", example = "2000.00")
    private BigDecimal unitPrice;

    @Schema(description = "小计金额", example = "10000.00")
    private BigDecimal totalPrice;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "入库后关联的资产ID", example = "1")
    private Long assetId;

    @Schema(description = "是否已入库", example = "false")
    private Boolean warehoused;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
