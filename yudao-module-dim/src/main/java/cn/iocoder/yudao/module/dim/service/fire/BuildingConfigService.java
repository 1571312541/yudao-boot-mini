package cn.iocoder.yudao.module.dim.service.fire;

import cn.iocoder.yudao.module.dim.dal.dataobject.fire.BuildingConfigDO;

import java.util.List;

/**
 * 楼栋配置 Service 接口
 */
public interface BuildingConfigService {

    /**
     * 获取所有启用的楼栋列表
     *
     * @return 楼栋列表
     */
    List<BuildingConfigDO> getBuildingList();

    /**
     * 获取指定楼号的楼栋配置
     *
     * @param buildingNo 楼号
     * @return 楼栋配置
     */
    BuildingConfigDO getBuildingByNo(Integer buildingNo);

    /**
     * 获取指定楼号的楼层列表
     *
     * @param buildingNo 楼号
     * @return 楼层列表
     */
    List<Integer> getFloorsByBuildingNo(Integer buildingNo);

}
