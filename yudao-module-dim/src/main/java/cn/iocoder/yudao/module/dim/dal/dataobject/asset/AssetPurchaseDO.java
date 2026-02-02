package cn.iocoder.yudao.module.dim.dal.dataobject.asset;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 资产采购 DO
 */
@TableName("dim_asset_purchase")
@KeySequence("dim_asset_purchase_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class AssetPurchaseDO extends BaseDO {

    @TableId
    private Long id;
    /**
     * 采购单号
     */
    private String code;
    /**
     * 采购标题
     */
    private String title;
    /**
     * 采购内容/说明
     */
    private String content;
    /**
     * 采购总金额
     */
    private BigDecimal totalAmount;
    /**
     * 状态：0待提交,1待审核,2已通过,3已拒绝,4已入库
     *
     * @see cn.iocoder.yudao.module.dim.enums.AssetPurchaseStatusEnum
     */
    private Integer status;
    /**
     * 申请人ID
     */
    private Long applicantId;
    /**
     * 申请人姓名
     */
    private String applicantName;
    /**
     * 申请部门ID
     */
    private Long applicantDeptId;
    /**
     * 申请时间
     */
    private LocalDateTime applyTime;
    /**
     * 审核人ID
     */
    private Long auditorId;
    /**
     * 审核人姓名
     */
    private String auditorName;
    /**
     * 审核时间
     */
    private LocalDateTime auditTime;
    /**
     * 审核备注
     */
    private String auditRemark;
    /**
     * 流程实例ID（预留，用于后续工作流扩展）
     */
    private String processInstanceId;

}
