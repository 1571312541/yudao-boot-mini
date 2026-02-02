package cn.iocoder.yudao.module.dim.dal.mysql.asset;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.AssetLogPageReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.asset.AssetLogDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AssetLogMapper extends BaseMapperX<AssetLogDO> {

    default PageResult<AssetLogDO> selectPage(AssetLogPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<AssetLogDO>()
                .eqIfPresent(AssetLogDO::getAssetId, reqVO.getAssetId())
                .eqIfPresent(AssetLogDO::getType, reqVO.getType())
                .betweenIfPresent(AssetLogDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(AssetLogDO::getId));
    }

    default List<AssetLogDO> selectListByAssetId(Long assetId) {
        return selectList(AssetLogDO::getAssetId, assetId);
    }

}
