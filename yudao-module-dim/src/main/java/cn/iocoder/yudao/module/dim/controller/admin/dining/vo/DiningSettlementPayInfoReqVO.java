package cn.iocoder.yudao.module.dim.controller.admin.dining.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 餐饮结算支付状态更新 Request VO")
@Data
public class DiningSettlementPayInfoReqVO {

    @Schema(description = "结算单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "结算单ID不能为空")
    private Long id;

    @Schema(description = "是否已支付", example = "Y")
    private String isPaid;

    @Schema(description = "是否已开票", example = "Y")
    private String isInvoiced;

}
