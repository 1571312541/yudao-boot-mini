package cn.iocoder.yudao.module.dim.service.room;

import cn.iocoder.yudao.module.dim.controller.admin.room.vo.AmountStatisticsRespVO;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.YearlyStatisticsRespVO;

import java.time.LocalDateTime;

/**
 * 住宿统计 Service 接口
 */
public interface StayStatisticsService {

    /**
     * 获取年度统计
     *
     * @param year 年份
     * @return 年度统计
     */
    YearlyStatisticsRespVO getYearlyStatistics(Integer year);

    /**
     * 获取金额统计
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 金额统计
     */
    AmountStatisticsRespVO getAmountStatistics(LocalDateTime startTime, LocalDateTime endTime);

}
