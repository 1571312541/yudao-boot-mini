package cn.iocoder.yudao.module.dim.dal.mysql.vehicle;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.dim.controller.admin.vehicle.vo.VehicleUsagePageReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.vehicle.VehicleUsageDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VehicleUsageMapper extends BaseMapperX<VehicleUsageDO> {

    default PageResult<VehicleUsageDO> selectPage(VehicleUsagePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<VehicleUsageDO>()
                .eqIfPresent(VehicleUsageDO::getVehicleId, reqVO.getVehicleId())
                .likeIfPresent(VehicleUsageDO::getUserName, reqVO.getUserName())
                .eqIfPresent(VehicleUsageDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(VehicleUsageDO::getStartTime, reqVO.getStartTime())
                .orderByDesc(VehicleUsageDO::getId));
    }

    default List<VehicleUsageDO> selectListByVehicleId(Long vehicleId) {
        return selectList(VehicleUsageDO::getVehicleId, vehicleId);
    }

}
