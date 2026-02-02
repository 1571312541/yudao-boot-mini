package cn.iocoder.yudao.module.dim.dal.dataobject.fire;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 楼栋配置 DO
 */
@TableName("dim_building_config")
@KeySequence("dim_building_config_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class BuildingConfigDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 楼号
     */
    private Integer buildingNo;
    /**
     * 楼栋名称
     */
    private String buildingName;
    /**
     * 楼层列表(JSON数组,如:[1,2,3,-1])
     */
    private String floors;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 楼栋图标URL
     */
    private String iconUrl;
    /**
     * 平面图URL
     */
    private String planUrl;
    /**
     * 状态:0禁用,1启用
     */
    private Integer status;

}
