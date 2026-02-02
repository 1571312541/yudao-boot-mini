package cn.iocoder.yudao.module.dim.service.fire;

import cn.hutool.json.JSONUtil;
import cn.iocoder.yudao.module.dim.dal.dataobject.fire.BuildingConfigDO;
import cn.iocoder.yudao.module.dim.dal.mysql.fire.BuildingConfigMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 楼栋配置 Service 实现类
 */
@Service
@Validated
public class BuildingConfigServiceImpl implements BuildingConfigService {

    @Resource
    private BuildingConfigMapper buildingConfigMapper;

    @Override
    public List<BuildingConfigDO> getBuildingList() {
        return buildingConfigMapper.selectListEnabled();
    }

    @Override
    public BuildingConfigDO getBuildingByNo(Integer buildingNo) {
        return buildingConfigMapper.selectByBuildingNo(buildingNo);
    }

    @Override
    public List<Integer> getFloorsByBuildingNo(Integer buildingNo) {
        BuildingConfigDO config = buildingConfigMapper.selectByBuildingNo(buildingNo);
        if (config == null || config.getFloors() == null) {
            return new ArrayList<>();
        }
        // 解析JSON数组
        return JSONUtil.toList(config.getFloors(), Integer.class);
    }

}
