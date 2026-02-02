package cn.iocoder.yudao.module.dim.controller.admin.asset.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 资产日志分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class AssetLogPageReqVO extends PageParam {

    @Schema(description = "资产ID", example = "1")
    private Long assetId;

    @Schema(description = "操作类型", example = "0")
    private Integer type;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
