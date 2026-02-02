package cn.iocoder.yudao.module.dim.service.room;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.CancelReserveReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.ExtendStayReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.RoomGuestPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.RoomGuestSaveReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.TransferRoomReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.room.RoomDO;
import cn.iocoder.yudao.module.dim.dal.dataobject.room.RoomGuestDO;
import cn.iocoder.yudao.module.dim.dal.dataobject.room.StaySettlementDO;
import cn.iocoder.yudao.module.dim.dal.mysql.room.RoomGuestMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.dim.enums.ErrorCodeConstants.*;

/**
 * 住客 Service 实现类
 */
@Service
@Validated
public class RoomGuestServiceImpl implements RoomGuestService {

    /** 住宿状态：在住 */
    private static final Integer STATUS_ACTIVE = 0;
    /** 住宿状态：已退房 */
    private static final Integer STATUS_CHECKED_OUT = 1;
    /** 住宿状态：预约中 */
    private static final Integer STATUS_RESERVED = 2;
    /** 住宿状态：已取消 */
    private static final Integer STATUS_CANCELLED = 3;
    /** 房间状态：空闲 */
    private static final Integer ROOM_STATUS_VACANT = 0;
    /** 房间状态：已入住 */
    private static final Integer ROOM_STATUS_OCCUPIED = 1;
    /** 房间状态：已预定 */
    private static final Integer ROOM_STATUS_RESERVED = 3;

    @Resource
    private RoomGuestMapper roomGuestMapper;

    @Resource
    private RoomService roomService;

    @Resource
    private StayLogService stayLogService;

    @Resource
    @Lazy
    private StaySettlementService staySettlementService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long checkIn(RoomGuestSaveReqVO createReqVO) {
        // 校验房间存在
        RoomDO room = roomService.getRoom(createReqVO.getRoomId());
        if (room == null) {
            throw exception(ROOM_NOT_EXISTS);
        }
        // 校验房间是否可入住（空闲或已预定状态）
        if (!ROOM_STATUS_VACANT.equals(room.getStatus()) && !ROOM_STATUS_RESERVED.equals(room.getStatus())) {
            throw exception(ROOM_ALREADY_OCCUPIED);
        }
        // 插入主住客记录
        RoomGuestDO guest = BeanUtils.toBean(createReqVO, RoomGuestDO.class);
        guest.setStatus(STATUS_ACTIVE);
        guest.setIsPrimary(true);  // 标记为主住客
        guest.setPrimaryGuestId(null);  // 主住客没有 primaryGuestId
        if (guest.getCheckInDate() == null) {
            guest.setCheckInDate(LocalDateTime.now());
        }
        roomGuestMapper.insert(guest);

        // 处理同住人列表
        if (!CollectionUtils.isEmpty(createReqVO.getCoGuests())) {
            for (RoomGuestSaveReqVO.CoGuestVO coGuestVO : createReqVO.getCoGuests()) {
                RoomGuestDO coGuest = new RoomGuestDO();
                coGuest.setRoomId(createReqVO.getRoomId());
                coGuest.setGuestName(coGuestVO.getGuestName());
                coGuest.setIdType(coGuestVO.getIdType());
                coGuest.setIdNumber(coGuestVO.getIdNumber());
                coGuest.setPhone(coGuestVO.getPhone());
                coGuest.setDeptName(coGuestVO.getDeptName());
                coGuest.setCheckInDate(guest.getCheckInDate());
                coGuest.setExpectedCheckOutDate(createReqVO.getExpectedCheckOutDate());
                coGuest.setStatus(STATUS_ACTIVE);
                coGuest.setIsPrimary(false);  // 标记为同住人
                coGuest.setPrimaryGuestId(guest.getId());  // 关联主住客
                roomGuestMapper.insert(coGuest);
            }
        }

        // 更新房间状态为已入住
        roomService.updateRoomStatus(createReqVO.getRoomId(), ROOM_STATUS_OCCUPIED);
        // 记录日志（入住）
        stayLogService.createStayLog(createReqVO.getRoomId(), guest.getId(), 0,
                createReqVO.getRemarks());
        return guest.getId();
    }

