package cn.iocoder.yudao.module.dim.controller.admin.asset.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 资产外借新增/修改 Request VO")
@Data
public class AssetBorrowSaveReqVO {

    @Schema(description = "主键", example = "1")
    private Long id;

    @Schema(description = "资产ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "资产不能为空")
    private Long assetId;

    @Schema(description = "外借数量", example = "1")
    private Integer quantity;

    @Schema(description = "借用人ID", example = "1")
    private Long borrowerId;

    @Schema(description = "借用人姓名", example = "张三")
    private String borrowerName;

    @Schema(description = "借出日期")
    private LocalDateTime borrowDate;

    @Schema(description = "预计归还日期")
    private LocalDateTime expectedReturnDate;

    @Schema(description = "实际归还日期")
    private LocalDateTime returnDate;

    @Schema(description = "状态", example = "0")
    private Integer status;

    @Schema(description = "备注")
    private String remarks;

}
