package cn.iocoder.yudao.module.dim.controller.admin.asset.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 资产采购 Response VO")
@Data
public class AssetPurchaseRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "采购单号", example = "PO202601010001")
    private String code;

    @Schema(description = "采购标题", example = "办公设备采购")
    private String title;

    @Schema(description = "采购内容/说明")
    private String content;

    @Schema(description = "采购总金额", example = "10000.00")
    private BigDecimal totalAmount;

    @Schema(description = "状态", example = "0")
    private Integer status;

    @Schema(description = "申请人ID", example = "1")
    private Long applicantId;

    @Schema(description = "申请人姓名", example = "张三")
    private String applicantName;

    @Schema(description = "申请部门ID", example = "1")
    private Long applicantDeptId;

    @Schema(description = "申请时间")
    private LocalDateTime applyTime;

    @Schema(description = "审核人ID", example = "2")
    private Long auditorId;

    @Schema(description = "审核人姓名", example = "李四")
    private String auditorName;

    @Schema(description = "审核时间")
    private LocalDateTime auditTime;

    @Schema(description = "审核备注")
    private String auditRemark;

    @Schema(description = "流程实例ID")
    private String processInstanceId;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "采购明细列表")
    private List<AssetPurchaseItemRespVO> items;

}
