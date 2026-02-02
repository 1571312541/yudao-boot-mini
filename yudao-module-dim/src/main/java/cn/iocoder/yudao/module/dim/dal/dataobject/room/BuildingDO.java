package cn.iocoder.yudao.module.dim.dal.dataobject.room;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 楼栋 DO
 */
@TableName("dim_building")
@KeySequence("dim_building_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class BuildingDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 楼栋名称
     */
    private String name;

    /**
     * 楼栋编号
     */
    private String code;

    /**
     * 楼层数
     */
    private Integer floorCount;

    /**
     * 地址
     */
    private String address;

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
