package cn.iocoder.yudao.module.dim.controller.admin.dining.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Schema(description = "管理后台 - 报餐登记新增/修改 Request VO")
@Data
public class DiningRegistrationSaveReqVO {

    @Schema(description = "主键", example = "1")
    private Long id;

    @Schema(description = "报餐人ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "报餐人不能为空")
    private Long userId;

    @Schema(description = "报餐人姓名", example = "张三")
    private String userName;

    @Schema(description = "报餐人部门", example = "综合部")
    private String deptName;

    @Schema(description = "报餐日期")
    private LocalDate registrationDate;

    @Schema(description = "餐别", example = "0")
    private Integer mealType;

    @Schema(description = "人数", example = "1")
    private Integer guestCount;

    @Schema(description = "状态", example = "0")
    private Integer status;

    @Schema(description = "备注")
    private String remarks;

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
