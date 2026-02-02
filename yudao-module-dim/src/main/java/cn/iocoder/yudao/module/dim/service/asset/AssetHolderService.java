package cn.iocoder.yudao.module.dim.service.asset;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.AssetHolderPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.AssetHolderSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.asset.AssetHolderDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 资产持有 Service 接口
 */
public interface AssetHolderService {

    /**
     * 创建资产持有记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createAssetHolder(@Valid AssetHolderSaveReqVO createReqVO);

    /**
     * 更新资产持有记录
     *
     * @param updateReqVO 更新信息
     */
    void updateAssetHolder(@Valid AssetHolderSaveReqVO updateReqVO);

    /**
     * 删除资产持有记录
     *
     * @param id 编号
     */
    void deleteAssetHolder(Long id);

    /**
     * 获得资产持有记录
     *
     * @param id 编号
     * @return 资产持有记录
     */
    AssetHolderDO getAssetHolder(Long id);

    /**
     * 获得资产持有记录分页
     *
     * @param pageReqVO 分页查询
     * @return 资产持有记录分页
     */
    PageResult<AssetHolderDO> getAssetHolderPage(AssetHolderPageReqVO pageReqVO);

    /**
     * 根据资产ID获得持有记录列表
     *
     * @param assetId 资产ID
     * @return 持有记录列表
     */
    List<AssetHolderDO> getAssetHolderListByAssetId(Long assetId);

}
