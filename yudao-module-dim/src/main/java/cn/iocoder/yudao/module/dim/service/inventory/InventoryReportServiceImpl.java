package cn.iocoder.yudao.module.dim.service.inventory;

import cn.iocoder.yudao.module.dim.controller.admin.inventory.vo.InventoryMonthlyReportVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.inventory.InventoryCategoryDO;
import cn.iocoder.yudao.module.dim.dal.dataobject.inventory.InventoryItemDO;
import cn.iocoder.yudao.module.dim.dal.dataobject.inventory.InventoryLogDO;
import cn.iocoder.yudao.module.dim.dal.dataobject.inventory.InventoryMonthlyDO;
import cn.iocoder.yudao.module.dim.dal.mysql.inventory.InventoryCategoryMapper;
import cn.iocoder.yudao.module.dim.dal.mysql.inventory.InventoryItemMapper;
import cn.iocoder.yudao.module.dim.dal.mysql.inventory.InventoryLogMapper;
import cn.iocoder.yudao.module.dim.dal.mysql.inventory.InventoryMonthlyMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 库存报表 Service 实现类
 */
@Service
public class InventoryReportServiceImpl implements InventoryReportService {

    @Resource
    private InventoryMonthlyMapper inventoryMonthlyMapper;

    @Resource
    private InventoryItemMapper inventoryItemMapper;

    @Resource
    private InventoryCategoryMapper inventoryCategoryMapper;

    @Resource
    private InventoryLogMapper inventoryLogMapper;

    @Override
    public List<InventoryMonthlyReportVO> getMonthlyReport(String yearMonth, Integer type) {
        // 获取月度汇总数据
        List<InventoryMonthlyDO> monthlyList = inventoryMonthlyMapper.selectListByYearMonth(yearMonth);

        if (monthlyList.isEmpty()) {
            return new ArrayList<>();
        }

        // 获取物品信息
        List<Long> itemIds = monthlyList.stream()
                .map(InventoryMonthlyDO::getItemId)
                .collect(Collectors.toList());
        List<InventoryItemDO> items = inventoryItemMapper.selectBatchIds(itemIds);
        Map<Long, InventoryItemDO> itemMap = items.stream()
                .collect(Collectors.toMap(InventoryItemDO::getId, item -> item));

        // 获取分类信息
        List<Long> categoryIds = items.stream()
                .map(InventoryItemDO::getCategoryId)
                .filter(id -> id != null)
                .distinct()
                .collect(Collectors.toList());
        Map<Long, String> categoryNameMap = categoryIds.isEmpty() ? Collections.emptyMap() :
                inventoryCategoryMapper.selectBatchIds(categoryIds).stream()
                        .collect(Collectors.toMap(InventoryCategoryDO::getId, InventoryCategoryDO::getName));

        // 组装报表数据
        List<InventoryMonthlyReportVO> result = new ArrayList<>();
        for (InventoryMonthlyDO monthly : monthlyList) {
            InventoryItemDO item = itemMap.get(monthly.getItemId());
            if (item == null) {
                continue;
            }

            // 类型过滤
            if (type != null && !type.equals(item.getType())) {
                continue;
            }

            InventoryMonthlyReportVO vo = new InventoryMonthlyReportVO();
            vo.setItemId(monthly.getItemId());
            vo.setItemCode(item.getCode());
            vo.setItemName(item.getName());
            vo.setTypeName(item.getType() == 1 ? "物资" : "耗材");
            vo.setCategoryName(item.getCategoryId() != null ? categoryNameMap.get(item.getCategoryId()) : null);
            vo.setUnit(item.getUnit());
            vo.setYearMonth(yearMonth);
            vo.setOpeningQty(monthly.getOpeningQty());
            vo.setOpeningAmount(monthly.getOpeningAmount());
            vo.setInQty(monthly.getInQty());
            vo.setInAmount(monthly.getInAmount());
            vo.setOutQty(monthly.getOutQty());
            vo.setOutAmount(monthly.getOutAmount());
            vo.setClosingQty(monthly.getClosingQty());
            vo.setClosingAmount(monthly.getClosingAmount());
            result.add(vo);
        }

        return result;
    }

    @Override
    public List<String> getAvailableYearMonths() {
        return inventoryMonthlyMapper.selectDistinctYearMonths();
    }

    @Override
    public void recalculateMonthlyData(String yearMonth) {
        // 解析年月
        YearMonth ym = YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("yyyy-MM"));
        LocalDateTime monthStart = ym.atDay(1).atStartOfDay();
        LocalDateTime monthEnd = ym.atEndOfMonth().atTime(23, 59, 59);

        // 获取所有物品
        List<InventoryItemDO> items = inventoryItemMapper.selectList();

        for (InventoryItemDO item : items) {
            // 查询该月的出入库记录（已审核通过的）
            List<InventoryLogDO> logs = inventoryLogMapper.selectListByItemIdAndTimeRange(
                    item.getId(), monthStart, monthEnd, 1);

            // 计算入库和出库
            BigDecimal inQty = BigDecimal.ZERO;
            BigDecimal inAmount = BigDecimal.ZERO;
            BigDecimal outQty = BigDecimal.ZERO;
            BigDecimal outAmount = BigDecimal.ZERO;

            for (InventoryLogDO log : logs) {
                BigDecimal qty = log.getQuantity();
                BigDecimal amount = log.getTotalAmount() != null ? log.getTotalAmount() : BigDecimal.ZERO;

                if (qty.compareTo(BigDecimal.ZERO) > 0) {
                    // 入库
                    inQty = inQty.add(qty);
                    inAmount = inAmount.add(amount);
                } else {
                    // 出库（数量为负）
                    outQty = outQty.add(qty.abs());
                    outAmount = outAmount.add(amount.abs());
                }
            }

            // 获取期初（上月期末）
            YearMonth prevMonth = ym.minusMonths(1);
            String prevYearMonth = prevMonth.format(DateTimeFormatter.ofPattern("yyyy-MM"));
            InventoryMonthlyDO prevMonthly = inventoryMonthlyMapper.selectByItemIdAndYearMonth(
                    item.getId(), prevYearMonth);

            BigDecimal openingQty = prevMonthly != null ? prevMonthly.getClosingQty() : BigDecimal.ZERO;
            BigDecimal openingAmount = prevMonthly != null ? prevMonthly.getClosingAmount() : BigDecimal.ZERO;

            // 计算期末
            BigDecimal closingQty = openingQty.add(inQty).subtract(outQty);
            BigDecimal closingAmount = openingAmount.add(inAmount).subtract(outAmount);

            // 保存或更新
            InventoryMonthlyDO monthly = inventoryMonthlyMapper.selectByItemIdAndYearMonth(
                    item.getId(), yearMonth);

            if (monthly == null) {
                monthly = new InventoryMonthlyDO();
                monthly.setItemId(item.getId());
                monthly.setYearMonth(yearMonth);
            }

            monthly.setOpeningQty(openingQty);
            monthly.setOpeningAmount(openingAmount);
            monthly.setInQty(inQty);
            monthly.setInAmount(inAmount);
            monthly.setOutQty(outQty);
            monthly.setOutAmount(outAmount);
            monthly.setClosingQty(closingQty);
            monthly.setClosingAmount(closingAmount);

            if (monthly.getId() == null) {
                inventoryMonthlyMapper.insert(monthly);
            } else {
                inventoryMonthlyMapper.updateById(monthly);
            }
        }
    }

}
