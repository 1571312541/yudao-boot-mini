package cn.iocoder.yudao.module.dim.controller.admin.asset.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "管理后台 - 资产持有分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class AssetHolderPageReqVO extends PageParam {

    @Schema(description = "资产ID", example = "1")
    private Long assetId;

    @Schema(description = "持有人姓名", example = "张三")
    private String holderName;

    @Schema(description = "部门ID", example = "1")
    private Long deptId;

}
