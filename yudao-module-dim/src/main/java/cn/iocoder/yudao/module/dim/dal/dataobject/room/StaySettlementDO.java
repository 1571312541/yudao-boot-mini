package cn.iocoder.yudao.module.dim.dal.dataobject.room;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 住宿费用结算 DO
 */
@TableName("dim_stay_settlement")
@KeySequence("dim_stay_settlement_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class StaySettlementDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 住客记录ID
     */
    private Long guestId;

    /**
     * 房间ID
     */
    private Long roomId;

    /**
     * 入住日期
     */
    private LocalDateTime checkInDate;

    /**
     * 退房日期
     */
    private LocalDateTime checkOutDate;

    /**
     * 住宿天数
     */
    private Integer stayDays;

    /**
     * 房间单价
     */
    private BigDecimal roomPrice;

    /**
     * 总金额
     */
    private BigDecimal totalAmount;

    /**
     * 支付状态（0-未结算 1-已结算）
     */
    private Integer paymentStatus;

    /**
     * 结算时间
     */
    private LocalDateTime paymentTime;

    /**
     * 开票状态（0-未开票 1-已开票）
     */
    private Integer invoiceStatus;

    /**
     * 开票时间
     */
    private LocalDateTime invoiceTime;

    /**
     * 备注
     */
    private String remarks;

}
