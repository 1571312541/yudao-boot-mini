package cn.iocoder.yudao.module.dim.controller.admin.dining.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Schema(description = "管理后台 - 餐饮设置 Response VO")
@Data
public class DiningRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "餐类名称", example = "早餐")
    private String name;

    @Schema(description = "餐类类型", example = "1")
    private Integer mealType;

    @Schema(description = "单价(元)", example = "15.00")
    private BigDecimal price;

    @Schema(description = "开始时间", example = "07:00")
    private LocalTime startTime;

    @Schema(description = "结束时间", example = "08:30")
    private LocalTime endTime;

    @Schema(description = "状态", example = "0")
    private Integer status;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
