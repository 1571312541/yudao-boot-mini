package cn.iocoder.yudao.module.dim.dal.mysql.vehicle;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.dim.controller.admin.vehicle.vo.VehiclePageReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.vehicle.VehicleDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VehicleMapper extends BaseMapperX<VehicleDO> {

    default PageResult<VehicleDO> selectPage(VehiclePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<VehicleDO>()
                .likeIfPresent(VehicleDO::getName, reqVO.getName())
                .likeIfPresent(VehicleDO::getPlateNumber, reqVO.getPlateNumber())
                .likeIfPresent(VehicleDO::getDriver, reqVO.getDriver())
                .likeIfPresent(VehicleDO::getLeadingOfficial, reqVO.getLeadingOfficial())
                .eqIfPresent(VehicleDO::getStatus, reqVO.getStatus())
                .eqIfPresent(VehicleDO::getType, reqVO.getType())
                .betweenIfPresent(VehicleDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(VehicleDO::getId));
    }

    default VehicleDO selectByPlateNumber(String plateNumber) {
        return selectOne(VehicleDO::getPlateNumber, plateNumber);
    }

}
