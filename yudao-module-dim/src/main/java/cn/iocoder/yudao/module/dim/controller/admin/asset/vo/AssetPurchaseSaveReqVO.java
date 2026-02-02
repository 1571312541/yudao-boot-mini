package cn.iocoder.yudao.module.dim.controller.admin.asset.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

@Schema(description = "管理后台 - 资产采购新增/修改 Request VO")
@Data
public class AssetPurchaseSaveReqVO {

    @Schema(description = "主键", example = "1")
    private Long id;

    @Schema(description = "采购单号", example = "PO202601010001")
    private String code;

    @Schema(description = "采购标题", requiredMode = Schema.RequiredMode.REQUIRED, example = "办公设备采购")
    @NotBlank(message = "采购标题不能为空")
    private String title;

    @Schema(description = "采购内容/说明")
    private String content;

    @Schema(description = "采购总金额", example = "10000.00")
    private BigDecimal totalAmount;

    @Schema(description = "采购明细列表")
    @Valid
    private List<AssetPurchaseItemSaveReqVO> items;

}
