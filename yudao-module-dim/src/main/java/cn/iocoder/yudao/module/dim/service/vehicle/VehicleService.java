package cn.iocoder.yudao.module.dim.service.vehicle;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.dim.controller.admin.vehicle.vo.VehiclePageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.vehicle.vo.VehicleSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.vehicle.VehicleDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 公务车辆 Service 接口
 */
public interface VehicleService {

    /**
     * 创建公务车辆
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createVehicle(@Valid VehicleSaveReqVO createReqVO);

    /**
     * 更新公务车辆
     *
     * @param updateReqVO 更新信息
     */
    void updateVehicle(@Valid VehicleSaveReqVO updateReqVO);

    /**
     * 删除公务车辆
     *
     * @param id 编号
     */
    void deleteVehicle(Long id);

    /**
     * 获得公务车辆
     *
     * @param id 编号
     * @return 公务车辆
     */
    VehicleDO getVehicle(Long id);

    /**
     * 获得公务车辆分页
     *
     * @param pageReqVO 分页查询
     * @return 公务车辆分页
     */
    PageResult<VehicleDO> getVehiclePage(VehiclePageReqVO pageReqVO);

    /**
     * 获得所有公务车辆列表
     *
     * @return 公务车辆列表
     */
    List<VehicleDO> getVehicleList();

}
