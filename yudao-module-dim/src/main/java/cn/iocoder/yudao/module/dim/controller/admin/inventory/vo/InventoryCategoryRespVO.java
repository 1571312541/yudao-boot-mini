package cn.iocoder.yudao.module.dim.controller.admin.inventory.vo;

import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 库存分类 Response VO")
@Data
public class InventoryCategoryRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "父级ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    @ExcelProperty("父级ID")
    private Long parentId;

    @Schema(description = "分类名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "办公用品")
    @ExcelProperty("分类名称")
    private String name;

    @Schema(description = "分类类型：1-物资 2-耗材 0-通用", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("分类类型")
    private Integer type;

    @Schema(description = "排序", example = "0")
    @ExcelProperty("排序")
    private Integer sort;

    @Schema(description = "状态：0-正常 1-停用", example = "0")
    @ExcelProperty("状态")
    private Integer status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
