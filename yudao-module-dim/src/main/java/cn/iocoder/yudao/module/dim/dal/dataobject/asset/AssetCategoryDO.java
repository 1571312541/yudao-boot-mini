package cn.iocoder.yudao.module.dim.dal.dataobject.asset;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 资产分类 DO
 */
@TableName("dim_asset_category")
@KeySequence("dim_asset_category_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class AssetCategoryDO extends BaseDO {

    @TableId
    private Long id;
    /**
     * 父级ID
     */
    private Long parentId;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 状态：0禁用,1启用
     */
    private Integer status;

}
