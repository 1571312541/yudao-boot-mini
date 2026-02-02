package cn.iocoder.yudao.module.dim.service.visitor;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.dim.controller.admin.visitor.vo.VisitLogPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.visitor.vo.VisitLogSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.visitor.VisitLogDO;

import cn.iocoder.yudao.module.dim.controller.admin.visitor.vo.VisitorMonthStatisticsVO;
import cn.iocoder.yudao.module.dim.controller.admin.visitor.vo.VisitorYearCompareStatisticsVO;

import javax.validation.Valid;
import java.util.List;

/**
 * 来访日志 Service 接口
 */
public interface VisitLogService {

    /**
     * 创建来访日志
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createVisitLog(@Valid VisitLogSaveReqVO createReqVO);

    /**
     * 更新来访日志
     *
     * @param updateReqVO 更新信息
     */
    void updateVisitLog(@Valid VisitLogSaveReqVO updateReqVO);

    /**
     * 删除来访日志
     *
     * @param id 编号
     */
    void deleteVisitLog(Long id);

    /**
     * 获得来访日志
     *
     * @param id 编号
     * @return 来访日志
     */
    VisitLogDO getVisitLog(Long id);

    /**
     * 获得来访日志分页
     *
     * @param pageReqVO 分页查询
     * @return 来访日志分页
     */
    PageResult<VisitLogDO> getVisitLogPage(VisitLogPageReqVO pageReqVO);

    /**
     * 访客离场
     *
     * @param id 来访日志编号
     */
    void leave(Long id);

    /**
     * 统计指定年份每月的访客数量
     *
     * @param year 年份
     * @return 每月统计列表
     */
    List<VisitorMonthStatisticsVO> countByYear(Integer year);

    /**
     * 获取年度对比统计（当年与去年同期）
     *
     * @param year 年份
     * @return 年度对比统计
     */
    VisitorYearCompareStatisticsVO getYearCompareStatistics(Integer year);

}
