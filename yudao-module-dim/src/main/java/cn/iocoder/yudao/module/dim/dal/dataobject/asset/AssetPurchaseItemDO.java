package cn.iocoder.yudao.module.dim.dal.dataobject.asset;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 资产采购明细 DO
 */
@TableName("dim_asset_purchase_item")
@KeySequence("dim_asset_purchase_item_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class AssetPurchaseItemDO extends BaseDO {

    @TableId
    private Long id;
    /**
     * 采购单ID
     */
    private Long purchaseId;
    /**
     * 资产名称
     */
    private String assetName;
    /**
     * 资产分类ID
     */
    private Long categoryId;
    /**
     * 规格型号
     */
    private String specification;
    /**
     * 计量单位
     */
    private String measurementUnit;
    /**
     * 采购数量
     */
    private Integer quantity;
    /**
     * 单价
     */
    private BigDecimal unitPrice;
    /**
     * 小计金额
     */
    private BigDecimal totalPrice;
    /**
     * 备注
     */
    private String remark;
    /**
     * 入库后关联的资产ID
     */
    private Long assetId;
    /**
     * 是否已入库
     */
    private Boolean warehoused;

}
