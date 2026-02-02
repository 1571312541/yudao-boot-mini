package cn.iocoder.yudao.module.dim.controller.admin.inventory;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.dim.controller.admin.inventory.vo.*;
import cn.iocoder.yudao.module.dim.dal.dataobject.inventory.InventoryBatchDO;
import cn.iocoder.yudao.module.dim.dal.dataobject.inventory.InventoryLogDO;
import cn.iocoder.yudao.module.dim.service.inventory.InventoryOperationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 库存操作 Controller
 * 入库/出库/审核
 */
@Tag(name = "管理后台 - 库存操作")
@RestController
@RequestMapping("/dim/inventory")
@Validated
public class InventoryOperationController {

    @Resource
    private InventoryOperationService inventoryOperationService;

    // ==================== 入库操作 ====================

    @PostMapping("/inbound/create")
    @Operation(summary = "创建入库单")
    @PreAuthorize("@ss.hasPermission('dim:inventory:inbound:create')")
    public CommonResult<Long> createInbound(@Valid @RequestBody InventoryInboundReqVO reqVO) {
        return success(inventoryOperationService.createInbound(reqVO));
    }

    @GetMapping("/inbound/page")
    @Operation(summary = "获得入库分页")
    @PreAuthorize("@ss.hasPermission('dim:inventory:inbound:query')")
    public CommonResult<PageResult<InventoryLogRespVO>> getInboundPage(@Valid InventoryLogPageReqVO pageReqVO) {
        PageResult<InventoryLogDO> pageResult = inventoryOperationService.getInboundPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, InventoryLogRespVO.class));
    }

    // ==================== 出库操作 ====================

    @PostMapping("/outbound/create")
    @Operation(summary = "创建出库单（FIFO）")
    @PreAuthorize("@ss.hasPermission('dim:inventory:outbound:create')")
    public CommonResult<Long> createOutbound(@Valid @RequestBody InventoryOutboundReqVO reqVO) {
        return success(inventoryOperationService.createOutbound(reqVO));
    }

    @PostMapping("/outbound/batch-create")
    @Operation(summary = "批量创建出库单（购物车模式）")
    @PreAuthorize("@ss.hasPermission('dim:inventory:outbound:create')")
    public CommonResult<List<Long>> createBatchOutbound(@Valid @RequestBody BatchOutboundReqVO reqVO) {
        return success(inventoryOperationService.createBatchOutbound(reqVO));
    }

    // ==================== 盘点操作 ====================

    @PostMapping("/check")
    @Operation(summary = "库存盘点（手动对账）")
    @PreAuthorize("@ss.hasPermission('dim:inventory:check')")
    public CommonResult<InventoryCheckRespVO> checkInventory(@Valid @RequestBody InventoryCheckReqVO reqVO) {
        return success(inventoryOperationService.checkInventory(reqVO));
    }

    @GetMapping("/outbound/page")
    @Operation(summary = "获得出库分页")
    @PreAuthorize("@ss.hasPermission('dim:inventory:outbound:query')")
    public CommonResult<PageResult<InventoryLogRespVO>> getOutboundPage(@Valid InventoryLogPageReqVO pageReqVO) {
        PageResult<InventoryLogDO> pageResult = inventoryOperationService.getOutboundPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, InventoryLogRespVO.class));
    }

    // ==================== 审核操作 ====================

    @PutMapping("/audit")
    @Operation(summary = "审核入库/出库")
    @PreAuthorize("@ss.hasPermission('dim:inventory:audit')")
    public CommonResult<Boolean> auditLog(@Valid @RequestBody InventoryAuditReqVO reqVO) {
        inventoryOperationService.auditLog(reqVO);
        return success(true);
    }

    @GetMapping("/audit/pending-list")
    @Operation(summary = "获得待审核列表")
    @PreAuthorize("@ss.hasPermission('dim:inventory:audit')")
    public CommonResult<List<InventoryLogRespVO>> getPendingAuditList() {
        List<InventoryLogDO> list = inventoryOperationService.getPendingAuditList();
        return success(BeanUtils.toBean(list, InventoryLogRespVO.class));
    }

    // ==================== 日志查询 ====================

    @GetMapping("/log/get")
    @Operation(summary = "获得库存日志")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('dim:inventory:log:query')")
    public CommonResult<InventoryLogRespVO> getLog(@RequestParam("id") Long id) {
        InventoryLogDO log = inventoryOperationService.getLog(id);
        return success(BeanUtils.toBean(log, InventoryLogRespVO.class));
    }

    @GetMapping("/log/page")
    @Operation(summary = "获得库存日志分页")
    @PreAuthorize("@ss.hasPermission('dim:inventory:log:query')")
    public CommonResult<PageResult<InventoryLogRespVO>> getLogPage(@Valid InventoryLogPageReqVO pageReqVO) {
        PageResult<InventoryLogDO> pageResult = inventoryOperationService.getLogPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, InventoryLogRespVO.class));
    }

    @GetMapping("/log/list-by-item")
    @Operation(summary = "根据物品ID获得库存日志列表")
    @Parameter(name = "itemId", description = "物品ID", required = true)
    @PreAuthorize("@ss.hasPermission('dim:inventory:log:query')")
    public CommonResult<List<InventoryLogRespVO>> getLogListByItemId(@RequestParam("itemId") Long itemId) {
        List<InventoryLogDO> list = inventoryOperationService.getLogListByItemId(itemId);
        return success(BeanUtils.toBean(list, InventoryLogRespVO.class));
    }

    @GetMapping("/log/export-excel")
    @Operation(summary = "导出库存日志 Excel")
    @PreAuthorize("@ss.hasPermission('dim:inventory:log:export')")
    public void exportLogExcel(@Valid InventoryLogPageReqVO pageReqVO,
                               HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<InventoryLogDO> list = inventoryOperationService.getLogPage(pageReqVO).getList();
        ExcelUtils.write(response, "库存日志.xls", "数据", InventoryLogRespVO.class,
                BeanUtils.toBean(list, InventoryLogRespVO.class));
    }

    // ==================== 批次查询 ====================

    @GetMapping("/batch/page")
    @Operation(summary = "获得批次分页")
    @PreAuthorize("@ss.hasPermission('dim:inventory:batch:query')")
    public CommonResult<PageResult<InventoryBatchRespVO>> getBatchPage(@Valid InventoryBatchPageReqVO pageReqVO) {
        PageResult<InventoryBatchDO> pageResult = inventoryOperationService.getBatchPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, InventoryBatchRespVO.class));
    }

    @GetMapping("/batch/list-by-item")
    @Operation(summary = "根据物品ID获得批次列表")
    @Parameter(name = "itemId", description = "物品ID", required = true)
    @PreAuthorize("@ss.hasPermission('dim:inventory:batch:query')")
    public CommonResult<List<InventoryBatchRespVO>> getBatchListByItemId(@RequestParam("itemId") Long itemId) {
        List<InventoryBatchDO> list = inventoryOperationService.getBatchListByItemId(itemId);
        return success(BeanUtils.toBean(list, InventoryBatchRespVO.class));
    }

}
