package cn.iocoder.yudao.module.dim.service.asset;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.AssetPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.AssetSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.asset.AssetDO;
import cn.iocoder.yudao.module.dim.dal.mysql.asset.AssetMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.dim.enums.ErrorCodeConstants.*;

/**
 * 资产 Service 实现类
 */
@Service
@Validated
public class AssetServiceImpl implements AssetService {

    @Resource
    private AssetMapper assetMapper;

    @Resource
    private AssetCategoryService assetCategoryService;

    @Override
    public Long createAsset(AssetSaveReqVO createReqVO) {
        // 校验分类存在
        if (createReqVO.getCategoryId() != null) {
            if (assetCategoryService.getAssetCategory(createReqVO.getCategoryId()) == null) {
                throw exception(ASSET_CATEGORY_NOT_EXISTS);
            }
        }
        // 插入
        AssetDO asset = BeanUtils.toBean(createReqVO, AssetDO.class);
        assetMapper.insert(asset);
        return asset.getId();
    }

    @Override
    public void updateAsset(AssetSaveReqVO updateReqVO) {
        // 校验存在
        validateAssetExists(updateReqVO.getId());
        // 校验分类存在
        if (updateReqVO.getCategoryId() != null) {
            if (assetCategoryService.getAssetCategory(updateReqVO.getCategoryId()) == null) {
                throw exception(ASSET_CATEGORY_NOT_EXISTS);
            }
        }
        // 更新
        AssetDO updateObj = BeanUtils.toBean(updateReqVO, AssetDO.class);
        assetMapper.updateById(updateObj);
    }

    @Override
    public void deleteAsset(Long id) {
        // 校验存在
        validateAssetExists(id);
        // 删除
        assetMapper.deleteById(id);
    }

    private void validateAssetExists(Long id) {
        if (assetMapper.selectById(id) == null) {
            throw exception(ASSET_NOT_EXISTS);
        }
    }

    @Override
    public AssetDO getAsset(Long id) {
        return assetMapper.selectById(id);
    }

    @Override
    public PageResult<AssetDO> getAssetPage(AssetPageReqVO pageReqVO) {
        return assetMapper.selectPage(pageReqVO);
    }

    @Override
    public List<AssetDO> getAssetList(List<Long> ids) {
        return assetMapper.selectBatchIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAssetInventory(Long id, Integer quantity) {
        AssetDO asset = assetMapper.selectById(id);
        if (asset == null) {
            throw exception(ASSET_NOT_EXISTS);
        }
        int newInventory = asset.getInventory() + quantity;
        if (newInventory < 0) {
            throw exception(ASSET_INVENTORY_NOT_ENOUGH);
        }
        AssetDO updateObj = new AssetDO();
        updateObj.setId(id);
        updateObj.setInventory(newInventory);
        assetMapper.updateById(updateObj);
    }

}
