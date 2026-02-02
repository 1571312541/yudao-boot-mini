package cn.iocoder.yudao.module.dim.dal.dataobject.visitor;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 访客信息 DO
 */
@TableName("dim_visitor")
@KeySequence("dim_visitor_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class VisitorDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 人员类型
     */
    private Integer type;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private String gender;
    /**
     * 出生日期
     */
    private LocalDate birthday;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 单位名称
     */
    private String unitName;
    /**
     * 证件号码
     */
    private String idNum;
    /**
     * 人像信息(路径)
     */
    private String imgInfo;
    /**
     * 车辆信息
     */
    private String carInfo;
    /**
     * 随行人数
     */
    private Integer visitorNum;
    /**
     * 被访单位
     */
    private String visitingUnit;
    /**
     * 被访人ID
     */
    private Long intervieweeId;
    /**
     * 被访人姓名
     */
    private String interviewee;
    /**
     * 被访人电话
     */
    private String intervieweePhone;
    /**
     * 登记日期
     */
    private LocalDateTime regDate;
    /**
     * 有效日期
     */
    private LocalDateTime effectiveDate;
    /**
     * 离场日期
     */
    private LocalDateTime departureDate;
    /**
     * 出入证卡号
     */
    private String cardNum;
    /**
     * 车辆通行证号
     */
    private String carCardNum;
    /**
     * 就餐卡号
     */
    private String diningNum;
    /**
     * 备注
     */
    private String remarks;

}
