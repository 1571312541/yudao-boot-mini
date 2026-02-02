package cn.iocoder.yudao.module.dim.dal.dataobject.asset;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 资产持有 DO
 */
@TableName("dim_asset_holder")
@KeySequence("dim_asset_holder_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class AssetHolderDO extends BaseDO {

    @TableId
    private Long id;
    /**
     * 资产ID
     */
    private Long assetId;
    /**
     * 持有数量
     */
    private Integer quantity;
    /**
     * 持有人ID
     */
    private Long holderId;
    /**
     * 持有人姓名
     */
    private String holderName;
    /**
     * 部门ID
     */
    private Long deptId;
    /**
     * 楼号
     */
    private Integer buildingNo;
    /**
     * 楼层
     */
    private Integer floor;
    /**
     * 具体位置
     */
    private String position;
    /**
     * 备注
     */
    private String remarks;

}
