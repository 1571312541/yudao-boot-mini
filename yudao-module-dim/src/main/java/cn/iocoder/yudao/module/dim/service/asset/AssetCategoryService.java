package cn.iocoder.yudao.module.dim.service.asset;

import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.AssetCategorySaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.asset.AssetCategoryDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 资产分类 Service 接口
 */
public interface AssetCategoryService {

    /**
     * 创建资产分类
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createAssetCategory(@Valid AssetCategorySaveReqVO createReqVO);

    /**
     * 更新资产分类
     *
     * @param updateReqVO 更新信息
     */
    void updateAssetCategory(@Valid AssetCategorySaveReqVO updateReqVO);

    /**
     * 删除资产分类
     *
     * @param id 编号
     */
    void deleteAssetCategory(Long id);

    /**
     * 获得资产分类
     *
     * @param id 编号
     * @return 资产分类
     */
    AssetCategoryDO getAssetCategory(Long id);

    /**
     * 获得资产分类列表
     *
     * @return 资产分类列表
     */
    List<AssetCategoryDO> getAssetCategoryList();

    /**
     * 根据父级ID获得子分类列表
     *
     * @param parentId 父级ID
     * @return 子分类列表
     */
    List<AssetCategoryDO> getAssetCategoryListByParentId(Long parentId);

}
