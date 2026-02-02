package cn.iocoder.yudao.module.dim.service.vehicle;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.dim.controller.admin.vehicle.vo.VehiclePageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.vehicle.vo.VehicleSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.vehicle.VehicleDO;
import cn.iocoder.yudao.module.dim.dal.mysql.vehicle.VehicleMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.dim.enums.ErrorCodeConstants.VEHICLE_NOT_EXISTS;
import static cn.iocoder.yudao.module.dim.enums.ErrorCodeConstants.VEHICLE_PLATE_DUPLICATE;

/**
 * 公务车辆 Service 实现类
 */
@Service
@Validated
public class VehicleServiceImpl implements VehicleService {

    @Resource
    private VehicleMapper vehicleMapper;

    @Override
    public Long createVehicle(VehicleSaveReqVO createReqVO) {
        // 校验车牌号唯一
        validatePlateNumberUnique(null, createReqVO.getPlateNumber());
        // 插入
        VehicleDO vehicle = BeanUtils.toBean(createReqVO, VehicleDO.class);
        vehicleMapper.insert(vehicle);
        return vehicle.getId();
    }

    @Override
    public void updateVehicle(VehicleSaveReqVO updateReqVO) {
        // 校验存在
        validateVehicleExists(updateReqVO.getId());
        // 校验车牌号唯一
        validatePlateNumberUnique(updateReqVO.getId(), updateReqVO.getPlateNumber());
        // 更新
        VehicleDO updateObj = BeanUtils.toBean(updateReqVO, VehicleDO.class);
        vehicleMapper.updateById(updateObj);
    }

    @Override
    public void deleteVehicle(Long id) {
        // 校验存在
        validateVehicleExists(id);
        // 删除
        vehicleMapper.deleteById(id);
    }

    private void validateVehicleExists(Long id) {
        if (vehicleMapper.selectById(id) == null) {
            throw exception(VEHICLE_NOT_EXISTS);
        }
    }

    private void validatePlateNumberUnique(Long id, String plateNumber) {
        VehicleDO vehicle = vehicleMapper.selectByPlateNumber(plateNumber);
        if (vehicle == null) {
            return;
        }
        if (id == null) {
            throw exception(VEHICLE_PLATE_DUPLICATE);
        }
        if (!vehicle.getId().equals(id)) {
            throw exception(VEHICLE_PLATE_DUPLICATE);
        }
    }

    @Override
    public VehicleDO getVehicle(Long id) {
        return vehicleMapper.selectById(id);
    }

    @Override
    public PageResult<VehicleDO> getVehiclePage(VehiclePageReqVO pageReqVO) {
        return vehicleMapper.selectPage(pageReqVO);
    }

    @Override
    public List<VehicleDO> getVehicleList() {
        return vehicleMapper.selectList();
    }

}
