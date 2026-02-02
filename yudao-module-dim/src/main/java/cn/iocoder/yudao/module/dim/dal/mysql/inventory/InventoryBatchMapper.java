package cn.iocoder.yudao.module.dim.dal.mysql.inventory;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.dim.controller.admin.inventory.vo.InventoryBatchPageReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.inventory.InventoryBatchDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 库存批次 Mapper
 */
@Mapper
public interface InventoryBatchMapper extends BaseMapperX<InventoryBatchDO> {

    default PageResult<InventoryBatchDO> selectPage(InventoryBatchPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<InventoryBatchDO>()
                .eqIfPresent(InventoryBatchDO::getItemId, reqVO.getItemId())
                .likeIfPresent(InventoryBatchDO::getBatchNo, reqVO.getBatchNo())
                .eqIfPresent(InventoryBatchDO::getStatus, reqVO.getStatus())
                .orderByAsc(InventoryBatchDO::getCreateTime));
    }

    /**
     * 按入库时间顺序获取有库存的批次（FIFO 核心查询）
     */
    default List<InventoryBatchDO> selectByItemIdOrderByCreateTime(Long itemId) {
        return selectList(new LambdaQueryWrapperX<InventoryBatchDO>()
                .eq(InventoryBatchDO::getItemId, itemId)
                .eq(InventoryBatchDO::getStatus, 0)
                .gt(InventoryBatchDO::getQuantity, 0)
                .orderByAsc(InventoryBatchDO::getCreateTime));
    }

    default List<InventoryBatchDO> selectListByItemId(Long itemId) {
        return selectList(new LambdaQueryWrapperX<InventoryBatchDO>()
                .eq(InventoryBatchDO::getItemId, itemId)
                .orderByAsc(InventoryBatchDO::getCreateTime));
    }

    default InventoryBatchDO selectByBatchNo(String batchNo) {
        return selectOne(new LambdaQueryWrapperX<InventoryBatchDO>()
                .eq(InventoryBatchDO::getBatchNo, batchNo));
    }

}
