package cn.iocoder.yudao.module.dim.dal.mysql.room;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.StayLogPageReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.room.StayLogDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StayLogMapper extends BaseMapperX<StayLogDO> {

    default PageResult<StayLogDO> selectPage(StayLogPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<StayLogDO>()
                .eqIfPresent(StayLogDO::getRoomId, reqVO.getRoomId())
                .eqIfPresent(StayLogDO::getGuestId, reqVO.getGuestId())
                .eqIfPresent(StayLogDO::getOperationType, reqVO.getOperationType())
                .betweenIfPresent(StayLogDO::getOperationTime, reqVO.getOperationTime())
                .orderByDesc(StayLogDO::getId));
    }

    default List<StayLogDO> selectListByRoomId(Long roomId) {
        return selectList(StayLogDO::getRoomId, roomId);
    }

    default List<StayLogDO> selectListByGuestId(Long guestId) {
        return selectList(StayLogDO::getGuestId, guestId);
    }

}
