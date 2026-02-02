package cn.iocoder.yudao.module.dim.controller.admin.visitor.vo;

import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 预约访客 Response VO")
@Data
public class ReservationVisitorRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "人员类型", example = "0")
    @ExcelProperty("人员类型")
    private Integer type;

    @Schema(description = "姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @ExcelProperty("姓名")
    private String name;

    @Schema(description = "性别", example = "男")
    @ExcelProperty("性别")
    private String gender;

    @Schema(description = "出生日期")
    @ExcelProperty("出生日期")
    private LocalDate birthday;

    @Schema(description = "联系电话", example = "13800138000")
    @ExcelProperty("联系电话")
    private String phone;

    @Schema(description = "单位名称", example = "XX公司")
    @ExcelProperty("单位名称")
    private String unitName;

    @Schema(description = "证件号码", example = "110101199001011234")
    @ExcelProperty("证件号码")
    private String idNum;

    @Schema(description = "人像信息(路径)")
    private String imgInfo;

    @Schema(description = "车辆信息", example = "京A12345")
    @ExcelProperty("车辆信息")
    private String carInfo;

    @Schema(description = "随行人数", example = "2")
    @ExcelProperty("随行人数")
    private Integer visitorNum;

    @Schema(description = "被访单位", example = "综合办")
    @ExcelProperty("被访单位")
    private String visitingUnit;

    @Schema(description = "被访人ID", example = "1")
    private Long intervieweeId;

    @Schema(description = "被访人姓名", example = "李四")
    @ExcelProperty("被访人")
    private String interviewee;

    @Schema(description = "被访人电话", example = "13900139000")
    @ExcelProperty("被访人电话")
    private String intervieweePhone;

    @Schema(description = "有效期开始时间")
    @ExcelProperty("有效期开始")
    private LocalDateTime startEffectiveDate;

    @Schema(description = "有效期结束时间")
    @ExcelProperty("有效期结束")
    private LocalDateTime endEffectiveDate;

    @Schema(description = "来访事由", example = "工程施工")
    @ExcelProperty("来访事由")
    private String purpose;

    @Schema(description = "状态", example = "0")
    @ExcelProperty("状态")
    private Integer status;

    @Schema(description = "出入证卡号", example = "C001")
    @ExcelProperty("出入证卡号")
    private String cardNum;

    @Schema(description = "车辆通行证号", example = "CAR001")
    @ExcelProperty("车辆通行证号")
    private String carCardNum;

    @Schema(description = "就餐卡号", example = "D001")
    @ExcelProperty("就餐卡号")
    private String diningNum;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String remarks;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
