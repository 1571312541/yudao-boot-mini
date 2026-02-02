package cn.iocoder.yudao.module.dim.controller.admin.inventory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 库存物品新增/修改 Request VO")
@Data
public class InventoryItemSaveReqVO {

    @Schema(description = "主键", example = "1")
    private Long id;

    @Schema(description = "物品编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "WZ000001")
    @NotBlank(message = "物品编码不能为空")
    private String code;

    @Schema(description = "物品名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "A4纸")
    @NotBlank(message = "物品名称不能为空")
    private String name;

    @Schema(description = "物品类型：1-物资 2-耗材", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "物品类型不能为空")
    private Integer type;

    @Schema(description = "分类ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "分类ID不能为空")
    private Long categoryId;

    @Schema(description = "仓库ID", example = "1")
    private Long warehouseId;

    @Schema(description = "规格型号", example = "500张/包")
    private String spec;

    @Schema(description = "计量单位", requiredMode = Schema.RequiredMode.REQUIRED, example = "包")
    @NotBlank(message = "计量单位不能为空")
    private String unit;

    @Schema(description = "品牌", example = "得力")
    private String brand;

    @Schema(description = "生产厂家", example = "得力集团")
    private String manufacturer;

    @Schema(description = "最小库存预警值", example = "10")
    private BigDecimal minQuantity;

    @Schema(description = "最大库存预警值", example = "1000")
    private BigDecimal maxQuantity;

    @Schema(description = "单价", example = "25.00")
    private BigDecimal unitPrice;

    @Schema(description = "状态：0-正常 1-停用", example = "0")
    private Integer status;

    @Schema(description = "备注", example = "常用办公耗材")
    private String remark;

    @Schema(description = "图片URL", example = "https://example.com/a4.jpg")
    private String imageUrl;

}
