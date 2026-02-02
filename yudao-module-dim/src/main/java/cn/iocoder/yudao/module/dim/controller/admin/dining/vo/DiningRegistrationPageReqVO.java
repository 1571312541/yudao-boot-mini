package cn.iocoder.yudao.module.dim.controller.admin.dining.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;

@Schema(description = "管理后台 - 报餐登记分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class DiningRegistrationPageReqVO extends PageParam {

    @Schema(description = "报餐人ID", example = "1")
    private Long userId;

    @Schema(description = "报餐人姓名", example = "张三")
    private String userName;

    @Schema(description = "餐别", example = "0")
    private Integer mealType;

    @Schema(description = "状态", example = "0")
    private Integer status;

    @Schema(description = "报餐日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    private LocalDate[] registrationDate;

    @Schema(description = "餐卡编号", example = "C001")
    private String cardId;

    @Schema(description = "是否已就餐（0-未就餐 1-已就餐）", example = "0")
    private Integer used;

    @Schema(description = "分类（0-客餐 1-桌餐）", example = "0")
    private Integer diningClass;

    @Schema(description = "关联结算单ID", example = "1")
    private Long settlementId;

    @Schema(description = "是否已支付", example = "N")
    private String isPaid;

    @Schema(description = "是否已开票", example = "N")
    private String isInvoiced;

    @Schema(description = "接待人", example = "李四")
    private String receptionist;

}
