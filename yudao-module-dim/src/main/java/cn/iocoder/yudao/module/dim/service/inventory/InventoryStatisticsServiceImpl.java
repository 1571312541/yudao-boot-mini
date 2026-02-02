package cn.iocoder.yudao.module.dim.service.inventory;

import cn.iocoder.yudao.module.dim.controller.admin.inventory.vo.InventoryStatisticsVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.inventory.InventoryCategoryDO;
import cn.iocoder.yudao.module.dim.dal.dataobject.inventory.InventoryItemDO;
import cn.iocoder.yudao.module.dim.dal.dataobject.inventory.InventoryLogDO;
import cn.iocoder.yudao.module.dim.dal.mysql.inventory.InventoryCategoryMapper;
import cn.iocoder.yudao.module.dim.dal.mysql.inventory.InventoryItemMapper;
import cn.iocoder.yudao.module.dim.dal.mysql.inventory.InventoryLogMapper;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 库存统计 Service 实现类
 */
@Service
public class InventoryStatisticsServiceImpl implements InventoryStatisticsService {

    @Resource
    private InventoryItemMapper inventoryItemMapper;

    @Resource
    private InventoryLogMapper inventoryLogMapper;

    @Resource
    private InventoryCategoryMapper inventoryCategoryMapper;

    @Override
    public InventoryStatisticsVO.OverviewVO getOverview() {
        InventoryStatisticsVO.OverviewVO overview = new InventoryStatisticsVO.OverviewVO();

        // 获取所有物品
        List<InventoryItemDO> items = inventoryItemMapper.selectList();

        // 基础统计
        overview.setTotalItems(items.size());
        overview.setMaterialCount((int) items.stream().filter(i -> i.getType() == 1).count());
        overview.setConsumableCount((int) items.stream().filter(i -> i.getType() == 2).count());
        overview.setTotalQuantity(items.stream()
                .map(i -> i.getQuantity() != null ? i.getQuantity() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        overview.setTotalAmount(items.stream()
                .map(i -> i.getTotalAmount() != null ? i.getTotalAmount() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        // 预警统计
        overview.setLowStockCount((int) items.stream()
                .filter(i -> i.getQuantity() != null && i.getMinQuantity() != null
                        && i.getQuantity().compareTo(i.getMinQuantity()) < 0)
                .count());
        overview.setHighStockCount((int) items.stream()
                .filter(i -> i.getQuantity() != null && i.getMaxQuantity() != null
                        && i.getQuantity().compareTo(i.getMaxQuantity()) > 0)
                .count());

        // 待审核数
        List<InventoryLogDO> pendingLogs = inventoryLogMapper.selectListPendingAudit();
        overview.setPendingAuditCount(pendingLogs.size());

        return overview;
    }

    @Override
    public InventoryStatisticsVO.TrendVO getTrend(Integer days) {
        InventoryStatisticsVO.TrendVO trend = new InventoryStatisticsVO.TrendVO();

        // 计算日期范围
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(days - 1);

        // 初始化日期列表
        List<String> dates = new ArrayList<>();
        List<BigDecimal> inQuantities = new ArrayList<>();
        List<BigDecimal> outQuantities = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");

        // 查询日志并按日期分组
        LocalDateTime startTime = startDate.atStartOfDay();
        LocalDateTime endTime = endDate.atTime(23, 59, 59);

        List<InventoryLogDO> logs = inventoryLogMapper.selectList(
                new LambdaQueryWrapperX<InventoryLogDO>()
                        .ge(InventoryLogDO::getCreateTime, startTime)
                        .le(InventoryLogDO::getCreateTime, endTime)
                        .eq(InventoryLogDO::getAuditStatus, 1)); // 只统计已审核通过的

        // 按日期分组统计
        Map<LocalDate, BigDecimal> inMap = new HashMap<>();
        Map<LocalDate, BigDecimal> outMap = new HashMap<>();

        for (InventoryLogDO log : logs) {
            LocalDate logDate = log.getCreateTime().toLocalDate();
            BigDecimal qty = log.getQuantity().abs();

            if (log.getQuantity().compareTo(BigDecimal.ZERO) > 0) {
                inMap.merge(logDate, qty, BigDecimal::add);
            } else {
                outMap.merge(logDate, qty, BigDecimal::add);
            }
        }

        // 填充数据
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            dates.add(date.format(formatter));
            inQuantities.add(inMap.getOrDefault(date, BigDecimal.ZERO));
            outQuantities.add(outMap.getOrDefault(date, BigDecimal.ZERO));
        }

        trend.setDates(dates);
        trend.setInQuantities(inQuantities);
        trend.setOutQuantities(outQuantities);

        return trend;
    }

    @Override
    public List<InventoryStatisticsVO.CategoryDistVO> getCategoryDistribution(Integer type) {
        // 获取物品列表
        List<InventoryItemDO> items = type != null ?
                inventoryItemMapper.selectListByType(type) :
                inventoryItemMapper.selectList();

        if (items.isEmpty()) {
            return new ArrayList<>();
        }

        // 获取分类信息
        List<Long> categoryIds = items.stream()
                .map(InventoryItemDO::getCategoryId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        Map<Long, String> categoryNameMap = categoryIds.isEmpty() ? new HashMap<>() :
                inventoryCategoryMapper.selectBatchIds(categoryIds).stream()
                        .collect(Collectors.toMap(InventoryCategoryDO::getId, InventoryCategoryDO::getName));

        // 按分类统计
        Map<Long, InventoryStatisticsVO.CategoryDistVO> distMap = new HashMap<>();

        for (InventoryItemDO item : items) {
            Long categoryId = item.getCategoryId();
            if (categoryId == null) {
                categoryId = 0L; // 未分类
            }

            InventoryStatisticsVO.CategoryDistVO dist = distMap.computeIfAbsent(categoryId, k -> {
                InventoryStatisticsVO.CategoryDistVO vo = new InventoryStatisticsVO.CategoryDistVO();
                vo.setName(categoryNameMap.getOrDefault(k, "未分类"));
                vo.setCount(0);
                vo.setAmount(BigDecimal.ZERO);
                return vo;
            });

            dist.setCount(dist.getCount() + 1);
            if (item.getTotalAmount() != null) {
                dist.setAmount(dist.getAmount().add(item.getTotalAmount()));
            }
        }

        return new ArrayList<>(distMap.values());
    }

    @Override
    public InventoryStatisticsVO.MonthlyOperationVO getMonthlyOperation(Integer months) {
        InventoryStatisticsVO.MonthlyOperationVO result = new InventoryStatisticsVO.MonthlyOperationVO();

        List<String> monthList = new ArrayList<>();
        List<BigDecimal> inAmounts = new ArrayList<>();
        List<BigDecimal> outAmounts = new ArrayList<>();

        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        LocalDate today = LocalDate.now();

        for (int i = months - 1; i >= 0; i--) {
            LocalDate monthDate = today.minusMonths(i);
            String yearMonth = monthDate.format(monthFormatter);
            monthList.add(yearMonth.substring(5)); // 只取 MM

            // 计算该月的出入库金额
            LocalDateTime monthStart = monthDate.withDayOfMonth(1).atStartOfDay();
            LocalDateTime monthEnd = monthDate.withDayOfMonth(monthDate.lengthOfMonth()).atTime(23, 59, 59);

            List<InventoryLogDO> logs = inventoryLogMapper.selectList(
                    new LambdaQueryWrapperX<InventoryLogDO>()
                            .ge(InventoryLogDO::getCreateTime, monthStart)
                            .le(InventoryLogDO::getCreateTime, monthEnd)
                            .eq(InventoryLogDO::getAuditStatus, 1));

            BigDecimal inAmount = BigDecimal.ZERO;
            BigDecimal outAmount = BigDecimal.ZERO;

            for (InventoryLogDO log : logs) {
                BigDecimal amount = log.getTotalAmount() != null ? log.getTotalAmount().abs() : BigDecimal.ZERO;
                if (log.getQuantity().compareTo(BigDecimal.ZERO) > 0) {
                    inAmount = inAmount.add(amount);
                } else {
                    outAmount = outAmount.add(amount);
                }
            }

            inAmounts.add(inAmount);
            outAmounts.add(outAmount);
        }

        result.setMonths(monthList);
        result.setInAmounts(inAmounts);
        result.setOutAmounts(outAmounts);

        return result;
    }

}
