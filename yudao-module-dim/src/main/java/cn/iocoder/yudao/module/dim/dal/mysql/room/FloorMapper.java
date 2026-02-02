package cn.iocoder.yudao.module.dim.dal.mysql.room;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.dim.dal.dataobject.room.FloorDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FloorMapper extends BaseMapperX<FloorDO> {

    default List<FloorDO> selectListByBuildingId(Long buildingId) {
        return selectList(new LambdaQueryWrapperX<FloorDO>()
                .eqIfPresent(FloorDO::getBuildingId, buildingId)
                .orderByAsc(FloorDO::getFloorNumber));
    }

    default Long selectCountByBuildingId(Long buildingId) {
        return selectCount(FloorDO::getBuildingId, buildingId);
    }

    default FloorDO selectByBuildingIdAndName(Long buildingId, String name) {
        return selectOne(new LambdaQueryWrapperX<FloorDO>()
                .eq(FloorDO::getBuildingId, buildingId)
                .eq(FloorDO::getName, name));
    }

}
