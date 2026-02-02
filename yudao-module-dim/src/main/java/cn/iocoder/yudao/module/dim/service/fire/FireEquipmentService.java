package cn.iocoder.yudao.module.dim.service.fire;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.dim.controller.admin.fire.vo.FireEquipmentCheckReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.fire.vo.FireEquipmentPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.fire.vo.FireEquipmentSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.fire.FireEquipmentCheckLogDO;
import cn.iocoder.yudao.module.dim.dal.dataobject.fire.FireEquipmentDO;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 消防设备 Service 接口
 */
public interface FireEquipmentService {

    /**
     * 创建消防设备
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createFireEquipment(@Valid FireEquipmentSaveReqVO createReqVO);

    /**
     * 更新消防设备
     *
     * @param updateReqVO 更新信息
     */
    void updateFireEquipment(@Valid FireEquipmentSaveReqVO updateReqVO);

    /**
     * 删除消防设备
     *
     * @param id 编号
     */
    void deleteFireEquipment(Long id);

    /**
     * 获得消防设备
     *
     * @param id 编号
     * @return 消防设备
     */
    FireEquipmentDO getFireEquipment(Long id);

    /**
     * 获得消防设备分页
     *
     * @param pageReqVO 分页查询
     * @return 消防设备分页
     */
    PageResult<FireEquipmentDO> getFireEquipmentPage(FireEquipmentPageReqVO pageReqVO);

    /**
     * 获得所有楼号列表
     *
     * @return 楼号列表
     */
    List<Integer> getBuildingNos();

    /**
     * 获得指定楼号的楼层列表
     *
     * @param buildingNo 楼号
     * @return 楼层列表
     */
    List<Integer> getFloorsByBuilding(Integer buildingNo);

    /**
     * 按楼栋楼层获取设备列表
     *
     * @param buildingNo 楼号
     * @param floor 楼层
     * @return 设备列表
     */
    List<FireEquipmentDO> getListByBuildingAndFloor(Integer buildingNo, Integer floor);

    /**
     * 执行巡检(更新设备状态 + 创建日志)
     *
     * @param checkReqVO 巡检请求
     * @return 巡检日志ID
     */
    Long checkEquipment(@Valid FireEquipmentCheckReqVO checkReqVO);

    /**
     * 获取设备巡检历史分页
     *
     * @param equipmentId 设备ID
     * @param pageParam 分页参数
     * @return 巡检日志分页
     */
    PageResult<FireEquipmentCheckLogDO> getCheckLogPage(Long equipmentId, PageParam pageParam);

    /**
     * 获取设备最近一次巡检记录
     *
     * @param equipmentId 设备ID
     * @return 巡检日志
     */
    FireEquipmentCheckLogDO getLatestCheckLog(Long equipmentId);

    /**
     * 获取设备统计数据
     *
     * @return 统计数据(总数、按类型、按状态、按楼号)
     */
    Map<String, Object> getStatistics();

    /**
     * 获取即将到期的设备
     *
     * @param days 天数
     * @return 设备列表
     */
    List<FireEquipmentDO> getExpiringEquipments(int days);

    /**
     * 获取需巡检的设备
     *
     * @param days 天数
     * @return 设备列表
     */
    List<FireEquipmentDO> getNeedCheckEquipments(int days);

}
