package cn.iocoder.yudao.module.dim.controller.admin.visitor.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 刷卡记录 Request VO")
@Data
public class CardRecordReqVO {

    @Schema(description = "员工编号/卡号", example = "EMP001")
    private String employeeNumber;

    @Schema(description = "设备IP", example = "192.168.1.100")
    private String deviceIp;

}