    @Override
    public void updateRoomGuest(RoomGuestSaveReqVO updateReqVO) {
        validateRoomGuestExists(updateReqVO.getId());
        RoomGuestDO updateObj = BeanUtils.toBean(updateReqVO, RoomGuestDO.class);
        roomGuestMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void checkOut(Long id, String remarks) {
        // 校验存在
        RoomGuestDO guest = roomGuestMapper.selectById(id);
        if (guest == null) {
            throw exception(ROOM_GUEST_NOT_EXISTS);
        }
        // 已退房的不能重复退房
        if (STATUS_CHECKED_OUT.equals(guest.getStatus())) {
            throw exception(ROOM_GUEST_ALREADY_CHECKED_OUT);
        }

        LocalDateTime checkOutTime = LocalDateTime.now();

        // 更新住客状态
        RoomGuestDO updateObj = new RoomGuestDO();
        updateObj.setId(id);
        updateObj.setStatus(STATUS_CHECKED_OUT);
        updateObj.setActualCheckOutDate(checkOutTime);
        updateObj.setRemarks(remarks);
        roomGuestMapper.updateById(updateObj);

        // 如果是主住客退房，创建费用结算记录
        if (Boolean.TRUE.equals(guest.getIsPrimary()) || guest.getIsPrimary() == null) {
            createSettlementForGuest(guest, checkOutTime);
        }

        // 检查房间是否还有其他在住住客（支持部分退房）
        List<RoomGuestDO> activeGuests = roomGuestMapper.selectListByRoomIdAndStatus(
                guest.getRoomId(), STATUS_ACTIVE);
        if (activeGuests.isEmpty()) {
            // 没有其他住客，更新房间状态为空闲
            roomService.updateRoomStatus(guest.getRoomId(), ROOM_STATUS_VACANT);
        }
        // 记录日志（退房）
        stayLogService.createStayLog(guest.getRoomId(), id, 1, remarks);
    }

    /**
     * 为住客创建费用结算记录
     */
    private void createSettlementForGuest(RoomGuestDO guest, LocalDateTime checkOutTime) {
        // 获取房间信息以获取价格
        RoomDO room = roomService.getRoom(guest.getRoomId());
        if (room == null || room.getPrice() == null) {
            return;
        }

        // 计算住宿天数
        LocalDateTime checkInDate = guest.getCheckInDate();
        if (checkInDate == null) {
            return;
        }
        long days = ChronoUnit.DAYS.between(checkInDate.toLocalDate(), checkOutTime.toLocalDate());
        if (days < 1) {
            days = 1; // 最少算一天
        }

        // 计算总金额
        BigDecimal roomPrice = room.getPrice();
        BigDecimal totalAmount = roomPrice.multiply(BigDecimal.valueOf(days));

        // 创建结算记录
        StaySettlementDO settlement = new StaySettlementDO();
        settlement.setGuestId(guest.getId());
        settlement.setRoomId(guest.getRoomId());
        settlement.setCheckInDate(checkInDate);
        settlement.setCheckOutDate(checkOutTime);
        settlement.setStayDays((int) days);
        settlement.setRoomPrice(roomPrice);
        settlement.setTotalAmount(totalAmount);
        settlement.setPaymentStatus(0); // 未结算
        settlement.setInvoiceStatus(0); // 未开票

        staySettlementService.createSettlement(settlement);
    }

    @Override
    public void deleteRoomGuest(Long id) {
        validateRoomGuestExists(id);
        roomGuestMapper.deleteById(id);
    }

    private void validateRoomGuestExists(Long id) {
        if (roomGuestMapper.selectById(id) == null) {
            throw exception(ROOM_GUEST_NOT_EXISTS);
        }
    }

    @Override
    public RoomGuestDO getRoomGuest(Long id) {
        return roomGuestMapper.selectById(id);
    }

    @Override
    public PageResult<RoomGuestDO> getRoomGuestPage(RoomGuestPageReqVO pageReqVO) {
        return roomGuestMapper.selectPage(pageReqVO);
    }

    @Override
    public List<RoomGuestDO> getActiveGuestsByRoomId(Long roomId) {
        return roomGuestMapper.selectListByRoomIdAndStatus(roomId, STATUS_ACTIVE);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void extendStay(ExtendStayReqVO reqVO) {
        // 校验住客存在
        RoomGuestDO guest = roomGuestMapper.selectById(reqVO.getGuestId());
        if (guest == null) {
            throw exception(ROOM_GUEST_NOT_EXISTS);
        }
        // 校验住客状态为在住
        if (!STATUS_ACTIVE.equals(guest.getStatus())) {
            throw exception(ROOM_GUEST_NOT_ACTIVE);
        }
        // 校验新预离时间 > 当前预离时间
        if (guest.getExpectedCheckOutDate() != null
                && !reqVO.getNewExpectedCheckOutDate().isAfter(guest.getExpectedCheckOutDate())) {
            throw exception(ROOM_GUEST_EXTEND_DATE_INVALID);
        }
        // 更新预离日期
        RoomGuestDO updateObj = new RoomGuestDO();
        updateObj.setId(reqVO.getGuestId());
        updateObj.setExpectedCheckOutDate(reqVO.getNewExpectedCheckOutDate());
        if (reqVO.getRemarks() != null) {
            updateObj.setRemarks(reqVO.getRemarks());
        }
        roomGuestMapper.updateById(updateObj);
        // 记录日志（续住 operationType=3）
        stayLogService.createStayLog(guest.getRoomId(), reqVO.getGuestId(), 3, reqVO.getRemarks());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long reserve(RoomGuestSaveReqVO reqVO) {
        // 校验房间存在
        RoomDO room = roomService.getRoom(reqVO.getRoomId());
        if (room == null) {
            throw exception(ROOM_NOT_EXISTS);
        }
        // 校验房间状态为空闲
        if (!ROOM_STATUS_VACANT.equals(room.getStatus())) {
            throw exception(ROOM_NOT_VACANT);
        }
        // 创建住客记录，状态为预约中
        RoomGuestDO guest = BeanUtils.toBean(reqVO, RoomGuestDO.class);
        guest.setStatus(STATUS_RESERVED);
        guest.setIsPrimary(true);  // 主住客
        roomGuestMapper.insert(guest);
        // 更新房间状态为已预定
        roomService.updateRoomStatus(reqVO.getRoomId(), ROOM_STATUS_RESERVED);
        // 记录日志（预约 operationType=4）
        stayLogService.createStayLog(reqVO.getRoomId(), guest.getId(), 4, reqVO.getRemarks());
        return guest.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmReserve(Long guestId) {
        // 校验住客存在
        RoomGuestDO guest = roomGuestMapper.selectById(guestId);
        if (guest == null) {
            throw exception(ROOM_GUEST_NOT_EXISTS);
        }
        // 校验住客状态为预约中
        if (!STATUS_RESERVED.equals(guest.getStatus())) {
            throw exception(ROOM_GUEST_NOT_RESERVED);
        }
        // 更新住客状态为在住，设置入住时间
        RoomGuestDO updateObj = new RoomGuestDO();
        updateObj.setId(guestId);
        updateObj.setStatus(STATUS_ACTIVE);
        updateObj.setCheckInDate(LocalDateTime.now());
        roomGuestMapper.updateById(updateObj);
        // 更新房间状态为已入住
        roomService.updateRoomStatus(guest.getRoomId(), ROOM_STATUS_OCCUPIED);
        // 记录日志（入住 operationType=0）
        stayLogService.createStayLog(guest.getRoomId(), guestId, 0, "预约转入住");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelReserve(CancelReserveReqVO reqVO) {
        // 校验住客存在
        RoomGuestDO guest = roomGuestMapper.selectById(reqVO.getGuestId());
        if (guest == null) {
            throw exception(ROOM_GUEST_NOT_EXISTS);
        }
        // 校验住客状态为预约中
        if (!STATUS_RESERVED.equals(guest.getStatus())) {
            throw exception(ROOM_GUEST_NOT_RESERVED);
        }
        // 更新住客状态为已取消
        RoomGuestDO updateObj = new RoomGuestDO();
        updateObj.setId(reqVO.getGuestId());
        updateObj.setStatus(STATUS_CANCELLED);
        if (reqVO.getRemarks() != null) {
            updateObj.setRemarks(reqVO.getRemarks());
        }
        roomGuestMapper.updateById(updateObj);
        // 更新房间状态为空闲
        roomService.updateRoomStatus(guest.getRoomId(), ROOM_STATUS_VACANT);
        // 记录日志（取消预约 operationType=5）
        stayLogService.createStayLog(guest.getRoomId(), reqVO.getGuestId(), 5, reqVO.getRemarks());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transferRoom(TransferRoomReqVO reqVO) {
        // 校验住客存在
        RoomGuestDO guest = roomGuestMapper.selectById(reqVO.getGuestId());
        if (guest == null) {
            throw exception(ROOM_GUEST_NOT_EXISTS);
        }
        // 校验住客状态为在住
        if (!STATUS_ACTIVE.equals(guest.getStatus())) {
            throw exception(ROOM_GUEST_NOT_ACTIVE);
        }
        // 校验新房间存在
        RoomDO newRoom = roomService.getRoom(reqVO.getNewRoomId());
        if (newRoom == null) {
            throw exception(ROOM_NOT_EXISTS);
        }
        // 校验新房间状态为空闲
        if (!ROOM_STATUS_VACANT.equals(newRoom.getStatus())) {
            throw exception(ROOM_NOT_VACANT);
        }
        Long oldRoomId = guest.getRoomId();
        // 更新住客的房间ID
        RoomGuestDO updateObj = new RoomGuestDO();
        updateObj.setId(reqVO.getGuestId());
        updateObj.setRoomId(reqVO.getNewRoomId());
        if (reqVO.getRemarks() != null) {
            updateObj.setRemarks(reqVO.getRemarks());
        }
        roomGuestMapper.updateById(updateObj);

        // 检查旧房间是否还有其他在住住客
        List<RoomGuestDO> activeGuests = roomGuestMapper.selectListByRoomIdAndStatus(oldRoomId, STATUS_ACTIVE);
        if (activeGuests.isEmpty()) {
            // 旧房间状态改为空闲
            roomService.updateRoomStatus(oldRoomId, ROOM_STATUS_VACANT);
        }
        // 新房间状态改为已入住
        roomService.updateRoomStatus(reqVO.getNewRoomId(), ROOM_STATUS_OCCUPIED);
        // 记录日志（换房 operationType=2）
        stayLogService.createStayLog(reqVO.getNewRoomId(), reqVO.getGuestId(), 2,
                "从房间ID:" + oldRoomId + "换入" + (reqVO.getRemarks() != null ? " " + reqVO.getRemarks() : ""));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addCoGuest(RoomGuestSaveReqVO reqVO, Long primaryGuestId) {
        // 校验主住客存在
        RoomGuestDO primaryGuest = roomGuestMapper.selectById(primaryGuestId);
        if (primaryGuest == null) {
            throw exception(ROOM_GUEST_PRIMARY_NOT_EXISTS);
        }
        // 校验主住客状态为在住
        if (!STATUS_ACTIVE.equals(primaryGuest.getStatus())) {
            throw exception(ROOM_GUEST_NOT_ACTIVE);
        }
        // 校验主住客确实是主住客（不是同住人）
        if (Boolean.FALSE.equals(primaryGuest.getIsPrimary())) {
            throw exception(ROOM_GUEST_IS_CO_GUEST);
        }

        // 创建同住人记录
        RoomGuestDO coGuest = BeanUtils.toBean(reqVO, RoomGuestDO.class);
        coGuest.setRoomId(primaryGuest.getRoomId());  // 使用主住客的房间
        coGuest.setStatus(STATUS_ACTIVE);
        coGuest.setIsPrimary(false);  // 标记为同住人
        coGuest.setPrimaryGuestId(primaryGuestId);  // 关联主住客
        if (coGuest.getCheckInDate() == null) {
            coGuest.setCheckInDate(LocalDateTime.now());
        }
        roomGuestMapper.insert(coGuest);

        // 记录日志
        stayLogService.createStayLog(primaryGuest.getRoomId(), coGuest.getId(), 0, "添加同住人");

        return coGuest.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeCoGuest(Long guestId) {
        // 校验住客存在
        RoomGuestDO guest = roomGuestMapper.selectById(guestId);
        if (guest == null) {
            throw exception(ROOM_GUEST_NOT_EXISTS);
        }
        // 校验是同住人（不是主住客）
        if (Boolean.TRUE.equals(guest.getIsPrimary()) || guest.getPrimaryGuestId() == null) {
            throw exception(ROOM_GUEST_IS_CO_GUEST);  // 主住客不能通过此方法移除
        }
        // 更新为已退房状态（而不是删除）
        RoomGuestDO updateObj = new RoomGuestDO();
        updateObj.setId(guestId);
        updateObj.setStatus(STATUS_CHECKED_OUT);
        updateObj.setActualCheckOutDate(LocalDateTime.now());
        roomGuestMapper.updateById(updateObj);

        // 记录日志
        stayLogService.createStayLog(guest.getRoomId(), guestId, 1, "移除同住人");
        // 注意：移除同住人不影响房间状态
    }

    @Override
    public List<RoomGuestDO> getCoGuests(Long primaryGuestId) {
        return roomGuestMapper.selectCoGuestsByPrimaryGuestId(primaryGuestId);
    }

}
