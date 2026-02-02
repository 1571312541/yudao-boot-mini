package cn.iocoder.yudao.module.dim.service.visitor;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.dim.controller.admin.visitor.vo.VisitLogPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.visitor.vo.VisitLogSaveReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.visitor.vo.VisitorMonthStatisticsVO;
import cn.iocoder.yudao.module.dim.controller.admin.visitor.vo.VisitorYearCompareStatisticsVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.visitor.VisitLogDO;
import cn.iocoder.yudao.module.dim.dal.dataobject.visitor.VisitorDO;
import cn.iocoder.yudao.module.dim.dal.mysql.visitor.VisitLogMapper;
import cn.iocoder.yudao.module.dim.dal.mysql.visitor.VisitorMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.dim.enums.ErrorCodeConstants.VISITOR_NOT_EXISTS;

/**
 * 来访日志 Service 实现类
 */
@Service
@Validated
public class VisitLogServiceImpl implements VisitLogService {

    @Resource
    private VisitLogMapper visitLogMapper;

    @Resource
    private VisitorMapper visitorMapper;

    @Override
    public Long createVisitLog(VisitLogSaveReqVO createReqVO) {
        // 校验访客存在
        VisitorDO visitor = visitorMapper.selectById(createReqVO.getVisitorId());
        if (visitor == null) {
            throw exception(VISITOR_NOT_EXISTS);
        }
        // 插入
        VisitLogDO visitLog = BeanUtils.toBean(createReqVO, VisitLogDO.class);
        if (visitLog.getVisitDate() == null) {
            visitLog.setVisitDate(LocalDateTime.now());
        }
        if (visitLog.getStatus() == null) {
            visitLog.setStatus(0); // 来访
        }
        visitLogMapper.insert(visitLog);
        return visitLog.getId();
    }

    @Override
    public void updateVisitLog(VisitLogSaveReqVO updateReqVO) {
        // 校验存在
        validateVisitLogExists(updateReqVO.getId());
        // 更新
        VisitLogDO updateObj = BeanUtils.toBean(updateReqVO, VisitLogDO.class);
        visitLogMapper.updateById(updateObj);
    }

    @Override
    public void deleteVisitLog(Long id) {
        // 校验存在
        validateVisitLogExists(id);
        // 删除
        visitLogMapper.deleteById(id);
    }

    private void validateVisitLogExists(Long id) {
        if (visitLogMapper.selectById(id) == null) {
            throw exception(VISITOR_NOT_EXISTS);
        }
    }

    @Override
    public VisitLogDO getVisitLog(Long id) {
        return visitLogMapper.selectById(id);
    }

    @Override
    public PageResult<VisitLogDO> getVisitLogPage(VisitLogPageReqVO pageReqVO) {
        return visitLogMapper.selectPage(pageReqVO);
    }

    @Override
    public void leave(Long id) {
        VisitLogDO visitLog = visitLogMapper.selectById(id);
        if (visitLog == null) {
            throw exception(VISITOR_NOT_EXISTS);
        }
        visitLog.setLeaveDate(LocalDateTime.now());
        visitLog.setStatus(1); // 离场
        visitLogMapper.updateById(visitLog);
    }

    @Override
    public List<VisitorMonthStatisticsVO> countByYear(Integer year) {
        return visitLogMapper.countByYear(year);
    }

    @Override
    public VisitorYearCompareStatisticsVO getYearCompareStatistics(Integer year) {
        VisitorYearCompareStatisticsVO vo = new VisitorYearCompareStatisticsVO();
        vo.setYear(year);
        vo.setCurrent(visitLogMapper.countByYear(year));
        vo.setLastYear(visitLogMapper.countByYear(year - 1));
        return vo;
    }

}
