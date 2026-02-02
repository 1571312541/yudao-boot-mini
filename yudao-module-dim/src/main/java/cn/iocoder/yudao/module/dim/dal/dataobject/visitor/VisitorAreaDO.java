package cn.iocoder.yudao.module.dim.dal.dataobject.visitor;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 访客区域 DO
 */
@TableName("dim_visitor_area")
@KeySequence("dim_visitor_area_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class VisitorAreaDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 区域名称
     */
    private String areaName;
    /**
     * 区域IP
     */
    private String areaIp;
    /**
     * 备注
     */
    private String remarks;

}
