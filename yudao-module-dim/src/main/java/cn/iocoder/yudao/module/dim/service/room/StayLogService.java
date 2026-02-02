package cn.iocoder.yudao.module.dim.service.room;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.StayLogPageReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.room.StayLogDO;

import java.util.List;

/**
 * 住宿日志 Service 接口
 */
public interface StayLogService {

    /**
     * 创建住宿日志
     *
     * @param roomId        房间ID
     * @param guestId       住客记录ID
     * @param operationType 操作类型
     * @param remarks       备注
     * @return 编号
     */
    Long createStayLog(Long roomId, Long guestId, Integer operationType, String remarks);

    /**
     * 获得住宿日志
     */
    StayLogDO getStayLog(Long id);

    /**
     * 获得住宿日志分页
     */
    PageResult<StayLogDO> getStayLogPage(StayLogPageReqVO pageReqVO);

    /**
     * 根据房间ID获得日志列表
     */
    List<StayLogDO> getStayLogListByRoomId(Long roomId);

}
