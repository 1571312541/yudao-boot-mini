package cn.iocoder.yudao.module.dim.dal.dataobject.inventory;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 库存批次 DO
 * 用于支持 FIFO 先进先出
 */
@TableName("dim_inventory_batch")
@KeySequence("dim_inventory_batch_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class InventoryBatchDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 物品ID
     */
    private Long itemId;

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 批次库存量
     */
    private BigDecimal quantity;

    /**
     * 入库单价
     */
    private BigDecimal unitPrice;

    /**
     * 生产日期
     */
    private LocalDate productionDate;

    /**
     * 有效期至
     */
    private LocalDate expiryDate;

    /**
     * 供应商
     */
    private String supplier;

    /**
     * 状态：0-正常 1-已清空
     */
    private Integer status;

}
