package cn.iocoder.yudao.module.dim.dal.mysql.fire;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.dim.dal.dataobject.fire.BuildingConfigDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BuildingConfigMapper extends BaseMapperX<BuildingConfigDO> {

    /**
     * 查询所有启用的楼栋(按排序)
     */
    default List<BuildingConfigDO> selectListEnabled() {
        return selectList(new LambdaQueryWrapperX<BuildingConfigDO>()
                .eq(BuildingConfigDO::getStatus, 1)
                .orderByAsc(BuildingConfigDO::getSort));
    }

    /**
     * 按楼号查询楼栋配置
     */
    default BuildingConfigDO selectByBuildingNo(Integer buildingNo) {
        return selectOne(new LambdaQueryWrapperX<BuildingConfigDO>()
                .eq(BuildingConfigDO::getBuildingNo, buildingNo));
    }

}
