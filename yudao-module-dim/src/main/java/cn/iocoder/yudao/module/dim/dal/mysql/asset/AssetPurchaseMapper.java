package cn.iocoder.yudao.module.dim.dal.mysql.asset;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.AssetPurchasePageReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.asset.AssetPurchaseDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AssetPurchaseMapper extends BaseMapperX<AssetPurchaseDO> {

    default PageResult<AssetPurchaseDO> selectPage(AssetPurchasePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<AssetPurchaseDO>()
                .likeIfPresent(AssetPurchaseDO::getCode, reqVO.getCode())
                .likeIfPresent(AssetPurchaseDO::getTitle, reqVO.getTitle())
                .eqIfPresent(AssetPurchaseDO::getStatus, reqVO.getStatus())
                .eqIfPresent(AssetPurchaseDO::getApplicantId, reqVO.getApplicantId())
                .likeIfPresent(AssetPurchaseDO::getApplicantName, reqVO.getApplicantName())
                .betweenIfPresent(AssetPurchaseDO::getApplyTime, reqVO.getApplyTime())
                .betweenIfPresent(AssetPurchaseDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(AssetPurchaseDO::getId));
    }

    default AssetPurchaseDO selectByCode(String code) {
        return selectOne(AssetPurchaseDO::getCode, code);
    }

}
