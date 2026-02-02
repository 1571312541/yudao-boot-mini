package cn.iocoder.yudao.module.dim.service.room;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.StayLogPageReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.room.StayLogDO;
import cn.iocoder.yudao.module.dim.dal.mysql.room.StayLogMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 住宿日志 Service 实现类
 */
@Service
@Validated
public class StayLogServiceImpl implements StayLogService {

    @Resource
    private StayLogMapper stayLogMapper;

    @Override
    public Long createStayLog(Long roomId, Long guestId, Integer operationType, String remarks) {
        StayLogDO log = new StayLogDO();
        log.setRoomId(roomId);
        log.setGuestId(guestId);
        log.setOperationType(operationType);
        log.setOperationTime(LocalDateTime.now());
        log.setRemarks(remarks);
        // 设置操作人
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        log.setOperatorId(loginUserId);
        stayLogMapper.insert(log);
        return log.getId();
    }

    @Override
    public StayLogDO getStayLog(Long id) {
        return stayLogMapper.selectById(id);
    }

    @Override
    public PageResult<StayLogDO> getStayLogPage(StayLogPageReqVO pageReqVO) {
        return stayLogMapper.selectPage(pageReqVO);
    }

    @Override
    public List<StayLogDO> getStayLogListByRoomId(Long roomId) {
        return stayLogMapper.selectListByRoomId(roomId);
    }

}
