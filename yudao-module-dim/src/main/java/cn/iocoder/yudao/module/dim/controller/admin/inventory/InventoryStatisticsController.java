package cn.iocoder.yudao.module.dim.controller.admin.inventory;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.dim.controller.admin.inventory.vo.InventoryStatisticsVO;
import cn.iocoder.yudao.module.dim.service.inventory.InventoryStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 库存统计 Controller
 */
@Tag(name = "管理后台 - 库存统计")
@RestController
@RequestMapping("/dim/inventory/statistics")
@Validated
public class InventoryStatisticsController {

    @Resource
    private InventoryStatisticsService inventoryStatisticsService;

    @GetMapping("/overview")
    @Operation(summary = "获取统计概览")
    @PreAuthorize("@ss.hasPermission('dim:inventory:statistics:query')")
    public CommonResult<InventoryStatisticsVO.OverviewVO> getOverview() {
        return success(inventoryStatisticsService.getOverview());
    }

    @GetMapping("/trend")
    @Operation(summary = "获取库存趋势")
    @Parameter(name = "days", description = "天数", example = "30")
    @PreAuthorize("@ss.hasPermission('dim:inventory:statistics:query')")
    public CommonResult<InventoryStatisticsVO.TrendVO> getTrend(
            @RequestParam(value = "days", defaultValue = "30") Integer days) {
        return success(inventoryStatisticsService.getTrend(days));
    }

    @GetMapping("/category-distribution")
    @Operation(summary = "获取分类分布")
    @Parameter(name = "type", description = "物品类型：1-物资 2-耗材")
    @PreAuthorize("@ss.hasPermission('dim:inventory:statistics:query')")
    public CommonResult<List<InventoryStatisticsVO.CategoryDistVO>> getCategoryDistribution(
            @RequestParam(value = "type", required = false) Integer type) {
        return success(inventoryStatisticsService.getCategoryDistribution(type));
    }

    @GetMapping("/monthly-operation")
    @Operation(summary = "获取月度出入库统计")
    @Parameter(name = "months", description = "月数", example = "6")
    @PreAuthorize("@ss.hasPermission('dim:inventory:statistics:query')")
    public CommonResult<InventoryStatisticsVO.MonthlyOperationVO> getMonthlyOperation(
            @RequestParam(value = "months", defaultValue = "6") Integer months) {
        return success(inventoryStatisticsService.getMonthlyOperation(months));
    }

}
