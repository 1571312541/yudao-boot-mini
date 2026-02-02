package cn.iocoder.yudao.module.dim.controller.admin.inventory.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "管理后台 - 库存物品分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class InventoryItemPageReqVO extends PageParam {

    @Schema(description = "物品编码", example = "WZ000001")
    private String code;

    @Schema(description = "物品名称", example = "A4纸")
    private String name;

    @Schema(description = "物品类型：1-物资 2-耗材", example = "1")
    private Integer type;

    @Schema(description = "分类ID", example = "1")
    private Long categoryId;

    @Schema(description = "仓库ID", example = "1")
    private Long warehouseId;

    @Schema(description = "状态：0-正常 1-停用", example = "0")
    private Integer status;

}
