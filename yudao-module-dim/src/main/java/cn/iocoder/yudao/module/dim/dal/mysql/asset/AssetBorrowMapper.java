package cn.iocoder.yudao.module.dim.dal.mysql.asset;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.AssetBorrowPageReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.asset.AssetBorrowDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AssetBorrowMapper extends BaseMapperX<AssetBorrowDO> {

    default PageResult<AssetBorrowDO> selectPage(AssetBorrowPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<AssetBorrowDO>()
                .eqIfPresent(AssetBorrowDO::getAssetId, reqVO.getAssetId())
                .likeIfPresent(AssetBorrowDO::getBorrowerName, reqVO.getBorrowerName())
                .eqIfPresent(AssetBorrowDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(AssetBorrowDO::getBorrowDate, reqVO.getBorrowDate())
                .orderByDesc(AssetBorrowDO::getId));
    }

    default List<AssetBorrowDO> selectListByAssetId(Long assetId) {
        return selectList(AssetBorrowDO::getAssetId, assetId);
    }

}
