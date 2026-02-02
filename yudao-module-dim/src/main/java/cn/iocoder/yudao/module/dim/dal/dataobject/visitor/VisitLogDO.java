package cn.iocoder.yudao.module.dim.dal.dataobject.visitor;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 来访日志 DO
 */
@TableName("dim_visit_log")
@KeySequence("dim_visit_log_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class VisitLogDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 访客ID
     */
    private Long visitorId;
    /**
     * 状态：0来访,1离场
     */
    private Integer status;
    /**
     * 来访时间
     */
    private LocalDateTime visitDate;
    /**
     * 离场时间
     */
    private LocalDateTime leaveDate;
    /**
     * 来访事由
     */
    private String purpose;
    /**
     * 备注
     */
    private String remarks;

}
