package cn.iocoder.yudao.module.dim.controller.admin.dining.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;

@Schema(description = "管理后台 - 就餐记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class DiningRecordPageReqVO extends PageParam {

    @Schema(description = "就餐人ID", example = "1")
    private Long userId;

    @Schema(description = "就餐人姓名", example = "张三")
    private String userName;

    @Schema(description = "餐别", example = "0")
    private Integer mealType;

    @Schema(description = "就餐日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    private LocalDate[] diningDate;

    @Schema(description = "人员类型（0-外协, 1-实验队, 2-施工队, 3-物业, 4-本所, 5-总部）", example = "0")
    private Integer personType;

    @Schema(description = "餐卡编号", example = "C001")
    private String cardId;

    @Schema(description = "分类（0-客餐 1-桌餐）", example = "0")
    private Integer diningClass;

    @Schema(description = "关联结算单ID", example = "1")
    private Long settlementId;

}
