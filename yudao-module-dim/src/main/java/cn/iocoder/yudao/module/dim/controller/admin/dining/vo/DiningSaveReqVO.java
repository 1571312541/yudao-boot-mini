package cn.iocoder.yudao.module.dim.controller.admin.dining.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 餐饮设置新增/修改 Request VO")
@Data
public class DiningSaveReqVO {

    @Schema(description = "主键", example = "1")
    private Long id;

    @Schema(description = "餐类名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "早餐")
    @NotBlank(message = "餐类名称不能为空")
    private String name;

    @Schema(description = "餐类类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "餐类类型不能为空")
    private Integer mealType;

    @Schema(description = "单价(元)", example = "15.00")
    private BigDecimal price;

    @Schema(description = "开始时间", example = "07:00")
    private String startTime;

    @Schema(description = "结束时间", example = "08:30")
    private String endTime;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    @NotNull(message = "状态不能为空")
    private Integer status;

    @Schema(description = "备注")
    private String remark;

}
