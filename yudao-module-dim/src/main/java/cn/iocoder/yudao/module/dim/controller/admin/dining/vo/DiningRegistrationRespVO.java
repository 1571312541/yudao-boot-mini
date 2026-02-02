package cn.iocoder.yudao.module.dim.controller.admin.dining.vo;

import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 报餐登记 Response VO")
@Data
public class DiningRegistrationRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "报餐人ID", example = "1")
    private Long userId;

    @Schema(description = "报餐人姓名", example = "张三")
    @ExcelProperty("报餐人")
    private String userName;

    @Schema(description = "报餐人部门", example = "综合部")
    @ExcelProperty("部门")
    private String deptName;

    @Schema(description = "报餐日期")
    @ExcelProperty("报餐日期")
    private LocalDate registrationDate;

    @Schema(description = "餐别", example = "0")
    @ExcelProperty("餐别")
    private Integer mealType;

    @Schema(description = "人数", example = "1")
    @ExcelProperty("人数")
    private Integer guestCount;

    @Schema(description = "状态", example = "0")
    @ExcelProperty("状态")
    private Integer status;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String remarks;

    @Schema(description = "餐卡编号", example = "C001")
    @ExcelProperty("餐卡编号")
    private String cardId;

    @Schema(description = "是否已就餐（0-未就餐 1-已就餐）", example = "0")
    @ExcelProperty("是否已就餐")
    private Integer used;

    @Schema(description = "分类（0-客餐 1-桌餐）", example = "0")
    @ExcelProperty("分类")
    private Integer diningClass;

    @Schema(description = "关联结算单ID", example = "1")
    private Long settlementId;

    @Schema(description = "是否已支付", example = "N")
    @ExcelProperty("是否已支付")
    private String isPaid;

    @Schema(description = "是否已开票", example = "N")
    @ExcelProperty("是否已开票")
    private String isInvoiced;

    @Schema(description = "接待人", example = "李四")
    @ExcelProperty("接待人")
    private String receptionist;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
