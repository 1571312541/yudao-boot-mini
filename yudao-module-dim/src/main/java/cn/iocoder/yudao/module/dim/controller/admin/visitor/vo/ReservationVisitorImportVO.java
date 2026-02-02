package cn.iocoder.yudao.module.dim.controller.admin.visitor.vo;

import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 预约访客导入 Request VO")
@Data
public class ReservationVisitorImportVO {

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("性别")
    private String gender;

    @ExcelProperty("出生日期")
    private LocalDate birthday;

    @ExcelProperty("联系电话")
    private String phone;

    @ExcelProperty("证件号码")
    private String idNum;

    @ExcelProperty("单位名称")
    private String unitName;

    @ExcelProperty("人员类型")
    private Integer type;

    @ExcelProperty("车辆信息")
    private String carInfo;

    @ExcelProperty("随行人数")
    private Integer visitorNum;

    @ExcelProperty("被访单位")
    private String visitingUnit;

    @ExcelProperty("被访人")
    private String interviewee;

    @ExcelProperty("被访人电话")
    private String intervieweePhone;

    @ExcelProperty("有效期开始")
    private LocalDateTime startEffectiveDate;

    @ExcelProperty("有效期结束")
    private LocalDateTime endEffectiveDate;

    @ExcelProperty("来访事由")
    private String purpose;

    @ExcelProperty("备注")
    private String remarks;

}
