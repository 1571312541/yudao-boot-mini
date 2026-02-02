package cn.iocoder.yudao.module.dim.dal.dataobject.dining;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalTime;

/**
 * 餐饮设置 DO
 */
@TableName("dim_dining")
@KeySequence("dim_dining_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class DiningDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 餐类名称
     */
    private String name;

    /**
     * 餐类类型（1-早餐 2-午餐 3-晚餐 4-宵夜）
     */
    private Integer mealType;

    /**
     * 单价(元)
     */
    private BigDecimal price;

    /**
     * 开始时间
     */
    private LocalTime startTime;

    /**
     * 结束时间
     */
    private LocalTime endTime;

    /**
     * 状态（0-启用 1-停用）
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

}
