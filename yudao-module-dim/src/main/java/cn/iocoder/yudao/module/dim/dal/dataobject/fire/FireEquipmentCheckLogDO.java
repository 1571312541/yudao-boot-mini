package cn.iocoder.yudao.module.dim.dal.dataobject.fire;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 消防设备巡检日志 DO
 */
@TableName("dim_fire_equipment_check_log")
@KeySequence("dim_fire_equipment_check_log_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class FireEquipmentCheckLogDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 设备ID
     */
    private Long equipmentId;
    /**
     * 巡检结果:0正常,1故障
     */
    private Integer checkResult;
    /**
     * 巡检备注
     */
    private String checkRemark;
    /**
     * 巡检时间
     */
    private LocalDateTime checkTime;
    /**
     * 巡检人ID
     */
    private Long checkerId;
    /**
     * 巡检人姓名
     */
    private String checkerName;

}
