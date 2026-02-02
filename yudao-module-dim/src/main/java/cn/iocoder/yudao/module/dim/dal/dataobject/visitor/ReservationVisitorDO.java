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
 * 预约访客 DO
 */
@TableName("dim_reservation_visitor")
@KeySequence("dim_reservation_visitor_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class ReservationVisitorDO extends BaseDO {

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
     * 有效期开始时间
     */
    private LocalDateTime startEffectiveDate;
    /**
     * 有效期结束时间
     */
    private LocalDateTime endEffectiveDate;
    /**
     * 来访事由
     */
    private String purpose;
    /**
     * 状态：0待来访,1已来访,2已过期,3已取消
     */
    private Integer status;
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
