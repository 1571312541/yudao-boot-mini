package cn.iocoder.yudao.module.dim.dal.mysql.asset;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.AssetHolderPageReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.asset.AssetHolderDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AssetHolderMapper extends BaseMapperX<AssetHolderDO> {

    default PageResult<AssetHolderDO> selectPage(AssetHolderPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<AssetHolderDO>()
                .eqIfPresent(AssetHolderDO::getAssetId, reqVO.getAssetId())
                .likeIfPresent(AssetHolderDO::getHolderName, reqVO.getHolderName())
                .eqIfPresent(AssetHolderDO::getDeptId, reqVO.getDeptId())
                .orderByDesc(AssetHolderDO::getId));
    }

    default List<AssetHolderDO> selectListByAssetId(Long assetId) {
        return selectList(AssetHolderDO::getAssetId, assetId);
    }

}
