package cn.iocoder.yudao.module.dim.service.inventory;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.dim.controller.admin.inventory.vo.*;
import cn.iocoder.yudao.module.dim.dal.dataobject.inventory.InventoryBatchDO;
import cn.iocoder.yudao.module.dim.dal.dataobject.inventory.InventoryLogDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 库存操作 Service 接口
 * 提供入库、出库、审核等核心业务操作
 */
public interface InventoryOperationService {

    // ==================== 入库操作 ====================

    /**
     * 创建入库单
     */
    Long createInbound(@Valid InventoryInboundReqVO reqVO);

    /**
     * 获得入库分页
     */
    PageResult<InventoryLogDO> getInboundPage(InventoryLogPageReqVO pageReqVO);

    // ==================== 出库操作 ====================

    /**
     * 创建出库单（使用 FIFO 算法）
     */
    Long createOutbound(@Valid InventoryOutboundReqVO reqVO);

    /**
     * 批量创建出库单（购物车模式）
     */
    List<Long> createBatchOutbound(@Valid BatchOutboundReqVO reqVO);

    /**
     * 库存盘点（手动对账）
     */
    InventoryCheckRespVO checkInventory(@Valid InventoryCheckReqVO reqVO);

    /**
     * 获得出库分页
     */
    PageResult<InventoryLogDO> getOutboundPage(InventoryLogPageReqVO pageReqVO);

    // ==================== 审核操作 ====================

    /**
     * 审核入库/出库
     */
    void auditLog(@Valid InventoryAuditReqVO reqVO);

    /**
     * 获得待审核列表
     */
    List<InventoryLogDO> getPendingAuditList();

    // ==================== 日志查询 ====================

    /**
     * 获得日志
     */
    InventoryLogDO getLog(Long id);

    /**
     * 获得日志分页
     */
    PageResult<InventoryLogDO> getLogPage(InventoryLogPageReqVO pageReqVO);

    /**
     * 根据物品ID获得日志列表
     */
    List<InventoryLogDO> getLogListByItemId(Long itemId);

    // ==================== 批次查询 ====================

    /**
     * 获得批次分页
     */
    PageResult<InventoryBatchDO> getBatchPage(InventoryBatchPageReqVO pageReqVO);

    /**
     * 根据物品ID获得批次列表
     */
    List<InventoryBatchDO> getBatchListByItemId(Long itemId);

}
