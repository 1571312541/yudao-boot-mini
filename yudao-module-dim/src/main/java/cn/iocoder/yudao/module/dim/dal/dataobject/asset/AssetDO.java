package cn.iocoder.yudao.module.dim.dal.dataobject.asset;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 资产信息 DO
 */
@TableName("dim_asset")
@KeySequence("dim_asset_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class AssetDO extends BaseDO {

    @TableId
    private Long id;
    /**
     * 分类ID
     */
    private Long categoryId;
    /**
     * 资产名称
     */
    private String name;
    /**
     * 资产编号
     */
    private String code;
    /**
     * 计量单位
     */
    private String measurementUnit;
    /**
     * 库存数量
     */
    private Integer inventory;
    /**
     * 单价
     */
    private BigDecimal price;
    /**
     * 购置日期
     */
    private LocalDate purchaseDate;
    /**
     * 状态
     */
    private Integer status;

}
