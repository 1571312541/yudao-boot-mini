package cn.iocoder.yudao.module.dim.controller.admin.asset.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "管理后台 - 资产新增/修改 Request VO")
@Data
public class AssetSaveReqVO {

    @Schema(description = "主键", example = "1")
    private Long id;

    @Schema(description = "分类ID", example = "1")
    private Long categoryId;

    @Schema(description = "资产名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "打印机")
    @NotBlank(message = "资产名称不能为空")
    private String name;

    @Schema(description = "资产编号", example = "ZC001")
    private String code;

    @Schema(description = "计量单位", example = "台")
    private String measurementUnit;

    @Schema(description = "库存数量", example = "10")
    private Integer inventory;

    @Schema(description = "单价", example = "2000.00")
    private BigDecimal price;

    @Schema(description = "购置日期")
    private LocalDate purchaseDate;

    @Schema(description = "状态", example = "1")
    private Integer status;

}
