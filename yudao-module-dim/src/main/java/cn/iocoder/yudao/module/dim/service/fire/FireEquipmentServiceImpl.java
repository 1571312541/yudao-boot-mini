package cn.iocoder.yudao.module.dim.service.fire;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.dim.controller.admin.fire.vo.FireEquipmentCheckReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.fire.vo.FireEquipmentPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.fire.vo.FireEquipmentSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.fire.FireEquipmentCheckLogDO;
import cn.iocoder.yudao.module.dim.dal.dataobject.fire.FireEquipmentDO;
import cn.iocoder.yudao.module.dim.dal.mysql.fire.FireEquipmentCheckLogMapper;
import cn.iocoder.yudao.module.dim.dal.mysql.fire.FireEquipmentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserNickname;
import static cn.iocoder.yudao.module.dim.enums.ErrorCodeConstants.FIRE_EQUIPMENT_NOT_EXISTS;

/**
 * 消防设备 Service 实现类
 */
@Service
@Validated
public class FireEquipmentServiceImpl implements FireEquipmentService {

    @Resource
    private FireEquipmentMapper fireEquipmentMapper;

    @Resource
    private FireEquipmentCheckLogMapper checkLogMapper;

    @Override
    public Long createFireEquipment(FireEquipmentSaveReqVO createReqVO) {
        FireEquipmentDO equipment = BeanUtils.toBean(createReqVO, FireEquipmentDO.class);
        fireEquipmentMapper.insert(equipment);
        return equipment.getId();
    }

    @Override
    public void updateFireEquipment(FireEquipmentSaveReqVO updateReqVO) {
        // 校验存在
        validateFireEquipmentExists(updateReqVO.getId());
        // 更新
        FireEquipmentDO updateObj = BeanUtils.toBean(updateReqVO, FireEquipmentDO.class);
        fireEquipmentMapper.updateById(updateObj);
    }

    @Override
    public void deleteFireEquipment(Long id) {
        // 校验存在
        validateFireEquipmentExists(id);
        // 删除
        fireEquipmentMapper.deleteById(id);
    }

    private void validateFireEquipmentExists(Long id) {
        if (fireEquipmentMapper.selectById(id) == null) {
            throw exception(FIRE_EQUIPMENT_NOT_EXISTS);
        }
    }

    @Override
    public FireEquipmentDO getFireEquipment(Long id) {
        return fireEquipmentMapper.selectById(id);
    }

    @Override
    public PageResult<FireEquipmentDO> getFireEquipmentPage(FireEquipmentPageReqVO pageReqVO) {
        return fireEquipmentMapper.selectPage(pageReqVO);
    }

    @Override
    public List<Integer> getBuildingNos() {
        return fireEquipmentMapper.selectDistinctBuildingNos();
    }

    @Override
    public List<Integer> getFloorsByBuilding(Integer buildingNo) {
        return fireEquipmentMapper.selectDistinctFloorsByBuilding(buildingNo);
    }

    @Override
    public List<FireEquipmentDO> getListByBuildingAndFloor(Integer buildingNo, Integer floor) {
        return fireEquipmentMapper.selectListByBuildingAndFloor(buildingNo, floor);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long checkEquipment(FireEquipmentCheckReqVO checkReqVO) {
        // 1. 校验设备存在
        FireEquipmentDO equipment = fireEquipmentMapper.selectById(checkReqVO.getEquipmentId());
        if (equipment == null) {
            throw exception(FIRE_EQUIPMENT_NOT_EXISTS);
        }

        // 2. 获取当前用户信息
        Long userId = getLoginUserId();
        String userName = getLoginUserNickname();
        if (userName == null) {
            userName = "系统";
        }

        // 3. 创建巡检日志
        FireEquipmentCheckLogDO checkLog = new FireEquipmentCheckLogDO();
        checkLog.setEquipmentId(checkReqVO.getEquipmentId());
        checkLog.setCheckResult(checkReqVO.getCheckResult());
        checkLog.setCheckRemark(checkReqVO.getCheckRemark());
        checkLog.setCheckTime(LocalDateTime.now());
        checkLog.setCheckerId(userId);
        checkLog.setCheckerName(userName);
        checkLogMapper.insert(checkLog);

        // 4. 更新设备状态和检查日期
        FireEquipmentDO updateObj = new FireEquipmentDO();
        updateObj.setId(equipment.getId());
        updateObj.setCheckDate(LocalDate.now());
        // 根据巡检结果更新状态: 0正常 -> status=0, 1故障 -> status=2
        updateObj.setStatus(checkReqVO.getCheckResult() == 0 ? 0 : 2);
        // 计算下次检查日期
        if (equipment.getMaintenanceCycle() != null && equipment.getMaintenanceCycle() > 0) {
            updateObj.setNextCheckDate(LocalDate.now().plusDays(equipment.getMaintenanceCycle()));
        }
        fireEquipmentMapper.updateById(updateObj);

        return checkLog.getId();
    }

    @Override
    public PageResult<FireEquipmentCheckLogDO> getCheckLogPage(Long equipmentId, PageParam pageParam) {
        return checkLogMapper.selectPageByEquipmentId(equipmentId, pageParam);
    }

    @Override
    public FireEquipmentCheckLogDO getLatestCheckLog(Long equipmentId) {
        return checkLogMapper.selectLatestByEquipmentId(equipmentId);
    }

    @Override
    public Map<String, Object> getStatistics() {
        Map<String, Object> result = new HashMap<>();
        // 总数
        result.put("total", fireEquipmentMapper.selectCount());
        // 按类型统计
        result.put("byType", fireEquipmentMapper.selectCountGroupByType());
        // 按状态统计
        result.put("byStatus", fireEquipmentMapper.selectCountGroupByStatus());
        // 按楼号统计
        result.put("byBuilding", fireEquipmentMapper.selectCountGroupByBuilding());
        return result;
    }

    @Override
    public List<FireEquipmentDO> getExpiringEquipments(int days) {
        return fireEquipmentMapper.selectExpiringEquipments(days);
    }

    @Override
    public List<FireEquipmentDO> getNeedCheckEquipments(int days) {
        return fireEquipmentMapper.selectNeedCheckEquipments(days);
    }

}
