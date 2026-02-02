package cn.iocoder.yudao.module.dim.dal.mysql.dining;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.DiningSettlementPageReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.dining.DiningSettlementDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DiningSettlementMapper extends BaseMapperX<DiningSettlementDO> {

    default PageResult<DiningSettlementDO> selectPage(DiningSettlementPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DiningSettlementDO>()
                .eqIfPresent(DiningSettlementDO::getUserId, reqVO.getUserId())
                .likeIfPresent(DiningSettlementDO::getUserName, reqVO.getUserName())
                .eqIfPresent(DiningSettlementDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(DiningSettlementDO::getStartDate, reqVO.getStartDate())
                .eqIfPresent(DiningSettlementDO::getIsPaid, reqVO.getIsPaid())
                .eqIfPresent(DiningSettlementDO::getIsInvoiced, reqVO.getIsInvoiced())
                .orderByDesc(DiningSettlementDO::getId));
    }

    default List<DiningSettlementDO> selectListByUserId(Long userId) {
        return selectList(DiningSettlementDO::getUserId, userId);
    }

}
