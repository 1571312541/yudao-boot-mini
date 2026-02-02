package cn.iocoder.yudao.module.dim.service.room;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.RoomImportRespVO;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.RoomImportVO;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.RoomPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.RoomSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.room.BuildingDO;
import cn.iocoder.yudao.module.dim.dal.dataobject.room.FloorDO;
import cn.iocoder.yudao.module.dim.dal.dataobject.room.RoomDO;
import cn.iocoder.yudao.module.dim.dal.mysql.room.BuildingMapper;
import cn.iocoder.yudao.module.dim.dal.mysql.room.FloorMapper;
import cn.iocoder.yudao.module.dim.dal.mysql.room.RoomMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.dim.enums.ErrorCodeConstants.*;

/**
 * 房间 Service 实现类
 */
@Service
@Validated
public class RoomServiceImpl implements RoomService {

    /** 房间状态：空闲 */
    private static final Integer ROOM_STATUS_VACANT = 0;

    @Resource
    private RoomMapper roomMapper;

    @Resource
    private FloorService floorService;

    @Resource
    private BuildingMapper buildingMapper;

    @Resource
    private FloorMapper floorMapper;

    @Override
    public Long createRoom(RoomSaveReqVO createReqVO) {
        // 校验楼层存在
        if (floorService.getFloor(createReqVO.getFloorId()) == null) {
            throw exception(FLOOR_NOT_EXISTS);
        }
        // 校验房间号唯一
        if (roomMapper.selectByRoomNumber(createReqVO.getRoomNumber()) != null) {
            throw exception(ROOM_NUMBER_DUPLICATE);
        }
        RoomDO room = BeanUtils.toBean(createReqVO, RoomDO.class);
        roomMapper.insert(room);
        return room.getId();
    }

    @Override
    public void updateRoom(RoomSaveReqVO updateReqVO) {
        validateRoomExists(updateReqVO.getId());
        // 校验房间号唯一
        RoomDO existRoom = roomMapper.selectByRoomNumber(updateReqVO.getRoomNumber());
        if (existRoom != null && !existRoom.getId().equals(updateReqVO.getId())) {
            throw exception(ROOM_NUMBER_DUPLICATE);
        }
        RoomDO updateObj = BeanUtils.toBean(updateReqVO, RoomDO.class);
        roomMapper.updateById(updateObj);
    }

    @Override
    public void deleteRoom(Long id) {
        validateRoomExists(id);
        roomMapper.deleteById(id);
    }

    private void validateRoomExists(Long id) {
        if (roomMapper.selectById(id) == null) {
            throw exception(ROOM_NOT_EXISTS);
        }
    }

    @Override
    public RoomDO getRoom(Long id) {
        return roomMapper.selectById(id);
    }

    @Override
    public PageResult<RoomDO> getRoomPage(RoomPageReqVO pageReqVO) {
        return roomMapper.selectPage(pageReqVO);
    }

    @Override
    public List<RoomDO> getRoomListByFloorId(Long floorId) {
        return roomMapper.selectListByFloorId(floorId);
    }

    @Override
    public void updateRoomStatus(Long id, Integer status) {
        validateRoomExists(id);
        RoomDO updateObj = new RoomDO();
        updateObj.setId(id);
        updateObj.setStatus(status);
        roomMapper.updateById(updateObj);
    }

    @Override
    public Map<Integer, Long> countRoomsByStatus() {
        List<Map<String, Object>> result = roomMapper.selectCountGroupByStatus();
        Map<Integer, Long> countMap = new HashMap<>();
        for (Map<String, Object> row : result) {
            Integer status = ((Number) row.get("status")).intValue();
            Long count = ((Number) row.get("count")).longValue();
            countMap.put(status, count);
        }
        return countMap;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoomImportRespVO importRooms(List<RoomImportVO> list) {
        RoomImportRespVO respVO = new RoomImportRespVO();
        int successCount = 0;
        int failureCount = 0;
        List<String> failureMessages = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            RoomImportVO importVO = list.get(i);
            int rowNum = i + 2; // Excel行号从2开始（第1行是表头）

            try {
                // 1. 校验必填字段
                if (!StringUtils.hasText(importVO.getBuildingName())) {
                    failureMessages.add("第" + rowNum + "行：楼栋名称不能为空");
                    failureCount++;
                    continue;
                }
                if (!StringUtils.hasText(importVO.getFloorName())) {
                    failureMessages.add("第" + rowNum + "行：楼层名称不能为空");
                    failureCount++;
                    continue;
                }
                if (!StringUtils.hasText(importVO.getRoomNumber())) {
                    failureMessages.add("第" + rowNum + "行：房间号不能为空");
                    failureCount++;
                    continue;
                }

                // 2. 根据楼栋名称查找楼栋ID
                BuildingDO building = buildingMapper.selectByName(importVO.getBuildingName());
                if (building == null) {
                    failureMessages.add("第" + rowNum + "行：楼栋[" + importVO.getBuildingName() + "]不存在");
                    failureCount++;
                    continue;
                }

                // 3. 根据楼层名称+楼栋ID查找楼层ID
                FloorDO floor = floorMapper.selectByBuildingIdAndName(building.getId(), importVO.getFloorName());
                if (floor == null) {
                    failureMessages.add("第" + rowNum + "行：楼栋[" + importVO.getBuildingName() + "]下楼层[" + importVO.getFloorName() + "]不存在");
                    failureCount++;
                    continue;
                }

                // 4. 校验房间号是否已存在
                if (roomMapper.selectByRoomNumber(importVO.getRoomNumber()) != null) {
                    failureMessages.add("第" + rowNum + "行：房间号[" + importVO.getRoomNumber() + "]已存在");
                    failureCount++;
                    continue;
                }

                // 5. 房间类型转换
                Integer roomType = convertRoomType(importVO.getRoomType());

                // 6. 插入房间记录
                RoomDO room = new RoomDO();
                room.setFloorId(floor.getId());
                room.setRoomNumber(importVO.getRoomNumber());
                room.setRoomType(roomType);
                room.setBedCount(importVO.getBedCount());
                room.setArea(importVO.getArea());
                room.setPrice(importVO.getPrice());
                room.setFacilities(importVO.getFacilities());
                room.setStatus(ROOM_STATUS_VACANT);  // 默认空闲状态
                roomMapper.insert(room);

                successCount++;
            } catch (Exception e) {
                failureMessages.add("第" + rowNum + "行：导入失败，" + e.getMessage());
                failureCount++;
            }
        }

        respVO.setSuccessCount(successCount);
        respVO.setFailureCount(failureCount);
        respVO.setFailureMessages(failureMessages);
        return respVO;
    }

    /**
     * 房间类型转换：单人间->0, 双人间->1, 多人间->2
     */
    private Integer convertRoomType(String roomTypeName) {
        if (!StringUtils.hasText(roomTypeName)) {
            return 0; // 默认单人间
        }
        switch (roomTypeName.trim()) {
            case "双人间":
                return 1;
            case "多人间":
                return 2;
            case "单人间":
            default:
                return 0;
        }
    }

}
