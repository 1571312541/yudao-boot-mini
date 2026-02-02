package cn.iocoder.yudao.module.dim.service.room;

import cn.iocoder.yudao.module.dim.controller.admin.room.vo.AmountStatisticsRespVO;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.YearlyStatisticsRespVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.room.RoomGuestDO;
import cn.iocoder.yudao.module.dim.dal.dataobject.room.StaySettlementDO;
import cn.iocoder.yudao.module.dim.dal.mysql.room.RoomGuestMapper;
import cn.iocoder.yudao.module.dim.dal.mysql.room.StaySettlementMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 住宿统计 Service 实现类
 */
@Service
@Validated
public class StayStatisticsServiceImpl implements StayStatisticsService {

    /** 住宿状态：在住 */
    private static final Integer STATUS_ACTIVE = 0;
    /** 住宿状态：已退房 */
    private static final Integer STATUS_CHECKED_OUT = 1;

    @Resource
    private RoomGuestMapper roomGuestMapper;

    @Resource
    private StaySettlementMapper staySettlementMapper;

    @Override
    public YearlyStatisticsRespVO getYearlyStatistics(Integer year) {
        YearlyStatisticsRespVO respVO = new YearlyStatisticsRespVO();
        respVO.setYear(year);

        List<YearlyStatisticsRespVO.MonthData> monthlyData = new ArrayList<>();

        for (int month = 1; month <= 12; month++) {
            LocalDateTime startOfMonth = LocalDateTime.of(year, month, 1, 0, 0, 0);
            LocalDateTime endOfMonth = startOfMonth.plusMonths(1).minusSeconds(1);

            YearlyStatisticsRespVO.MonthData monthData = new YearlyStatisticsRespVO.MonthData();
            monthData.setMonth(month);

            // 统计入住人次（当月入住的记录）
            Long checkInCount = roomGuestMapper.selectCount(new LambdaQueryWrapper<RoomGuestDO>()
                    .ge(RoomGuestDO::getCheckInDate, startOfMonth)
                    .le(RoomGuestDO::getCheckInDate, endOfMonth));
            monthData.setCheckInCount(checkInCount != null ? checkInCount.intValue() : 0);

            // 统计退房人次（当月退房的记录）
            Long checkOutCount = roomGuestMapper.selectCount(new LambdaQueryWrapper<RoomGuestDO>()
                    .eq(RoomGuestDO::getStatus, STATUS_CHECKED_OUT)
                    .ge(RoomGuestDO::getActualCheckOutDate, startOfMonth)
                    .le(RoomGuestDO::getActualCheckOutDate, endOfMonth));
            monthData.setCheckOutCount(checkOutCount != null ? checkOutCount.intValue() : 0);

            // 统计住宿天数（从结算记录中获取）
            List<StaySettlementDO> settlements = staySettlementMapper.selectList(
                    new LambdaQueryWrapper<StaySettlementDO>()
                            .ge(StaySettlementDO::getCheckOutDate, startOfMonth)
                            .le(StaySettlementDO::getCheckOutDate, endOfMonth));
            int totalStayDays = 0;
            for (StaySettlementDO settlement : settlements) {
                if (settlement.getStayDays() != null) {
                    totalStayDays += settlement.getStayDays();
                }
            }
            monthData.setStayDays(totalStayDays);

            monthlyData.add(monthData);
        }

        respVO.setMonthlyData(monthlyData);
        return respVO;
    }

    @Override
    public AmountStatisticsRespVO getAmountStatistics(LocalDateTime startTime, LocalDateTime endTime) {
        AmountStatisticsRespVO respVO = new AmountStatisticsRespVO();

        BigDecimal totalAmount;
        BigDecimal settledAmount;
        BigDecimal unsettledAmount;

        if (startTime != null && endTime != null) {
            // 按时间范围统计
            totalAmount = staySettlementMapper.selectTotalAmountByTimeRange(startTime, endTime);
            settledAmount = staySettlementMapper.selectSettledAmountByTimeRange(startTime, endTime);
            unsettledAmount = totalAmount.subtract(settledAmount);
        } else {
            // 统计全部
            totalAmount = staySettlementMapper.selectTotalAmount();
            settledAmount = staySettlementMapper.selectSettledAmount();
            unsettledAmount = staySettlementMapper.selectUnsettledAmount();
        }

        respVO.setTotalAmount(totalAmount != null ? totalAmount : BigDecimal.ZERO);
        respVO.setSettledAmount(settledAmount != null ? settledAmount : BigDecimal.ZERO);
        respVO.setUnsettledAmount(unsettledAmount != null ? unsettledAmount : BigDecimal.ZERO);

        // 按单位统计
        List<AmountStatisticsRespVO.DeptAmount> deptAmounts = new ArrayList<>();

        // 获取所有住客的单位信息和对应结算
        List<RoomGuestDO> allGuests = roomGuestMapper.selectList(new LambdaQueryWrapper<RoomGuestDO>()
                .eq(RoomGuestDO::getStatus, STATUS_CHECKED_OUT)
                .isNotNull(RoomGuestDO::getDeptName));

        Map<String, BigDecimal> deptAmountMap = new HashMap<>();
        Map<String, Integer> deptCountMap = new HashMap<>();

        for (RoomGuestDO guest : allGuests) {
            String deptName = guest.getDeptName();
            if (deptName == null || deptName.isEmpty()) {
                deptName = "未知单位";
            }

            // 获取该住客的结算记录
            StaySettlementDO settlement = staySettlementMapper.selectByGuestId(guest.getId());
            if (settlement != null && settlement.getTotalAmount() != null) {
                // 检查是否在时间范围内
                if (startTime != null && endTime != null) {
                    if (settlement.getCheckOutDate() != null &&
                            (settlement.getCheckOutDate().isBefore(startTime) || settlement.getCheckOutDate().isAfter(endTime))) {
                        continue;
                    }
                }

                deptAmountMap.merge(deptName, settlement.getTotalAmount(), BigDecimal::add);
                deptCountMap.merge(deptName, 1, Integer::sum);
            }
        }

        for (Map.Entry<String, BigDecimal> entry : deptAmountMap.entrySet()) {
            AmountStatisticsRespVO.DeptAmount deptAmount = new AmountStatisticsRespVO.DeptAmount();
            deptAmount.setDeptName(entry.getKey());
            deptAmount.setAmount(entry.getValue());
            deptAmount.setCount(deptCountMap.getOrDefault(entry.getKey(), 0));
            deptAmounts.add(deptAmount);
        }

        respVO.setDeptAmounts(deptAmounts);
        return respVO;
    }

}
