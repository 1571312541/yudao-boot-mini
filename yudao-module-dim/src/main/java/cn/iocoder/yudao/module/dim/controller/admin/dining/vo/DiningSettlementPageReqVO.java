package cn.iocoder.yudao.module.dim.controller.admin.dining.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;

@Schema(description = "管理后台 - 餐饮结算分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class DiningSettlementPageReqVO extends PageParam {

    @Schema(description = "结算人ID", example = "1")
    private Long userId;

    @Schema(description = "结算人姓名", example = "张三")
    private String userName;

    @Schema(description = "状态", example = "0")
    private Integer status;

    @Schema(description = "结算开始日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    private LocalDate[] startDate;

    @Schema(description = "是否已支付", example = "N")
    private String isPaid;

    @Schema(description = "是否已开票", example = "N")
    private String isInvoiced;

}
