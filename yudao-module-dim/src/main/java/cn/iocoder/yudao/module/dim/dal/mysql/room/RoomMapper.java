package cn.iocoder.yudao.module.dim.dal.mysql.room;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.RoomPageReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.room.RoomDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface RoomMapper extends BaseMapperX<RoomDO> {

    default PageResult<RoomDO> selectPage(RoomPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RoomDO>()
                .eqIfPresent(RoomDO::getFloorId, reqVO.getFloorId())
                .likeIfPresent(RoomDO::getRoomNumber, reqVO.getRoomNumber())
                .eqIfPresent(RoomDO::getRoomType, reqVO.getRoomType())
                .eqIfPresent(RoomDO::getStatus, reqVO.getStatus())
                .orderByAsc(RoomDO::getRoomNumber));
    }

    default List<RoomDO> selectListByFloorId(Long floorId) {
        return selectList(RoomDO::getFloorId, floorId);
    }

    default RoomDO selectByRoomNumber(String roomNumber) {
        return selectOne(RoomDO::getRoomNumber, roomNumber);
    }

    default Long selectCountByFloorId(Long floorId) {
        return selectCount(RoomDO::getFloorId, floorId);
    }

    /**
     * 按状态统计房间数量
     */
    @Select("SELECT status, COUNT(*) as count FROM dim_room WHERE deleted = 0 GROUP BY status")
    List<Map<String, Object>> selectCountGroupByStatus();

}
