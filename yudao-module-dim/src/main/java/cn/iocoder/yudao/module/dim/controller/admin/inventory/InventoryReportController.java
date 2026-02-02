package cn.iocoder.yudao.module.dim.controller.admin.inventory;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.dim.controller.admin.inventory.vo.InventoryMonthlyReportVO;
import cn.iocoder.yudao.module.dim.service.inventory.InventoryReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 库存报表 Controller
 */
@Tag(name = "管理后台 - 库存报表")
@RestController
@RequestMapping("/dim/inventory/report")
@Validated
public class InventoryReportController {

    @Resource
    private InventoryReportService inventoryReportService;

    @GetMapping("/monthly/list")
    @Operation(summary = "获取月度汇总报表")
    @Parameters({
            @Parameter(name = "yearMonth", description = "年月（YYYY-MM）", required = true),
            @Parameter(name = "type", description = "物品类型：1-物资 2-耗材")
    })
    @PreAuthorize("@ss.hasPermission('dim:inventory:report:query')")
    public CommonResult<List<InventoryMonthlyReportVO>> getMonthlyReport(
            @RequestParam("yearMonth") String yearMonth,
            @RequestParam(value = "type", required = false) Integer type) {
        return success(inventoryReportService.getMonthlyReport(yearMonth, type));
    }

    @GetMapping("/monthly/export")
    @Operation(summary = "导出月度汇总报表 Excel")
    @Parameters({
            @Parameter(name = "yearMonth", description = "年月（YYYY-MM）", required = true),
            @Parameter(name = "type", description = "物品类型：1-物资 2-耗材")
    })
    @PreAuthorize("@ss.hasPermission('dim:inventory:report:export')")
    public void exportMonthlyReport(
            @RequestParam("yearMonth") String yearMonth,
            @RequestParam(value = "type", required = false) Integer type,
            HttpServletResponse response) throws IOException {
        List<InventoryMonthlyReportVO> list = inventoryReportService.getMonthlyReport(yearMonth, type);
        ExcelUtils.write(response, "月度库存汇总报表_" + yearMonth + ".xls", "月度汇总",
                InventoryMonthlyReportVO.class, list);
    }

    @GetMapping("/year-months")
    @Operation(summary = "获取可用年月列表")
    @PreAuthorize("@ss.hasPermission('dim:inventory:report:query')")
    public CommonResult<List<String>> getAvailableYearMonths() {
        return success(inventoryReportService.getAvailableYearMonths());
    }

    @PostMapping("/monthly/recalculate")
    @Operation(summary = "重新计算月度汇总数据")
    @Parameter(name = "yearMonth", description = "年月（YYYY-MM）", required = true)
    @PreAuthorize("@ss.hasPermission('dim:inventory:report:update')")
    public CommonResult<Boolean> recalculateMonthlyData(@RequestParam("yearMonth") String yearMonth) {
        inventoryReportService.recalculateMonthlyData(yearMonth);
        return success(true);
    }

}
