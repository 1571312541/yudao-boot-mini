package cn.iocoder.yudao.module.dim.dal.mysql.dining;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.price.DiningPricePageReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.dining.DiningPriceDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DiningPriceMapper extends BaseMapperX<DiningPriceDO> {

    default PageResult<DiningPriceDO> selectPage(DiningPricePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DiningPriceDO>()
                .eqIfPresent(DiningPriceDO::getPersonType, reqVO.getPersonType())
                .eqIfPresent(DiningPriceDO::getMealType, reqVO.getMealType())
                .eqIfPresent(DiningPriceDO::getDiningClass, reqVO.getDiningClass())
                .eqIfPresent(DiningPriceDO::getStatus, reqVO.getStatus())
                .orderByDesc(DiningPriceDO::getId));
    }

    default DiningPriceDO selectByCondition(Integer personType, Integer mealType, Integer diningClass) {
        return selectOne(new LambdaQueryWrapperX<DiningPriceDO>()
                .eq(DiningPriceDO::getPersonType, personType)
                .eq(DiningPriceDO::getMealType, mealType)
                .eq(DiningPriceDO::getDiningClass, diningClass)
                .eq(DiningPriceDO::getStatus, 0));
    }

}
