package cn.iocoder.yudao.module.dim.dal.dataobject.room;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 房间 DO
 */
@TableName("dim_room")
@KeySequence("dim_room_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class RoomDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 楼层ID
     */
    private Long floorId;

    /**
     * 房间号
     */
    private String roomNumber;

    /**
     * 房间类型（0-单人间 1-双人间 2-多人间）
     */
    private Integer roomType;

    /**
     * 床位数
     */
    private Integer bedCount;

    /**
     * 面积（平方米）
     */
    private BigDecimal area;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 房间状态（0-空闲 1-已入住 2-维修中 3-已预定）
     */
    private Integer status;

    /**
     * 设施配置（JSON格式）
     */
    private String facilities;

    /**
     * 备注
     */
    private String remarks;

}
