package cn.iocoder.yudao.module.dim.controller.admin.inventory.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 库存日志分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class InventoryLogPageReqVO extends PageParam {

    @Schema(description = "物品ID", example = "1")
    private Long itemId;

    @Schema(description = "操作类型", example = "1")
    private Integer operationType;

    @Schema(description = "操作单号", example = "RK20260101001")
    private String operationNo;

    @Schema(description = "审核状态：0-待审核 1-已通过 2-已驳回", example = "0")
    private Integer auditStatus;

    @Schema(description = "申请人ID", example = "1")
    private Long applicantId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
