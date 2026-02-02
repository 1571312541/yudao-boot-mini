package cn.iocoder.yudao.module.dim.controller.admin.asset.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 资产外借 Response VO")
@Data
public class AssetBorrowRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "资产ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long assetId;

    @Schema(description = "资产名称", example = "打印机")
    private String assetName;

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

    @Schema(description = "状态", example = "1")
    private Integer status;

    @Schema(description = "备注")
    private String remarks;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
