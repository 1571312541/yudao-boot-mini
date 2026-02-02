package cn.iocoder.yudao.module.dim.dal.mysql.visitor;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.dim.controller.admin.visitor.vo.ReservationVisitorPageReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.visitor.ReservationVisitorDO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ReservationVisitorMapper extends BaseMapperX<ReservationVisitorDO> {

    default PageResult<ReservationVisitorDO> selectPage(ReservationVisitorPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ReservationVisitorDO>()
                .likeIfPresent(ReservationVisitorDO::getName, reqVO.getName())
                .likeIfPresent(ReservationVisitorDO::getPhone, reqVO.getPhone())
                .likeIfPresent(ReservationVisitorDO::getIdNum, reqVO.getIdNum())
                .eqIfPresent(ReservationVisitorDO::getType, reqVO.getType())
                .eqIfPresent(ReservationVisitorDO::getStatus, reqVO.getStatus())
                .likeIfPresent(ReservationVisitorDO::getUnitName, reqVO.getUnitName())
                .betweenIfPresent(ReservationVisitorDO::getStartEffectiveDate, reqVO.getStartEffectiveDate())
                .orderByDesc(ReservationVisitorDO::getId));
    }

    default ReservationVisitorDO selectByIdNum(String idNum) {
        return selectOne(ReservationVisitorDO::getIdNum, idNum);
    }

    default ReservationVisitorDO selectByPhone(String phone) {
        return selectOne(ReservationVisitorDO::getPhone, phone);
    }

    default List<ReservationVisitorDO> selectPendingByDate(LocalDateTime date) {
        return selectList(new LambdaQueryWrapperX<ReservationVisitorDO>()
                .eq(ReservationVisitorDO::getStatus, 0) // 待来访状态
                .le(ReservationVisitorDO::getStartEffectiveDate, date)
                .ge(ReservationVisitorDO::getEndEffectiveDate, date));
    }

    default List<ReservationVisitorDO> selectExpired(LocalDateTime date) {
        return selectList(new LambdaQueryWrapperX<ReservationVisitorDO>()
                .eq(ReservationVisitorDO::getStatus, 0) // 待来访状态
                .lt(ReservationVisitorDO::getEndEffectiveDate, date));
    }

}
