package cn.iocoder.yudao.module.dim.controller.admin.visitor.vo;

import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Schema(description = "管理后台 - 访客导入 Request VO")
@Data
public class VisitorImportVO {

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

    @ExcelProperty("备注")
    private String remarks;

    @ExcelProperty("车辆通行证号")
    private String carCardNum;

}
