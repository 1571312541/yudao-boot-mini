package cn.iocoder.yudao.module.dim.dal.dataobject.dining;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 报餐登记 DO
 */
@TableName("dim_dining_registration")
@KeySequence("dim_dining_registration_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class DiningRegistrationDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 报餐人ID
     */
    private Long userId;

    /**
     * 报餐人姓名
     */
    private String userName;

    /**
     * 报餐人部门
     */
    private String deptName;

    /**
     * 报餐日期
     */
    private LocalDate registrationDate;

    /**
     * 餐别（0-早餐 1-午餐 2-晚餐）
     */
    private Integer mealType;

    /**
     * 人数
     */
    private Integer guestCount;

    /**
     * 状态（0-待确认 1-已确认 2-已取消）
     */
    private Integer status;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 餐卡编号
     */
    private String cardId;

    /**
     * 是否已就餐（0-未就餐 1-已就餐）
     */
    private Integer used;

    /**
     * 分类（0-客餐 1-桌餐）
     */
    private Integer diningClass;

    /**
     * 关联结算单ID
     */
    private Long settlementId;

    /**
     * 是否已支付
     */
    private String isPaid;

    /**
     * 是否已开票
     */
    private String isInvoiced;

    /**
     * 接待人
     */
    private String receptionist;

    /**
     * 人员类型（0-外协, 1-实验队, 2-施工队, 3-物业, 4-本所, 5-总部）
     */
    private Integer personType;

}
