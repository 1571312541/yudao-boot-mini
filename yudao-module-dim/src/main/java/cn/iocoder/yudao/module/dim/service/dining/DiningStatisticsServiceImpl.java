package cn.iocoder.yudao.module.dim.service.dining;

import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.statistics.*;
import cn.iocoder.yudao.module.dim.dal.dataobject.dining.DiningRecordDO;
import cn.iocoder.yudao.module.dim.dal.dataobject.dining.DiningRegistrationDO;
import cn.iocoder.yudao.module.dim.dal.mysql.dining.DiningRecordMapper;
import cn.iocoder.yudao.module.dim.dal.mysql.dining.DiningRegistrationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 餐饮统计 Service 实现类
 */
@Slf4j
@Service
@Validated
public class DiningStatisticsServiceImpl implements DiningStatisticsService {

    @Resource
    private DiningRecordMapper diningRecordMapper;

    @Resource
    private DiningRegistrationMapper diningRegistrationMapper;

    @Resource
    private DiningPriceService diningPriceService;

    @Override
    public List<DiningStatsByPersonTypeRespVO> getStatsByPersonType(LocalDate startDate, LocalDate endDate, Integer mealType) {
        // 查询日期范围内的就餐记录
        List<DiningRecordDO> records = diningRecordMapper.selectListByDateRangeAndMealType(startDate, endDate, mealType);

        // 按人员类型分组统计
        Map<Integer, DiningStatsByPersonTypeRespVO> statsMap = new HashMap<>();

        // 初始化所有人员类型（0-5）
        for (int i = 0; i <= 5; i++) {
            DiningStatsByPersonTypeRespVO vo = new DiningStatsByPersonTypeRespVO();
            vo.setPersonType(i);
            vo.setPersonTypeName(DiningStatsByPersonTypeRespVO.getPersonTypeName(i));
            statsMap.put(i, vo);
        }

        // 遍历记录进行统计
        for (DiningRecordDO record : records) {
            Integer personType = record.getPersonType() != null ? record.getPersonType() : 0;
            DiningStatsByPersonTypeRespVO vo = statsMap.get(personType);
            if (vo == null) {
                vo = new DiningStatsByPersonTypeRespVO();
                vo.setPersonType(personType);
                vo.setPersonTypeName(DiningStatsByPersonTypeRespVO.getPersonTypeName(personType));
                statsMap.put(personType, vo);
            }

            // 根据餐别累加
            Integer recordMealType = record.getMealType();
            if (recordMealType != null) {
                switch (recordMealType) {
                    case 0:
                        vo.setBreakfastCount(vo.getBreakfastCount() + 1);
                        break;
                    case 1:
                        vo.setLunchCount(vo.getLunchCount() + 1);
                        break;
                    case 2:
                        vo.setDinnerCount(vo.getDinnerCount() + 1);
                        break;
                }
            }

            // 累加金额
            if (record.getAmount() != null) {
                vo.setTotalAmount(vo.getTotalAmount().add(record.getAmount()));
            }
        }

        // 计算总人次并返回有数据的统计结果
        List<DiningStatsByPersonTypeRespVO> result = new ArrayList<>();
        for (DiningStatsByPersonTypeRespVO vo : statsMap.values()) {
            vo.setTotalCount(vo.getBreakfastCount() + vo.getLunchCount() + vo.getDinnerCount());
            if (vo.getTotalCount() > 0) {
                result.add(vo);
            }
        }

        // 按人员类型排序
        result.sort(Comparator.comparing(DiningStatsByPersonTypeRespVO::getPersonType));

        return result;
    }

    @Override
    public List<DiningStatsByDeptRespVO> getStatsByDept(LocalDate startDate, LocalDate endDate) {
        // 查询日期范围内的就餐记录
        List<DiningRecordDO> records = diningRecordMapper.selectListByDateRangeAndMealType(startDate, endDate, null);

        // 按单位分组统计
        Map<String, DiningStatsByDeptRespVO> statsMap = new HashMap<>();

        for (DiningRecordDO record : records) {
            String deptName = record.getDeptName() != null ? record.getDeptName() : "未知";
            DiningStatsByDeptRespVO vo = statsMap.computeIfAbsent(deptName, k -> {
                DiningStatsByDeptRespVO newVo = new DiningStatsByDeptRespVO();
                newVo.setDeptName(k);
                return newVo;
            });

            // 根据餐别累加
            Integer mealType = record.getMealType();
            if (mealType != null) {
                switch (mealType) {
                    case 0:
                        vo.setBreakfastCount(vo.getBreakfastCount() + 1);
                        break;
                    case 1:
                        vo.setLunchCount(vo.getLunchCount() + 1);
                        break;
                    case 2:
                        vo.setDinnerCount(vo.getDinnerCount() + 1);
                        break;
                }
            }

            // 累加金额
            if (record.getAmount() != null) {
                vo.setTotalAmount(vo.getTotalAmount().add(record.getAmount()));
            }
        }

        // 计算总人次并返回结果
        List<DiningStatsByDeptRespVO> result = new ArrayList<>();
        for (DiningStatsByDeptRespVO vo : statsMap.values()) {
            vo.setTotalCount(vo.getBreakfastCount() + vo.getLunchCount() + vo.getDinnerCount());
            if (vo.getTotalCount() > 0) {
                result.add(vo);
            }
        }

        // 按单位名称排序
        result.sort(Comparator.comparing(DiningStatsByDeptRespVO::getDeptName));

        return result;
    }

