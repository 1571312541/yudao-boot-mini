package cn.iocoder.yudao.module.dim.service.inventory;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.dim.controller.admin.inventory.vo.*;
import cn.iocoder.yudao.module.dim.dal.dataobject.inventory.InventoryBatchDO;
import cn.iocoder.yudao.module.dim.dal.dataobject.inventory.InventoryItemDO;
import cn.iocoder.yudao.module.dim.dal.dataobject.inventory.InventoryLogDO;
import cn.iocoder.yudao.module.dim.dal.mysql.inventory.InventoryBatchMapper;
import cn.iocoder.yudao.module.dim.dal.mysql.inventory.InventoryItemMapper;
import cn.iocoder.yudao.module.dim.dal.mysql.inventory.InventoryLogMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.dim.enums.ErrorCodeConstants.*;

/**
 * 库存操作 Service 实现类
 * 包含 FIFO 出库算法
 */
@Service
@Validated
public class InventoryOperationServiceImpl implements InventoryOperationService {

    /** 审核状态：待审核 */
    private static final Integer AUDIT_STATUS_PENDING = 0;
    /** 审核状态：已通过 */
    private static final Integer AUDIT_STATUS_APPROVED = 1;
    /** 审核状态：已驳回 */
    private static final Integer AUDIT_STATUS_REJECTED = 2;

    /** 批次状态：正常 */
    private static final Integer BATCH_STATUS_NORMAL = 0;
    /** 批次状态：已清空 */
    private static final Integer BATCH_STATUS_EMPTY = 1;

    /** 操作单号序列 */
    private static final AtomicLong operationSeq = new AtomicLong(0);

    @Resource
    private InventoryItemMapper inventoryItemMapper;

    @Resource
    private InventoryBatchMapper inventoryBatchMapper;

    @Resource
    private InventoryLogMapper inventoryLogMapper;

    // ==================== 入库操作 ====================

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createInbound(InventoryInboundReqVO reqVO) {
        // 1. 校验物品存在
        InventoryItemDO item = inventoryItemMapper.selectById(reqVO.getItemId());
        if (item == null) {
            throw exception(INVENTORY_ITEM_NOT_EXISTS);
        }

        // 2. 生成操作单号
        String operationNo = generateOperationNo("RK");

        // 3. 计算折扣后单价
        BigDecimal unitPrice;
        BigDecimal originalPrice = reqVO.getOriginalPrice();
        BigDecimal discountRate = reqVO.getDiscountRate();

        if (reqVO.getUnitPrice() != null) {
            // 直接使用指定的单价
            unitPrice = reqVO.getUnitPrice();
        } else if (originalPrice != null && discountRate != null) {
            // 根据原价和折扣率计算单价：单价 = 原价 × 折扣率 / 100
            unitPrice = originalPrice.multiply(discountRate).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
        } else {
            // 使用物品默认单价
            unitPrice = item.getUnitPrice();
        }

        // 4. 创建批次记录
        InventoryBatchDO batch = new InventoryBatchDO();
        batch.setItemId(reqVO.getItemId());
        batch.setBatchNo(generateBatchNo());
        batch.setQuantity(reqVO.getQuantity());
        batch.setUnitPrice(unitPrice);
        batch.setProductionDate(reqVO.getProductionDate());
        batch.setExpiryDate(reqVO.getExpiryDate());
        batch.setSupplier(reqVO.getSupplier());
        batch.setStatus(BATCH_STATUS_NORMAL);
        inventoryBatchMapper.insert(batch);

        // 5. 创建入库日志
        InventoryLogDO log = new InventoryLogDO();
        log.setItemId(reqVO.getItemId());
        log.setBatchId(batch.getId());
        log.setOperationType(reqVO.getOperationType());
        log.setOperationNo(operationNo);
        log.setQuantity(reqVO.getQuantity());
        log.setUnitPrice(unitPrice);
        log.setOriginalPrice(originalPrice);
        log.setDiscountRate(discountRate);
        log.setTotalAmount(reqVO.getQuantity().multiply(unitPrice != null ? unitPrice : BigDecimal.ZERO));
        log.setBeforeQuantity(item.getQuantity());
        log.setAfterQuantity(item.getQuantity().add(reqVO.getQuantity()));
        log.setAuditStatus(AUDIT_STATUS_PENDING);
        log.setApplicantId(SecurityFrameworkUtils.getLoginUserId());
        log.setRemark(reqVO.getRemark());
        inventoryLogMapper.insert(log);

        return log.getId();
    }

