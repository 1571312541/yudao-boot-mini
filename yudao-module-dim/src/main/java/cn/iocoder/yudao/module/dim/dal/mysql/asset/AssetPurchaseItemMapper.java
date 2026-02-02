package cn.iocoder.yudao.module.dim.dal.mysql.asset;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.dim.dal.dataobject.asset.AssetPurchaseItemDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AssetPurchaseItemMapper extends BaseMapperX<AssetPurchaseItemDO> {

    default List<AssetPurchaseItemDO> selectListByPurchaseId(Long purchaseId) {
        return selectList(new LambdaQueryWrapperX<AssetPurchaseItemDO>()
                .eq(AssetPurchaseItemDO::getPurchaseId, purchaseId)
                .orderByAsc(AssetPurchaseItemDO::getId));
    }

    default void deleteByPurchaseId(Long purchaseId) {
        delete(new LambdaQueryWrapperX<AssetPurchaseItemDO>()
                .eq(AssetPurchaseItemDO::getPurchaseId, purchaseId));
    }

}
