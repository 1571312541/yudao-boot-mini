package cn.iocoder.yudao.module.dim.dal.mysql.inventory;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.dim.controller.admin.inventory.vo.InventoryLogPageReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.inventory.InventoryLogDO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 库存出入库日志 Mapper
 */
@Mapper
public interface InventoryLogMapper extends BaseMapperX<InventoryLogDO> {

    default PageResult<InventoryLogDO> selectPage(InventoryLogPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<InventoryLogDO>()
                .eqIfPresent(InventoryLogDO::getItemId, reqVO.getItemId())
                .eqIfPresent(InventoryLogDO::getOperationType, reqVO.getOperationType())
                .likeIfPresent(InventoryLogDO::getOperationNo, reqVO.getOperationNo())
                .eqIfPresent(InventoryLogDO::getAuditStatus, reqVO.getAuditStatus())
                .eqIfPresent(InventoryLogDO::getApplicantId, reqVO.getApplicantId())
                .betweenIfPresent(InventoryLogDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(InventoryLogDO::getId));
    }

    default List<InventoryLogDO> selectListByItemId(Long itemId) {
        return selectList(new LambdaQueryWrapperX<InventoryLogDO>()
                .eq(InventoryLogDO::getItemId, itemId)
                .orderByDesc(InventoryLogDO::getCreateTime));
    }

    default List<InventoryLogDO> selectListPendingAudit() {
        return selectList(new LambdaQueryWrapperX<InventoryLogDO>()
                .eq(InventoryLogDO::getAuditStatus, 0)
                .orderByAsc(InventoryLogDO::getCreateTime));
    }

    /**
     * 查询入库日志（操作类型 1、2、5）
     */
    default PageResult<InventoryLogDO> selectInboundPage(InventoryLogPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<InventoryLogDO>()
                .eqIfPresent(InventoryLogDO::getItemId, reqVO.getItemId())
                .likeIfPresent(InventoryLogDO::getOperationNo, reqVO.getOperationNo())
                .eqIfPresent(InventoryLogDO::getAuditStatus, reqVO.getAuditStatus())
                .betweenIfPresent(InventoryLogDO::getCreateTime, reqVO.getCreateTime())
                .in(InventoryLogDO::getOperationType, 1, 2, 5)
                .orderByDesc(InventoryLogDO::getId));
    }

    /**
     * 查询出库日志（操作类型 3、4、6）
     */
    default PageResult<InventoryLogDO> selectOutboundPage(InventoryLogPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<InventoryLogDO>()
                .eqIfPresent(InventoryLogDO::getItemId, reqVO.getItemId())
                .likeIfPresent(InventoryLogDO::getOperationNo, reqVO.getOperationNo())
                .eqIfPresent(InventoryLogDO::getAuditStatus, reqVO.getAuditStatus())
                .eqIfPresent(InventoryLogDO::getApplicantId, reqVO.getApplicantId())
                .betweenIfPresent(InventoryLogDO::getCreateTime, reqVO.getCreateTime())
                .in(InventoryLogDO::getOperationType, 3, 4, 6)
                .orderByDesc(InventoryLogDO::getId));
    }

    /**
     * 根据物品ID和时间范围查询日志
     */
    default List<InventoryLogDO> selectListByItemIdAndTimeRange(Long itemId, LocalDateTime startTime,
                                                                  LocalDateTime endTime, Integer auditStatus) {
        return selectList(new LambdaQueryWrapperX<InventoryLogDO>()
                .eq(InventoryLogDO::getItemId, itemId)
                .eqIfPresent(InventoryLogDO::getAuditStatus, auditStatus)
                .ge(InventoryLogDO::getCreateTime, startTime)
                .le(InventoryLogDO::getCreateTime, endTime)
                .orderByAsc(InventoryLogDO::getCreateTime));
    }

}
