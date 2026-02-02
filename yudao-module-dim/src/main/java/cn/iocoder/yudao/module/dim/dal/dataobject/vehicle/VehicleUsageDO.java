package cn.iocoder.yudao.module.dim.dal.dataobject.vehicle;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 用车记录 DO
 */
@TableName("dim_vehicle_usage")
@KeySequence("dim_vehicle_usage_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class VehicleUsageDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 车辆ID
     */
    private Long vehicleId;
    /**
     * 用车人ID
     */
    private Long userId;
    /**
     * 用车人姓名
     */
    private String userName;
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    /**
     * 目的地
     */
    private String destination;
    /**
     * 用途
     */
    private String purpose;
    /**
     * 行驶里程（公里）
     */
    private Integer mileage;
    /**
     * 状态：0使用中,1已归还
     */
    private Integer status;
    /**
     * 备注
     */
    private String remarks;

}