    @Override
    public DiningMonthlyStatsRespVO getMonthlyStats(Integer year, Integer month) {
        // 计算月份的起止日期
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        // 查询该月的就餐记录
        List<DiningRecordDO> records = diningRecordMapper.selectListByDateRangeAndMealType(startDate, endDate, null);

        // 按日期分组统计
        Map<LocalDate, DiningDailyStatVO> dailyStatsMap = new LinkedHashMap<>();

        // 初始化该月每一天
        for (int day = 1; day <= yearMonth.lengthOfMonth(); day++) {
            LocalDate date = yearMonth.atDay(day);
            DiningDailyStatVO vo = new DiningDailyStatVO();
            vo.setDate(date);
            dailyStatsMap.put(date, vo);
        }

        // 遍历记录进行统计
        for (DiningRecordDO record : records) {
            LocalDate diningDate = record.getDiningDate();
            DiningDailyStatVO vo = dailyStatsMap.get(diningDate);
            if (vo == null) {
                continue;
            }

            // 根据餐别累加
            Integer mealType = record.getMealType();
            if (mealType != null) {
                switch (mealType) {
                    case 0:
                        vo.setBreakfastCount(vo.getBreakfastCount() + 1);
                        break;
                    case 1:
                        vo.setLunchCount(vo.getLunchCount() + 1);
                        break;
                    case 2:
                        vo.setDinnerCount(vo.getDinnerCount() + 1);
                        break;
                }
            }

            // 累加金额
            if (record.getAmount() != null) {
                vo.setTotalAmount(vo.getTotalAmount().add(record.getAmount()));
            }
        }

        // 计算每日总人次
        List<DiningDailyStatVO> dailyStats = new ArrayList<>();
        DiningStatsSummaryVO summary = new DiningStatsSummaryVO();

        for (DiningDailyStatVO vo : dailyStatsMap.values()) {
            vo.setTotalCount(vo.getBreakfastCount() + vo.getLunchCount() + vo.getDinnerCount());
            dailyStats.add(vo);

            // 累加到月度汇总
            summary.setBreakfastCount(summary.getBreakfastCount() + vo.getBreakfastCount());
            summary.setLunchCount(summary.getLunchCount() + vo.getLunchCount());
            summary.setDinnerCount(summary.getDinnerCount() + vo.getDinnerCount());
            summary.setTotalAmount(summary.getTotalAmount().add(vo.getTotalAmount()));
        }
        summary.setTotalCount(summary.getBreakfastCount() + summary.getLunchCount() + summary.getDinnerCount());

        // 构建返回结果
        DiningMonthlyStatsRespVO result = new DiningMonthlyStatsRespVO();
        result.setYear(year);
        result.setMonth(month);
        result.setDailyStats(dailyStats);
        result.setSummary(summary);

        return result;
    }

    @Override
    public DiningYearlyStatsRespVO getYearlyStats(Integer year) {
        // 计算年份的起止日期
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);

        // 查询该年的就餐记录
        List<DiningRecordDO> records = diningRecordMapper.selectListByDateRangeAndMealType(startDate, endDate, null);

        // 按月份分组统计
        Map<Integer, DiningMonthlyStatVO> monthlyStatsMap = new LinkedHashMap<>();

        // 初始化12个月
        for (int m = 1; m <= 12; m++) {
            DiningMonthlyStatVO vo = new DiningMonthlyStatVO();
            vo.setMonth(m);
            monthlyStatsMap.put(m, vo);
        }

