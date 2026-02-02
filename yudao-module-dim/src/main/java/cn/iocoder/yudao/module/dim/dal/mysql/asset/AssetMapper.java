package cn.iocoder.yudao.module.dim.dal.mysql.asset;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.AssetPageReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.asset.AssetDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AssetMapper extends BaseMapperX<AssetDO> {

    default PageResult<AssetDO> selectPage(AssetPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<AssetDO>()
                .likeIfPresent(AssetDO::getName, reqVO.getName())
                .likeIfPresent(AssetDO::getCode, reqVO.getCode())
                .eqIfPresent(AssetDO::getCategoryId, reqVO.getCategoryId())
                .eqIfPresent(AssetDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(AssetDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(AssetDO::getId));
    }

    default AssetDO selectByCode(String code) {
        return selectOne(AssetDO::getCode, code);
    }

}
