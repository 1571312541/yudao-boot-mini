package cn.iocoder.yudao.module.dim.dal.dataobject.inventory;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 库存物品 DO
 */
@TableName("dim_inventory_item")
@KeySequence("dim_inventory_item_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class InventoryItemDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 物品编码
     */
    private String code;

    /**
     * 物品名称
     */
    private String name;

    /**
     * 物品类型：1-物资 2-耗材
     */
    private Integer type;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 仓库ID
     */
    private Long warehouseId;

    /**
     * 规格型号
     */
    private String spec;

    /**
     * 计量单位
     */
    private String unit;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 生产厂家
     */
    private String manufacturer;

    /**
     * 当前库存量
     */
    private BigDecimal quantity;

    /**
     * 锁定库存量
     */
    private BigDecimal lockedQuantity;

    /**
     * 最小库存预警值
     */
    private BigDecimal minQuantity;

    /**
     * 最大库存预警值
     */
    private BigDecimal maxQuantity;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 库存金额
     */
    private BigDecimal totalAmount;

    /**
     * 状态：0-正常 1-停用
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 图片URL
     */
    private String imageUrl;

}
