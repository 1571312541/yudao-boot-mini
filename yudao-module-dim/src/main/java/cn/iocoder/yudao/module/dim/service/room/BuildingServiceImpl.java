package cn.iocoder.yudao.module.dim.service.room;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.BuildingPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.BuildingSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.room.BuildingDO;
import cn.iocoder.yudao.module.dim.dal.mysql.room.BuildingMapper;
import cn.iocoder.yudao.module.dim.dal.mysql.room.FloorMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.dim.enums.ErrorCodeConstants.*;

/**
 * 楼栋 Service 实现类
 */
@Service
@Validated
public class BuildingServiceImpl implements BuildingService {

    @Resource
    private BuildingMapper buildingMapper;

    @Resource
    private FloorMapper floorMapper;

    @Override
    public Long createBuilding(BuildingSaveReqVO createReqVO) {
        BuildingDO building = BeanUtils.toBean(createReqVO, BuildingDO.class);
        buildingMapper.insert(building);
        return building.getId();
    }

    @Override
    public void updateBuilding(BuildingSaveReqVO updateReqVO) {
        validateBuildingExists(updateReqVO.getId());
        BuildingDO updateObj = BeanUtils.toBean(updateReqVO, BuildingDO.class);
        buildingMapper.updateById(updateObj);
    }

    @Override
    public void deleteBuilding(Long id) {
        validateBuildingExists(id);
        // 校验是否有楼层
        if (floorMapper.selectCountByBuildingId(id) > 0) {
            throw exception(BUILDING_HAS_FLOORS);
        }
        buildingMapper.deleteById(id);
    }

    private void validateBuildingExists(Long id) {
        if (buildingMapper.selectById(id) == null) {
            throw exception(BUILDING_NOT_EXISTS);
        }
    }

    @Override
    public BuildingDO getBuilding(Long id) {
        return buildingMapper.selectById(id);
    }

    @Override
    public PageResult<BuildingDO> getBuildingPage(BuildingPageReqVO pageReqVO) {
        return buildingMapper.selectPage(pageReqVO);
    }

    @Override
    public List<BuildingDO> getBuildingList(String name, Integer status) {
        return buildingMapper.selectList(name, status);
    }

}
