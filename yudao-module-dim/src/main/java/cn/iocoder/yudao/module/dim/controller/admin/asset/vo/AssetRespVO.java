package cn.iocoder.yudao.module.dim.controller.admin.asset.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 资产 Response VO")
@Data
public class AssetRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "分类ID", example = "1")
    private Long categoryId;

    @Schema(description = "分类名称", example = "办公设备")
    private String categoryName;

    @Schema(description = "资产名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "打印机")
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

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
