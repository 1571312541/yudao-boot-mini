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
 * 餐饮结算 DO
 */
@TableName("dim_dining_settlement")
@KeySequence("dim_dining_settlement_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class DiningSettlementDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 结算人ID
     */
    private Long userId;

    /**
     * 结算人姓名
     */
    private String userName;

    /**
     * 结算人部门
     */
    private String deptName;

    /**
     * 结算开始日期
     */
    private LocalDate startDate;

    /**
     * 结算结束日期
     */
    private LocalDate endDate;

    /**
     * 早餐次数
     */
    private Integer breakfastCount;

    /**
     * 午餐次数
     */
    private Integer lunchCount;

    /**
     * 晚餐次数
     */
    private Integer dinnerCount;

    /**
     * 总金额
     */
    private BigDecimal totalAmount;

    /**
     * 已付金额
     */
    private BigDecimal paidAmount;

    /**
     * 状态（0-未结算 1-已结算）
     */
    private Integer status;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 是否已支付
     */
    private String isPaid;

    /**
     * 是否已开票
     */
    private String isInvoiced;

}
