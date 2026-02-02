package cn.iocoder.yudao.module.dim.dal.dataobject.inventory;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 库存物品分类 DO
 */
@TableName("dim_inventory_category")
@KeySequence("dim_inventory_category_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class InventoryCategoryDO extends BaseDO {

    /**
     * 主键
     */
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
     * 分类类型：1-物资 2-耗材 0-通用
     */
    private Integer type;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态：0-正常 1-停用
     */
    private Integer status;

}
