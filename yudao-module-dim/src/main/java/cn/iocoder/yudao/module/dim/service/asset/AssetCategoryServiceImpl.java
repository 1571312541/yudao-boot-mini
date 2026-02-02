package cn.iocoder.yudao.module.dim.service.asset;

import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.AssetCategorySaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.asset.AssetCategoryDO;
import cn.iocoder.yudao.module.dim.dal.mysql.asset.AssetCategoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.dim.enums.ErrorCodeConstants.*;

/**
 * 资产分类 Service 实现类
 */
@Service
@Validated
public class AssetCategoryServiceImpl implements AssetCategoryService {

    @Resource
    private AssetCategoryMapper assetCategoryMapper;

    @Override
    public Long createAssetCategory(AssetCategorySaveReqVO createReqVO) {
        // 校验父级分类存在
        validateParentCategoryExists(createReqVO.getParentId());
        // 插入
        AssetCategoryDO category = BeanUtils.toBean(createReqVO, AssetCategoryDO.class);
        assetCategoryMapper.insert(category);
        return category.getId();
    }

    @Override
    public void updateAssetCategory(AssetCategorySaveReqVO updateReqVO) {
        // 校验存在
        validateAssetCategoryExists(updateReqVO.getId());
        // 校验父级分类存在
        validateParentCategoryExists(updateReqVO.getParentId());
        // 更新
        AssetCategoryDO updateObj = BeanUtils.toBean(updateReqVO, AssetCategoryDO.class);
        assetCategoryMapper.updateById(updateObj);
    }

    @Override
    public void deleteAssetCategory(Long id) {
        // 校验存在
        validateAssetCategoryExists(id);
        // 校验是否有子分类
        if (assetCategoryMapper.selectCountByParentId(id) > 0) {
            throw exception(ASSET_CATEGORY_HAS_CHILDREN);
        }
        // 删除
        assetCategoryMapper.deleteById(id);
    }

    private void validateAssetCategoryExists(Long id) {
        if (assetCategoryMapper.selectById(id) == null) {
            throw exception(ASSET_CATEGORY_NOT_EXISTS);
        }
    }

    private void validateParentCategoryExists(Long parentId) {
        if (parentId == null || parentId == 0L) {
            return;
        }
        if (assetCategoryMapper.selectById(parentId) == null) {
            throw exception(ASSET_CATEGORY_NOT_EXISTS);
        }
    }

    @Override
    public AssetCategoryDO getAssetCategory(Long id) {
        return assetCategoryMapper.selectById(id);
    }

    @Override
    public List<AssetCategoryDO> getAssetCategoryList() {
        return assetCategoryMapper.selectList();
    }

    @Override
    public List<AssetCategoryDO> getAssetCategoryListByParentId(Long parentId) {
        return assetCategoryMapper.selectListByParentId(parentId);
    }

}
