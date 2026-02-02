package cn.iocoder.yudao.module.dim.service.asset;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.AssetHolderPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.AssetHolderSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.asset.AssetHolderDO;
import cn.iocoder.yudao.module.dim.dal.mysql.asset.AssetHolderMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.dim.enums.ErrorCodeConstants.*;

/**
 * 资产持有 Service 实现类
 */
@Service
@Validated
public class AssetHolderServiceImpl implements AssetHolderService {

    @Resource
    private AssetHolderMapper assetHolderMapper;

    @Resource
    private AssetService assetService;

    @Override
    public Long createAssetHolder(AssetHolderSaveReqVO createReqVO) {
        // 校验资产存在
        if (assetService.getAsset(createReqVO.getAssetId()) == null) {
            throw exception(ASSET_NOT_EXISTS);
        }
        // 插入
        AssetHolderDO holder = BeanUtils.toBean(createReqVO, AssetHolderDO.class);
        assetHolderMapper.insert(holder);
        return holder.getId();
    }

    @Override
    public void updateAssetHolder(AssetHolderSaveReqVO updateReqVO) {
        // 校验存在
        validateAssetHolderExists(updateReqVO.getId());
        // 更新
        AssetHolderDO updateObj = BeanUtils.toBean(updateReqVO, AssetHolderDO.class);
        assetHolderMapper.updateById(updateObj);
    }

    @Override
    public void deleteAssetHolder(Long id) {
        // 校验存在
        validateAssetHolderExists(id);
        // 删除
        assetHolderMapper.deleteById(id);
    }

    private void validateAssetHolderExists(Long id) {
        if (assetHolderMapper.selectById(id) == null) {
            throw exception(ASSET_HOLDER_NOT_EXISTS);
        }
    }

    @Override
    public AssetHolderDO getAssetHolder(Long id) {
        return assetHolderMapper.selectById(id);
    }

    @Override
    public PageResult<AssetHolderDO> getAssetHolderPage(AssetHolderPageReqVO pageReqVO) {
        return assetHolderMapper.selectPage(pageReqVO);
    }

    @Override
    public List<AssetHolderDO> getAssetHolderListByAssetId(Long assetId) {
        return assetHolderMapper.selectListByAssetId(assetId);
    }

}
