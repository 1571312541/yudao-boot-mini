package cn.iocoder.yudao.module.dim.dal.dataobject.inventory;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 库存出入库日志 DO
 */
@TableName("dim_inventory_log")
@KeySequence("dim_inventory_log_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class InventoryLogDO extends BaseDO {

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
     * 批次ID
     */
    private Long batchId;

    /**
     * 仓库ID
     */
    private Long warehouseId;

    /**
     * 操作类型：1-采购入库 2-盘盈入库 3-领用出库 4-盘亏出库 5-调拨入库 6-调拨出库
     */
    private Integer operationType;

    /**
     * 操作单号
     */
    private String operationNo;

    /**
     * 操作数量（正数入库，负数出库）
     */
    private BigDecimal quantity;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 原价（折扣前）
     */
    private BigDecimal originalPrice;

    /**
     * 折扣率(%)
     */
    private BigDecimal discountRate;

    /**
     * 金额
     */
    private BigDecimal totalAmount;

    /**
     * 操作前库存
     */
    private BigDecimal beforeQuantity;

    /**
     * 操作后库存
     */
    private BigDecimal afterQuantity;

    /**
     * 审核状态：0-待审核 1-已通过 2-已驳回
     */
    private Integer auditStatus;

    /**
     * 审核人ID
     */
    private Long auditUserId;

    /**
     * 审核时间
     */
    private LocalDateTime auditTime;

    /**
     * 审核备注
     */
    private String auditRemark;

    /**
     * 申请人ID
     */
    private Long applicantId;

    /**
     * 申请部门ID
     */
    private Long applicantDeptId;

    /**
     * 用途
     */
    private String purpose;

    /**
     * 使用地点
     */
    private String usageLocation;

    /**
     * 备注
     */
    private String remark;

}
