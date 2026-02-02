package cn.iocoder.yudao.module.dim.controller.admin.dining.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Schema(description = "管理后台 - 餐饮结算自动创建 Request VO")
@Data
public class DiningSettlementAutoCreateReqVO {

    @Schema(description = "用户姓名", example = "张三")
    private String userName;

    @Schema(description = "单位名称", example = "综合部")
    private String deptName;

    @Schema(description = "开始日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "开始日期不能为空")
    private LocalDate startDate;

    @Schema(description = "结束日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "结束日期不能为空")
    private LocalDate endDate;

    @Schema(description = "是否已支付", example = "N")
    private String isPaid;

    @Schema(description = "是否已开票", example = "N")
    private String isInvoiced;

}
