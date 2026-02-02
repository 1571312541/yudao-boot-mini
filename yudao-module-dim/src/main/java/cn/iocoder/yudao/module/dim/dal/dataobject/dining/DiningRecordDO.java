package cn.iocoder.yudao.module.dim.dal.dataobject.dining;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 就餐记录 DO
 */
@TableName("dim_dining_record")
@KeySequence("dim_dining_record_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class DiningRecordDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 就餐人ID
     */
    private Long userId;

    /**
     * 就餐人姓名
     */
    private String userName;

    /**
     * 就餐人部门
     */
    private String deptName;

    /**
     * 就餐日期
     */
    private LocalDate diningDate;

    /**
     * 餐别（0-早餐 1-午餐 2-晚餐）
     */
    private Integer mealType;

    /**
     * 就餐类型（0-刷卡 1-现金 2-记账）
     */
    private Integer payType;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 人员类型（0-外协, 1-实验队, 2-施工队, 3-物业, 4-本所, 5-总部）
     */
    private Integer personType;

    /**
     * 餐卡编号
     */
    private String cardId;

    /**
     * 分类（0-客餐 1-桌餐）
     */
    private Integer diningClass;

    /**
     * 关联报餐登记ID
     */
    private Long registrationId;

    /**
     * 关联结算单ID
     */
    private Long settlementId;

}
