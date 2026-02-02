package cn.iocoder.yudao.module.dim.dal.dataobject.room;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 楼层 DO
 */
@TableName("dim_floor")
@KeySequence("dim_floor_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class FloorDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 楼栋ID
     */
    private Long buildingId;

    /**
     * 楼层名称
     */
    private String name;

    /**
     * 楼层编号
     */
    private Integer floorNumber;

    /**
     * 房间数量
     */
    private Integer roomCount;

    /**
     * 状态（0-正常 1-停用）
     */
    private Integer status;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 备注
     */
    private String remarks;

}
