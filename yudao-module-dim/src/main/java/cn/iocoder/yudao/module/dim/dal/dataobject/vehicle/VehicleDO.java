package cn.iocoder.yudao.module.dim.dal.dataobject.vehicle;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 公务车辆 DO
 */
@TableName("dim_vehicle")
@KeySequence("dim_vehicle_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class VehicleDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 车辆名称
     */
    private String name;
    /**
     * 车牌号
     */
    private String plateNumber;
    /**
     * 品牌型号
     */
    private String brand;
    /**
     * 颜色
     */
    private String color;
    /**
     * 购置日期
     */
    private LocalDate purchaseDate;
    /**
     * 驾驶人
     */
    private String driver;
    /**
     * 驾驶人电话
     */
    private String driverPhone;
    /**
     * 所属部门
     */
    private String department;
    /**
     * 类型：0普通车,1特种车
     */
    private Integer type;
    /**
     * 状态：0空闲,1使用中,2维修中
     */
    private Integer status;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 分管领导
     */
    private String leadingOfficial;
    /**
     * 维保周期（如：6个月、5000公里）
     */
    private String maintenancePeriod;
    /**
     * 维保里程（公里），达到此里程需维保
     */
    private Integer maintenanceMileage;
    /**
     * 累计行驶里程（公里）
     */
    private Integer totalMileage;

}
