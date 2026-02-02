package cn.iocoder.yudao.module.dim.controller.admin.asset.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 资产外借分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class AssetBorrowPageReqVO extends PageParam {

    @Schema(description = "资产ID", example = "1")
    private Long assetId;

    @Schema(description = "借用人姓名", example = "张三")
    private String borrowerName;

    @Schema(description = "状态", example = "1")
    private Integer status;

    @Schema(description = "借出日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] borrowDate;

}
