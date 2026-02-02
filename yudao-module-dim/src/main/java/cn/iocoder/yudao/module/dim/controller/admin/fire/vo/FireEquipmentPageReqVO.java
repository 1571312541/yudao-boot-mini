package cn.iocoder.yudao.module.dim.controller.admin.fire.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 消防设备分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class FireEquipmentPageReqVO extends PageParam {

    @Schema(description = "设备名称", example = "灭火器")
    private String name;

    @Schema(description = "设备编号", example = "FE001")
    private String code;

    @Schema(description = "设备类型:1灭火器,2消火栓,3烟感器,4喷淋头,5其他", example = "1")
    private Integer type;

    @Schema(description = "楼号", example = "1")
    private Integer buildingNo;

    @Schema(description = "楼层", example = "2")
    private Integer floor;

    @Schema(description = "状态:0正常,1待检,2故障,3报废", example = "0")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
