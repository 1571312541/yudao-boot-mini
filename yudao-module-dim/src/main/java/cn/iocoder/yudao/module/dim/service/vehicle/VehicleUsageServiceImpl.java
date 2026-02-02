package cn.iocoder.yudao.module.dim.service.vehicle;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.dim.controller.admin.vehicle.vo.VehicleUsagePageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.vehicle.vo.VehicleUsageSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.vehicle.VehicleDO;
import cn.iocoder.yudao.module.dim.dal.dataobject.vehicle.VehicleUsageDO;
import cn.iocoder.yudao.module.dim.dal.mysql.vehicle.VehicleMapper;
import cn.iocoder.yudao.module.dim.dal.mysql.vehicle.VehicleUsageMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.dim.enums.ErrorCodeConstants.VEHICLE_NOT_EXISTS;
import static cn.iocoder.yudao.module.dim.enums.ErrorCodeConstants.VEHICLE_USAGE_NOT_EXISTS;

/**
 * 用车记录 Service 实现类
 */
@Service
@Validated
public class VehicleUsageServiceImpl implements VehicleUsageService {

    @Resource
    private VehicleUsageMapper vehicleUsageMapper;

    @Resource
    private VehicleMapper vehicleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createVehicleUsage(VehicleUsageSaveReqVO createReqVO) {
        // 校验车辆存在
        VehicleDO vehicle = vehicleMapper.selectById(createReqVO.getVehicleId());
        if (vehicle == null) {
            throw exception(VEHICLE_NOT_EXISTS);
        }
        // 插入用车记录
        VehicleUsageDO usage = BeanUtils.toBean(createReqVO, VehicleUsageDO.class);
        usage.setStatus(0); // 使用中
        vehicleUsageMapper.insert(usage);
        // 更新车辆状态为使用中
        vehicle.setStatus(1);
        vehicleMapper.updateById(vehicle);
        return usage.getId();
    }

    @Override
    public void updateVehicleUsage(VehicleUsageSaveReqVO updateReqVO) {
        // 校验存在
        validateVehicleUsageExists(updateReqVO.getId());
        // 更新
        VehicleUsageDO updateObj = BeanUtils.toBean(updateReqVO, VehicleUsageDO.class);
        vehicleUsageMapper.updateById(updateObj);
    }

    @Override
    public void deleteVehicleUsage(Long id) {
        // 校验存在
        validateVehicleUsageExists(id);
        // 删除
        vehicleUsageMapper.deleteById(id);
    }

    private void validateVehicleUsageExists(Long id) {
        if (vehicleUsageMapper.selectById(id) == null) {
            throw exception(VEHICLE_USAGE_NOT_EXISTS);
        }
    }

    @Override
    public VehicleUsageDO getVehicleUsage(Long id) {
        return vehicleUsageMapper.selectById(id);
    }

    @Override
    public PageResult<VehicleUsageDO> getVehicleUsagePage(VehicleUsagePageReqVO pageReqVO) {
        return vehicleUsageMapper.selectPage(pageReqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void returnVehicle(Long id, Integer mileage) {
        // 校验存在
        VehicleUsageDO usage = vehicleUsageMapper.selectById(id);
        if (usage == null) {
            throw exception(VEHICLE_USAGE_NOT_EXISTS);
        }
        // 更新用车记录
        usage.setEndTime(LocalDateTime.now());
        usage.setMileage(mileage);
        usage.setStatus(1); // 已归还
        vehicleUsageMapper.updateById(usage);
        // 更新车辆状态为空闲
        VehicleDO vehicle = vehicleMapper.selectById(usage.getVehicleId());
        if (vehicle != null) {
            vehicle.setStatus(0);
            vehicleMapper.updateById(vehicle);
        }
    }

}
