package cn.iocoder.yudao.module.dim.service.room;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.CancelReserveReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.ExtendStayReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.RoomGuestPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.RoomGuestSaveReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.TransferRoomReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.room.RoomGuestDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 住客 Service 接口
 */
public interface RoomGuestService {

    /**
     * 办理入住
     */
    Long checkIn(@Valid RoomGuestSaveReqVO createReqVO);

    /**
     * 更新住客信息
     */
    void updateRoomGuest(@Valid RoomGuestSaveReqVO updateReqVO);

    /**
     * 办理退房
     */
    void checkOut(Long id, String remarks);

    /**
     * 删除住客记录
     */
    void deleteRoomGuest(Long id);

    /**
     * 获得住客记录
     */
    RoomGuestDO getRoomGuest(Long id);

    /**
     * 获得住客分页
     */
    PageResult<RoomGuestDO> getRoomGuestPage(RoomGuestPageReqVO pageReqVO);

    /**
     * 根据房间ID获得在住住客
     */
    List<RoomGuestDO> getActiveGuestsByRoomId(Long roomId);

    /**
     * 续住
     *
     * @param reqVO 续住请求
     */
    void extendStay(@Valid ExtendStayReqVO reqVO);

    /**
     * 预约
     *
     * @param reqVO 预约信息
     * @return 预约记录ID
     */
    Long reserve(@Valid RoomGuestSaveReqVO reqVO);

    /**
     * 预约转入住
     *
     * @param guestId 住客ID
     */
    void confirmReserve(Long guestId);

    /**
     * 取消预约
     *
     * @param reqVO 取消预约请求
     */
    void cancelReserve(@Valid CancelReserveReqVO reqVO);

    /**
     * 换房
     *
     * @param reqVO 换房请求
     */
    void transferRoom(@Valid TransferRoomReqVO reqVO);

    /**
     * 添加同住人
     *
     * @param reqVO 同住人信息
     * @param primaryGuestId 主住客ID
     * @return 同住人记录ID
     */
    Long addCoGuest(@Valid RoomGuestSaveReqVO reqVO, Long primaryGuestId);

    /**
     * 移除同住人（不影响房间状态）
     *
     * @param guestId 同住人记录ID
     */
    void removeCoGuest(Long guestId);

    /**
     * 获取同住人列表
     *
     * @param primaryGuestId 主住客ID
     * @return 同住人列表
     */
    List<RoomGuestDO> getCoGuests(Long primaryGuestId);

}