    @Override
    public PageResult<InventoryLogDO> getInboundPage(InventoryLogPageReqVO pageReqVO) {
        return inventoryLogMapper.selectInboundPage(pageReqVO);
    }

    // ==================== 出库操作 ====================

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createOutbound(InventoryOutboundReqVO reqVO) {
        // 1. 校验物品存在
        InventoryItemDO item = inventoryItemMapper.selectById(reqVO.getItemId());
        if (item == null) {
            throw exception(INVENTORY_ITEM_NOT_EXISTS);
        }

        // 2. 校验库存充足
        BigDecimal availableQuantity = item.getQuantity().subtract(
                item.getLockedQuantity() != null ? item.getLockedQuantity() : BigDecimal.ZERO);
        if (availableQuantity.compareTo(reqVO.getQuantity()) < 0) {
            throw exception(INVENTORY_STOCK_INSUFFICIENT);
        }

        // 3. 生成操作单号
        String operationNo = generateOperationNo("CK");

        // 4. 创建出库日志（此时不执行 FIFO 扣减，等审核通过后执行）
        InventoryLogDO log = new InventoryLogDO();
        log.setItemId(reqVO.getItemId());
        log.setOperationType(reqVO.getOperationType());
        log.setOperationNo(operationNo);
        log.setQuantity(reqVO.getQuantity().negate()); // 出库用负数
        log.setBeforeQuantity(item.getQuantity());
        log.setAfterQuantity(item.getQuantity().subtract(reqVO.getQuantity()));
        log.setAuditStatus(AUDIT_STATUS_PENDING);
        log.setApplicantId(SecurityFrameworkUtils.getLoginUserId());
        log.setApplicantDeptId(reqVO.getApplicantDeptId());
        log.setPurpose(reqVO.getPurpose());
        log.setUsageLocation(reqVO.getUsageLocation());
        log.setRemark(reqVO.getRemark());
        inventoryLogMapper.insert(log);

        return log.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Long> createBatchOutbound(BatchOutboundReqVO reqVO) {
        List<Long> logIds = new java.util.ArrayList<>();
        String batchOperationNo = generateOperationNo("PL"); // PL = 批量

        for (BatchOutboundReqVO.OutboundItem item : reqVO.getItems()) {
            // 构建单个出库请求
            InventoryOutboundReqVO outboundReq = new InventoryOutboundReqVO();
            outboundReq.setItemId(item.getItemId());
            outboundReq.setOperationType(reqVO.getOperationType());
            outboundReq.setQuantity(item.getQuantity());
            outboundReq.setApplicantDeptId(reqVO.getApplicantDeptId());
            outboundReq.setPurpose(reqVO.getPurpose());
            outboundReq.setUsageLocation(reqVO.getUsageLocation());
            outboundReq.setRemark("[批量出库:" + batchOperationNo + "] " + (reqVO.getRemark() != null ? reqVO.getRemark() : ""));

            // 调用单个出库方法
            Long logId = createOutbound(outboundReq);
            logIds.add(logId);
        }

        return logIds;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public InventoryCheckRespVO checkInventory(InventoryCheckReqVO reqVO) {
        InventoryCheckRespVO resp = new InventoryCheckRespVO();
        List<InventoryCheckRespVO.CheckResult> results = new java.util.ArrayList<>();
        int profitCount = 0;
        int lossCount = 0;
        int matchCount = 0;

        String checkNo = generateOperationNo("PD"); // PD = 盘点

        for (InventoryCheckReqVO.CheckItem checkItem : reqVO.getItems()) {
            // 1. 获取物品信息
            InventoryItemDO item = inventoryItemMapper.selectById(checkItem.getItemId());
            if (item == null) {
                continue;
            }

            // 2. 计算差异
            BigDecimal systemQty = item.getQuantity() != null ? item.getQuantity() : BigDecimal.ZERO;
            BigDecimal actualQty = checkItem.getActualQuantity();
            BigDecimal difference = actualQty.subtract(systemQty);

            // 3. 构建结果
            InventoryCheckRespVO.CheckResult result = new InventoryCheckRespVO.CheckResult();
            result.setItemId(item.getId());
            result.setItemCode(item.getCode());
            result.setItemName(item.getName());
            result.setSystemQuantity(systemQty);
            result.setActualQuantity(actualQty);
            result.setDifferenceQuantity(difference);

            int cmp = difference.compareTo(BigDecimal.ZERO);
            if (cmp == 0) {
                // 相符
                result.setCheckStatus(0);
                matchCount++;
            } else if (cmp > 0) {
                // 盘盈：实际 > 系统，需要入库
                result.setCheckStatus(1);
                profitCount++;

                // 创建盘盈入库记录
                InventoryInboundReqVO inboundReq = new InventoryInboundReqVO();
                inboundReq.setItemId(item.getId());
                inboundReq.setOperationType(2); // 2-盘盈入库
                inboundReq.setQuantity(difference);
                inboundReq.setUnitPrice(item.getUnitPrice());
                inboundReq.setRemark("[盘点:" + checkNo + "] " + (checkItem.getRemark() != null ? checkItem.getRemark() : "盘盈"));

                Long logId = createInbound(inboundReq);
                result.setLogId(logId);
            } else {
                // 盘亏：实际 < 系统，需要出库
                result.setCheckStatus(2);
                lossCount++;

                // 创建盘亏出库记录
                InventoryOutboundReqVO outboundReq = new InventoryOutboundReqVO();
                outboundReq.setItemId(item.getId());
                outboundReq.setOperationType(4); // 4-盘亏出库
                outboundReq.setQuantity(difference.abs());
                outboundReq.setRemark("[盘点:" + checkNo + "] " + (checkItem.getRemark() != null ? checkItem.getRemark() : "盘亏"));

                Long logId = createOutbound(outboundReq);
                result.setLogId(logId);
            }

            results.add(result);
        }

        resp.setResults(results);
        resp.setProfitCount(profitCount);
        resp.setLossCount(lossCount);
        resp.setMatchCount(matchCount);

        return resp;
    }

    @Override
    public PageResult<InventoryLogDO> getOutboundPage(InventoryLogPageReqVO pageReqVO) {
        return inventoryLogMapper.selectOutboundPage(pageReqVO);
    }

    // ==================== 审核操作 ====================

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditLog(InventoryAuditReqVO reqVO) {
        // 1. 校验日志存在
        InventoryLogDO log = inventoryLogMapper.selectById(reqVO.getLogId());
        if (log == null) {
            throw exception(INVENTORY_LOG_NOT_EXISTS);
        }

        // 2. 校验是否待审核状态
        if (!AUDIT_STATUS_PENDING.equals(log.getAuditStatus())) {
            throw exception(INVENTORY_LOG_ALREADY_AUDITED);
        }

        // 3. 更新审核状态
        InventoryLogDO updateLog = new InventoryLogDO();
        updateLog.setId(log.getId());
        updateLog.setAuditStatus(reqVO.getApproved() ? AUDIT_STATUS_APPROVED : AUDIT_STATUS_REJECTED);
        updateLog.setAuditUserId(SecurityFrameworkUtils.getLoginUserId());
        updateLog.setAuditTime(LocalDateTime.now());
        updateLog.setAuditRemark(reqVO.getRemark());
        inventoryLogMapper.updateById(updateLog);

        // 4. 如果审核通过，执行库存变更
        if (reqVO.getApproved()) {
            if (log.getQuantity().compareTo(BigDecimal.ZERO) > 0) {
                // 入库：直接增加库存
                processInboundApproval(log);
            } else {
                // 出库：执行 FIFO 扣减
                processOutboundApprovalFIFO(log);
            }
        }
    }

    /**
     * 处理入库审核通过
     */
    private void processInboundApproval(InventoryLogDO log) {
        // 更新物品库存
        InventoryItemDO item = inventoryItemMapper.selectById(log.getItemId());
        InventoryItemDO updateItem = new InventoryItemDO();
        updateItem.setId(item.getId());
        updateItem.setQuantity(item.getQuantity().add(log.getQuantity()));
        if (item.getUnitPrice() != null) {
            updateItem.setTotalAmount(updateItem.getQuantity().multiply(item.getUnitPrice()));
        }
        inventoryItemMapper.updateById(updateItem);
    }

    /**
     * FIFO 出库算法
     * 按批次入库时间顺序扣减库存
     */
    private void processOutboundApprovalFIFO(InventoryLogDO log) {
        Long itemId = log.getItemId();
        BigDecimal outQuantity = log.getQuantity().abs(); // 出库数量（转为正数）

        // 1. 获取该物品所有有库存的批次，按入库时间排序（FIFO）
        List<InventoryBatchDO> batches = inventoryBatchMapper.selectByItemIdOrderByCreateTime(itemId);

        BigDecimal remaining = outQuantity;
        BigDecimal totalCost = BigDecimal.ZERO;

        // 2. 按 FIFO 顺序扣减
        for (InventoryBatchDO batch : batches) {
            if (remaining.compareTo(BigDecimal.ZERO) <= 0) {
                break;
            }

            // 计算本批次扣减数量
            BigDecimal deductQty = batch.getQuantity().min(remaining);

            // 计算本批次成本
            if (batch.getUnitPrice() != null) {
                totalCost = totalCost.add(deductQty.multiply(batch.getUnitPrice()));
            }

            // 更新批次库存
            InventoryBatchDO updateBatch = new InventoryBatchDO();
            updateBatch.setId(batch.getId());
            updateBatch.setQuantity(batch.getQuantity().subtract(deductQty));
            if (updateBatch.getQuantity().compareTo(BigDecimal.ZERO) == 0) {
                updateBatch.setStatus(BATCH_STATUS_EMPTY);
            }
            inventoryBatchMapper.updateById(updateBatch);

            remaining = remaining.subtract(deductQty);
        }

        // 3. 检查库存是否足够（理论上在创建出库单时已校验，这里再次确认）
        if (remaining.compareTo(BigDecimal.ZERO) > 0) {
            throw exception(INVENTORY_STOCK_INSUFFICIENT);
        }

        // 4. 更新物品总库存
        InventoryItemDO item = inventoryItemMapper.selectById(itemId);
        InventoryItemDO updateItem = new InventoryItemDO();
        updateItem.setId(item.getId());
        updateItem.setQuantity(item.getQuantity().subtract(outQuantity));
        updateItem.setTotalAmount(item.getTotalAmount() != null
                ? item.getTotalAmount().subtract(totalCost)
                : BigDecimal.ZERO);
        inventoryItemMapper.updateById(updateItem);

        // 5. 更新日志的成本信息
        InventoryLogDO updateLog = new InventoryLogDO();
        updateLog.setId(log.getId());
        updateLog.setTotalAmount(totalCost);
        inventoryLogMapper.updateById(updateLog);
    }

    @Override
    public List<InventoryLogDO> getPendingAuditList() {
        return inventoryLogMapper.selectListPendingAudit();
    }

    // ==================== 日志查询 ====================

    @Override
    public InventoryLogDO getLog(Long id) {
        return inventoryLogMapper.selectById(id);
    }

    @Override
    public PageResult<InventoryLogDO> getLogPage(InventoryLogPageReqVO pageReqVO) {
        return inventoryLogMapper.selectPage(pageReqVO);
    }

    @Override
    public List<InventoryLogDO> getLogListByItemId(Long itemId) {
        return inventoryLogMapper.selectListByItemId(itemId);
    }

    // ==================== 批次查询 ====================

    @Override
    public PageResult<InventoryBatchDO> getBatchPage(InventoryBatchPageReqVO pageReqVO) {
        return inventoryBatchMapper.selectPage(pageReqVO);
    }

    @Override
    public List<InventoryBatchDO> getBatchListByItemId(Long itemId) {
        return inventoryBatchMapper.selectListByItemId(itemId);
    }

    // ==================== 辅助方法 ====================

    /**
     * 生成操作单号
     */
    private String generateOperationNo(String prefix) {
        String datePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long seq = operationSeq.incrementAndGet() % 10000;
        return prefix + datePart + String.format("%04d", seq);
    }

    /**
     * 生成批次号
     */
    private String generateBatchNo() {
        String datePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        long seq = operationSeq.incrementAndGet() % 1000;
        return "PH" + datePart + String.format("%03d", seq);
    }

}
