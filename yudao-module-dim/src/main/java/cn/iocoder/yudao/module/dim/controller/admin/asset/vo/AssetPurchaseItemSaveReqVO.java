package cn.iocoder.yudao.module.dim.controller.admin.asset.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 资产采购明细新增/修改 Request VO")
@Data
public class AssetPurchaseItemSaveReqVO {

    @Schema(description = "主键", example = "1")
    private Long id;

    @Schema(description = "资产名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "打印机")
    @NotBlank(message = "资产名称不能为空")
    private String assetName;

    @Schema(description = "资产分类ID", example = "1")
    private Long categoryId;

    @Schema(description = "规格型号", example = "HP LaserJet Pro")
    private String specification;

    @Schema(description = "计量单位", example = "台")
    private String measurementUnit;

    @Schema(description = "采购数量", requiredMode = Schema.RequiredMode.REQUIRED, example = "5")
    @NotNull(message = "采购数量不能为空")
    private Integer quantity;

    @Schema(description = "单价", example = "2000.00")
    private BigDecimal unitPrice;

    @Schema(description = "小计金额", example = "10000.00")
    private BigDecimal totalPrice;

    @Schema(description = "备注")
    private String remark;

}
