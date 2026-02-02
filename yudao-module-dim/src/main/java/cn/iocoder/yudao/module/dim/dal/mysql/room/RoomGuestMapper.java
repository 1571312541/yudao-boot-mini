package cn.iocoder.yudao.module.dim.dal.mysql.room;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.RoomGuestPageReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.room.RoomGuestDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoomGuestMapper extends BaseMapperX<RoomGuestDO> {

    default PageResult<RoomGuestDO> selectPage(RoomGuestPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RoomGuestDO>()
                .eqIfPresent(RoomGuestDO::getRoomId, reqVO.getRoomId())
                .likeIfPresent(RoomGuestDO::getGuestName, reqVO.getGuestName())
                .likeIfPresent(RoomGuestDO::getIdNumber, reqVO.getIdNumber())
                .eqIfPresent(RoomGuestDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(RoomGuestDO::getCheckInDate, reqVO.getCheckInDate())
                .orderByDesc(RoomGuestDO::getId));
    }

    default List<RoomGuestDO> selectListByRoomId(Long roomId) {
        return selectList(RoomGuestDO::getRoomId, roomId);
    }

    default List<RoomGuestDO> selectListByRoomIdAndStatus(Long roomId, Integer status) {
        return selectList(new LambdaQueryWrapperX<RoomGuestDO>()
                .eq(RoomGuestDO::getRoomId, roomId)
                .eq(RoomGuestDO::getStatus, status));
    }

    /**
     * 查询某主住客的所有同住人
     */
    default List<RoomGuestDO> selectCoGuestsByPrimaryGuestId(Long primaryGuestId) {
        return selectList(new LambdaQueryWrapperX<RoomGuestDO>()
                .eq(RoomGuestDO::getPrimaryGuestId, primaryGuestId));
    }

    /**
     * 查询某房间所有在住住客（包括主住客和同住人）
     */
    default List<RoomGuestDO> selectActiveGuestsByRoomId(Long roomId, Integer status) {
        return selectList(new LambdaQueryWrapperX<RoomGuestDO>()
                .eq(RoomGuestDO::getRoomId, roomId)
                .eq(RoomGuestDO::getStatus, status));
    }

}
