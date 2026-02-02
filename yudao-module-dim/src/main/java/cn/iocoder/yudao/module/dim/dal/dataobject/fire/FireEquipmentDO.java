package cn.iocoder.yudao.module.dim.dal.dataobject.fire;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 消防设备 DO
 */
@TableName("dim_fire_equipment")
@KeySequence("dim_fire_equipment_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class FireEquipmentDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 关联资产ID
     */
    private Long assetId;
    /**
     * 设备名称
     */
    private String name;
    /**
     * 设备编号
     */
    private String code;
    /**
     * 设备类型:1灭火器,2消火栓,3烟感器,4喷淋头,5其他
     */
    private Integer type;
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 计量单位
     */
    private String measurementUnit;
    /**
     * 楼号
     */
    private Integer buildingNo;
    /**
     * 楼层
     */
    private Integer floor;
    /**
     * 具体位置
     */
    private String position;
    /**
     * 安装日期
     */
    private LocalDate installDate;
    /**
     * 到期日期
     */
    private LocalDate expiryDate;
    /**
     * 检查日期
     */
    private LocalDate checkDate;
    /**
     * 下次检查日期
     */
    private LocalDate nextCheckDate;
    /**
     * 维护周期(天)
     */
    private Integer maintenanceCycle;
    /**
     * 状态:0正常,1待检,2故障,3报废
     */
    private Integer status;
    /**
     * 备注
     */
    private String remarks;

}
