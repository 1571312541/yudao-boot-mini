package cn.iocoder.yudao.module.dim.dal.mysql.asset;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.dim.dal.dataobject.asset.AssetCategoryDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AssetCategoryMapper extends BaseMapperX<AssetCategoryDO> {

    default List<AssetCategoryDO> selectList(String name, Integer status) {
        return selectList(new LambdaQueryWrapperX<AssetCategoryDO>()
                .likeIfPresent(AssetCategoryDO::getName, name)
                .eqIfPresent(AssetCategoryDO::getStatus, status)
                .orderByAsc(AssetCategoryDO::getSort));
    }

    default AssetCategoryDO selectByParentIdAndName(Long parentId, String name) {
        return selectOne(AssetCategoryDO::getParentId, parentId, AssetCategoryDO::getName, name);
    }

    default Long selectCountByParentId(Long parentId) {
        return selectCount(AssetCategoryDO::getParentId, parentId);
    }

    default List<AssetCategoryDO> selectListByParentId(Long parentId) {
        return selectList(AssetCategoryDO::getParentId, parentId);
    }

}
