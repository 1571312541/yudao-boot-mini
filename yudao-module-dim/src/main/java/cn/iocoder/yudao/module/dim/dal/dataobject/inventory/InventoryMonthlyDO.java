package cn.iocoder.yudao.module.dim.dal.dataobject.inventory;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 库存月度汇总 DO
 */
@TableName("dim_inventory_monthly")
@Data
@EqualsAndHashCode(callSuper = true)
public class InventoryMonthlyDO extends BaseDO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 物品ID
     */
    private Long itemId;

    /**
     * 年月（YYYY-MM）
     */
    @TableField("`year_month`")
    private String yearMonth;

    /**
     * 期初数量
     */
    private BigDecimal openingQty;

    /**
     * 期初金额
     */
    private BigDecimal openingAmount;

    /**
     * 入库数量
     */
    private BigDecimal inQty;

    /**
     * 入库金额
     */
    private BigDecimal inAmount;

    /**
     * 出库数量
     */
    private BigDecimal outQty;

    /**
     * 出库金额
     */
    private BigDecimal outAmount;

    /**
     * 期末数量
     */
    private BigDecimal closingQty;

    /**
     * 期末金额
     */
    private BigDecimal closingAmount;

}
