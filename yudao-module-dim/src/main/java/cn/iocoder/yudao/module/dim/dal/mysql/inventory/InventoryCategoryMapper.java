package cn.iocoder.yudao.module.dim.dal.mysql.inventory;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.dim.controller.admin.inventory.vo.InventoryCategoryPageReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.inventory.InventoryCategoryDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 库存物品分类 Mapper
 */
@Mapper
public interface InventoryCategoryMapper extends BaseMapperX<InventoryCategoryDO> {

    default PageResult<InventoryCategoryDO> selectPage(InventoryCategoryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<InventoryCategoryDO>()
                .likeIfPresent(InventoryCategoryDO::getName, reqVO.getName())
                .eqIfPresent(InventoryCategoryDO::getType, reqVO.getType())
                .eqIfPresent(InventoryCategoryDO::getStatus, reqVO.getStatus())
                .orderByAsc(InventoryCategoryDO::getSort)
                .orderByDesc(InventoryCategoryDO::getId));
    }

    default List<InventoryCategoryDO> selectList(InventoryCategoryPageReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<InventoryCategoryDO>()
                .likeIfPresent(InventoryCategoryDO::getName, reqVO.getName())
                .eqIfPresent(InventoryCategoryDO::getType, reqVO.getType())
                .eqIfPresent(InventoryCategoryDO::getStatus, reqVO.getStatus())
                .orderByAsc(InventoryCategoryDO::getSort)
                .orderByDesc(InventoryCategoryDO::getId));
    }

    default List<InventoryCategoryDO> selectListByParentId(Long parentId) {
        return selectList(new LambdaQueryWrapperX<InventoryCategoryDO>()
                .eq(InventoryCategoryDO::getParentId, parentId));
    }

    default List<InventoryCategoryDO> selectListByType(Integer type) {
        return selectList(new LambdaQueryWrapperX<InventoryCategoryDO>()
                .eq(InventoryCategoryDO::getType, type)
                .orderByAsc(InventoryCategoryDO::getSort));
    }

}
