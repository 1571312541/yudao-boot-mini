package cn.iocoder.yudao.module.dim.service.asset;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.AssetPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.AssetSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.asset.AssetDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 资产 Service 接口
 */
public interface AssetService {

    /**
     * 创建资产
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createAsset(@Valid AssetSaveReqVO createReqVO);

    /**
     * 更新资产
     *
     * @param updateReqVO 更新信息
     */
    void updateAsset(@Valid AssetSaveReqVO updateReqVO);

    /**
     * 删除资产
     *
     * @param id 编号
     */
    void deleteAsset(Long id);

    /**
     * 获得资产
     *
     * @param id 编号
     * @return 资产
     */
    AssetDO getAsset(Long id);

    /**
     * 获得资产分页
     *
     * @param pageReqVO 分页查询
     * @return 资产分页
     */
    PageResult<AssetDO> getAssetPage(AssetPageReqVO pageReqVO);

    /**
     * 获得资产列表
     *
     * @param ids 编号列表
     * @return 资产列表
     */
    List<AssetDO> getAssetList(List<Long> ids);

    /**
     * 更新资产库存
     *
     * @param id       资产编号
     * @param quantity 变更数量（正数增加，负数减少）
     */
    void updateAssetInventory(Long id, Integer quantity);

}
