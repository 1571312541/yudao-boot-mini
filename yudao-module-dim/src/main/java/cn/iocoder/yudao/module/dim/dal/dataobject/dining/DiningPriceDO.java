package cn.iocoder.yudao.module.dim.dal.dataobject.dining;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 餐饮价格配置 DO
 */
@TableName("dim_dining_price")
@KeySequence("dim_dining_price_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class DiningPriceDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 人员类型（0-外协, 1-实验队, 2-施工队, 3-物业, 4-本所, 5-总部）
     */
    private Integer personType;

    /**
     * 餐别（0-早餐 1-午餐 2-晚餐）
     */
    private Integer mealType;

    /**
     * 分类（0-客餐 1-桌餐）
     */
    private Integer diningClass;

    /**
     * 单价(元)
     */
    private BigDecimal price;

    /**
     * 状态（0-启用 1-停用）
     */
    private Integer status;

    /**
     * 备注
     */
    private String remarks;

}