        // 遍历记录进行统计
        for (DiningRecordDO record : records) {
            LocalDate diningDate = record.getDiningDate();
            if (diningDate == null) {
                continue;
            }
            int recordMonth = diningDate.getMonthValue();
            DiningMonthlyStatVO vo = monthlyStatsMap.get(recordMonth);

            // 根据餐别累加
            Integer mealType = record.getMealType();
            if (mealType != null) {
                switch (mealType) {
                    case 0:
                        vo.setBreakfastCount(vo.getBreakfastCount() + 1);
                        break;
                    case 1:
                        vo.setLunchCount(vo.getLunchCount() + 1);
                        break;
                    case 2:
                        vo.setDinnerCount(vo.getDinnerCount() + 1);
                        break;
                }
            }

            // 累加金额
            if (record.getAmount() != null) {
                vo.setTotalAmount(vo.getTotalAmount().add(record.getAmount()));
            }
        }

        // 计算每月总人次
        List<DiningMonthlyStatVO> monthlyStats = new ArrayList<>();
        DiningStatsSummaryVO summary = new DiningStatsSummaryVO();

        for (DiningMonthlyStatVO vo : monthlyStatsMap.values()) {
            vo.setTotalCount(vo.getBreakfastCount() + vo.getLunchCount() + vo.getDinnerCount());
            monthlyStats.add(vo);

            // 累加到年度汇总
            summary.setBreakfastCount(summary.getBreakfastCount() + vo.getBreakfastCount());
            summary.setLunchCount(summary.getLunchCount() + vo.getLunchCount());
            summary.setDinnerCount(summary.getDinnerCount() + vo.getDinnerCount());
            summary.setTotalAmount(summary.getTotalAmount().add(vo.getTotalAmount()));
        }
        summary.setTotalCount(summary.getBreakfastCount() + summary.getLunchCount() + summary.getDinnerCount());

        // 构建返回结果
        DiningYearlyStatsRespVO result = new DiningYearlyStatsRespVO();
        result.setYear(year);
        result.setMonthlyStats(monthlyStats);
        result.setSummary(summary);

        return result;
    }

    @Override
    public List<DiningExpenseStatVO> getExpenseStats(LocalDate startDate, LocalDate endDate, String deptName, String userName) {
        // 查询报餐登记记录
        List<DiningRegistrationDO> registrations = diningRegistrationMapper.selectListForExpenseStats(
                startDate, endDate, deptName, userName);

        // 按用户+单位分组统计
        Map<String, DiningExpenseStatVO> statsMap = new LinkedHashMap<>();

        for (DiningRegistrationDO reg : registrations) {
            String key = (reg.getUserName() != null ? reg.getUserName() : "") + "_" +
                    (reg.getDeptName() != null ? reg.getDeptName() : "");

            DiningExpenseStatVO vo = statsMap.computeIfAbsent(key, k -> {
                DiningExpenseStatVO newVo = new DiningExpenseStatVO();
                newVo.setUserName(reg.getUserName());
                newVo.setDeptName(reg.getDeptName());
                return newVo;
            });

            Integer mealType = reg.getMealType();
            Integer guestCount = reg.getGuestCount() != null ? reg.getGuestCount() : 1;
            Integer used = reg.getUsed();

            // 累加报餐次数
            if (mealType != null) {
                switch (mealType) {
                    case 0:
                        vo.setBreakfastRegCount(vo.getBreakfastRegCount() + guestCount);
                        if (used != null && used == 1) {
                            vo.setBreakfastUsedCount(vo.getBreakfastUsedCount() + guestCount);
                        }
                        break;
                    case 1:
                        vo.setLunchRegCount(vo.getLunchRegCount() + guestCount);
                        if (used != null && used == 1) {
                            vo.setLunchUsedCount(vo.getLunchUsedCount() + guestCount);
                        }
                        break;
                    case 2:
                        vo.setDinnerRegCount(vo.getDinnerRegCount() + guestCount);
                        if (used != null && used == 1) {
                            vo.setDinnerUsedCount(vo.getDinnerUsedCount() + guestCount);
                        }
                        break;
                }
            }

            // 计算金额（已就餐的）
            if (used != null && used == 1) {
                Integer personType = reg.getPersonType() != null ? reg.getPersonType() : 0;
                Integer diningClass = reg.getDiningClass() != null ? reg.getDiningClass() : 0;
                BigDecimal price = diningPriceService.getPrice(personType, mealType, diningClass);
                if (price != null) {
                    vo.setTotalAmount(vo.getTotalAmount().add(price.multiply(BigDecimal.valueOf(guestCount))));
                }
            }
        }

        return new ArrayList<>(statsMap.values());
    }

}
