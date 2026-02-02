package cn.iocoder.yudao.module.dim.service.room;

import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.FloorSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.room.FloorDO;
import cn.iocoder.yudao.module.dim.dal.mysql.room.FloorMapper;
import cn.iocoder.yudao.module.dim.dal.mysql.room.RoomMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.dim.enums.ErrorCodeConstants.*;

/**
 * 楼层 Service 实现类
 */
@Service
@Validated
public class FloorServiceImpl implements FloorService {

    @Resource
    private FloorMapper floorMapper;

    @Resource
    private RoomMapper roomMapper;

    @Resource
    private BuildingService buildingService;

    @Override
    public Long createFloor(FloorSaveReqVO createReqVO) {
        // 校验楼栋存在
        if (buildingService.getBuilding(createReqVO.getBuildingId()) == null) {
            throw exception(BUILDING_NOT_EXISTS);
        }
        FloorDO floor = BeanUtils.toBean(createReqVO, FloorDO.class);
        floorMapper.insert(floor);
        return floor.getId();
    }

    @Override
    public void updateFloor(FloorSaveReqVO updateReqVO) {
        validateFloorExists(updateReqVO.getId());
        FloorDO updateObj = BeanUtils.toBean(updateReqVO, FloorDO.class);
        floorMapper.updateById(updateObj);
    }

    @Override
    public void deleteFloor(Long id) {
        validateFloorExists(id);
        // 校验是否有房间
        if (roomMapper.selectCountByFloorId(id) > 0) {
            throw exception(FLOOR_HAS_ROOMS);
        }
        floorMapper.deleteById(id);
    }

    private void validateFloorExists(Long id) {
        if (floorMapper.selectById(id) == null) {
            throw exception(FLOOR_NOT_EXISTS);
        }
    }

    @Override
    public FloorDO getFloor(Long id) {
        return floorMapper.selectById(id);
    }

    @Override
    public List<FloorDO> getFloorListByBuildingId(Long buildingId) {
        return floorMapper.selectListByBuildingId(buildingId);
    }

}
