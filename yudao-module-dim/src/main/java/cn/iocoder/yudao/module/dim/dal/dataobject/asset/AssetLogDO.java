package cn.iocoder.yudao.module.dim.dal.dataobject.asset;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 资产操作日志 DO
 */
@TableName("dim_asset_log")
@KeySequence("dim_asset_log_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class AssetLogDO extends BaseDO {

    @TableId
    private Long id;
    /**
     * 资产ID
     */
    private Long assetId;
    /**
     * 资产名称（冗余字段，方便查询展示）
     */
    private String assetName;
    /**
     * 操作类型：0入库,1出库,2调拨,3报废
     */
    private Integer type;
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 操作人ID
     */
    private Long operatorId;
    /**
     * 备注
     */
    private String remarks;

}
