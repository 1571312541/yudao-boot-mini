package cn.iocoder.yudao.module.dim.dal.dataobject.room;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 住宿日志 DO
 */
@TableName("dim_stay_log")
@KeySequence("dim_stay_log_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class StayLogDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 房间ID
     */
    private Long roomId;

    /**
     * 住客记录ID
     */
    private Long guestId;

    /**
     * 操作类型（0-入住 1-退房 2-换房 3-续住 4-预约 5-取消预约）
     */
    private Integer operationType;

    /**
     * 操作人ID
     */
    private Long operatorId;

    /**
     * 操作人姓名
     */
    private String operatorName;

    /**
     * 操作时间
     */
    private LocalDateTime operationTime;

    /**
     * 备注
     */
    private String remarks;

}
