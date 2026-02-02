package cn.iocoder.yudao.module.dim.controller.admin.dining.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "管理后台 - 就餐记录新增/修改 Request VO")
@Data
public class DiningRecordSaveReqVO {

    @Schema(description = "主键", example = "1")
    private Long id;

    @Schema(description = "就餐人ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "就餐人不能为空")
    private Long userId;

    @Schema(description = "就餐人姓名", example = "张三")
    private String userName;

    @Schema(description = "就餐人部门", example = "综合部")
    private String deptName;

    @Schema(description = "就餐日期")
    private LocalDate diningDate;

    @Schema(description = "餐别", example = "0")
    private Integer mealType;

    @Schema(description = "就餐类型", example = "0")
    private Integer payType;

    @Schema(description = "金额", example = "15.00")
    private BigDecimal amount;

    @Schema(description = "备注")
    private String remarks;

    @Schema(description = "人员类型（0-外协, 1-实验队, 2-施工队, 3-物业, 4-本所, 5-总部）", example = "0")
    private Integer personType;

    @Schema(description = "餐卡编号", example = "C001")
    private String cardId;

    @Schema(description = "分类（0-客餐 1-桌餐）", example = "0")
    private Integer diningClass;

    @Schema(description = "关联报餐登记ID", example = "1")
    private Long registrationId;

    @Schema(description = "关联结算单ID", example = "1")
    private Long settlementId;

}
