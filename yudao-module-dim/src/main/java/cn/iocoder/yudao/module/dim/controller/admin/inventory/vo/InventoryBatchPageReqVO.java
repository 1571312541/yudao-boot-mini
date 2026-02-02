package cn.iocoder.yudao.module.dim.controller.admin.inventory.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "管理后台 - 库存批次分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class InventoryBatchPageReqVO extends PageParam {

    @Schema(description = "物品ID", example = "1")
    private Long itemId;

    @Schema(description = "批次号", example = "PH20260101001")
    private String batchNo;

    @Schema(description = "状态：0-正常 1-已清空", example = "0")
    private Integer status;

}
