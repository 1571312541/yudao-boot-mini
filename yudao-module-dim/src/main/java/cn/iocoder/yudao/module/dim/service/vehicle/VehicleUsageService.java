package cn.iocoder.yudao.module.dim.service.vehicle;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.dim.controller.admin.vehicle.vo.VehicleUsagePageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.vehicle.vo.VehicleUsageSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.vehicle.VehicleUsageDO;

import javax.validation.Valid;

/**
 * 用车记录 Service 接口
 */
public interface VehicleUsageService {

    /**
     * 创建用车记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createVehicleUsage(@Valid VehicleUsageSaveReqVO createReqVO);

    /**
     * 更新用车记录
     *
     * @param updateReqVO 更新信息
     */
    void updateVehicleUsage(@Valid VehicleUsageSaveReqVO updateReqVO);

    /**
     * 删除用车记录
     *
     * @param id 编号
     */
    void deleteVehicleUsage(Long id);

    /**
     * 获得用车记录
     *
     * @param id 编号
     * @return 用车记录
     */
    VehicleUsageDO getVehicleUsage(Long id);

    /**
     * 获得用车记录分页
     *
     * @param pageReqVO 分页查询
     * @return 用车记录分页
     */
    PageResult<VehicleUsageDO> getVehicleUsagePage(VehicleUsagePageReqVO pageReqVO);

    /**
     * 归还车辆
     *
     * @param id 用车记录编号
     * @param mileage 行驶里程
     */
    void returnVehicle(Long id, Integer mileage);

}
