package cn.iocoder.yudao.module.dim.service.room;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.RoomImportRespVO;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.RoomImportVO;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.RoomPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.RoomSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.room.RoomDO;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 房间 Service 接口
 */
public interface RoomService {

    /**
     * 创建房间
     */
    Long createRoom(@Valid RoomSaveReqVO createReqVO);

    /**
     * 更新房间
     */
    void updateRoom(@Valid RoomSaveReqVO updateReqVO);

    /**
     * 删除房间
     */
    void deleteRoom(Long id);

    /**
     * 获得房间
     */
    RoomDO getRoom(Long id);

    /**
     * 获得房间分页
     */
    PageResult<RoomDO> getRoomPage(RoomPageReqVO pageReqVO);

    /**
     * 根据楼层ID获得房间列表
     */
    List<RoomDO> getRoomListByFloorId(Long floorId);

    /**
     * 更新房间状态
     */
    void updateRoomStatus(Long id, Integer status);

    /**
     * 按状态统计房间数量
     *
     * @return Map<状态值, 房间数量>
     */
    Map<Integer, Long> countRoomsByStatus();

    /**
     * 导入房间
     *
     * @param list 导入数据
     * @return 导入结果
     */
    RoomImportRespVO importRooms(List<RoomImportVO> list);

}
