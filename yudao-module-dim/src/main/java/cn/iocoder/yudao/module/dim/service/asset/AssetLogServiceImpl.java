package cn.iocoder.yudao.module.dim.service.asset;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.AssetLogPageReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.asset.AssetDO;
import cn.iocoder.yudao.module.dim.dal.dataobject.asset.AssetLogDO;
import cn.iocoder.yudao.module.dim.dal.mysql.asset.AssetLogMapper;
import cn.iocoder.yudao.module.dim.dal.mysql.asset.AssetMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

/**
 * 资产日志 Service 实现类
 */
@Service
@Validated
public class AssetLogServiceImpl implements AssetLogService {

    @Resource
    private AssetLogMapper assetLogMapper;

    @Resource
    private AssetMapper assetMapper;

    @Override
    public Long createAssetLog(Long assetId, Integer type, Integer quantity, String remarks) {
        AssetLogDO log = new AssetLogDO();
        log.setAssetId(assetId);
        log.setType(type);
        log.setQuantity(quantity);
        log.setRemarks(remarks);
        // 设置操作人
        Long loginUserId = SecurityFrameworkUtils.getLoginUserId();
        log.setOperatorId(loginUserId);
        // 获取资产名称
        AssetDO asset = assetMapper.selectById(assetId);
        if (asset != null) {
            log.setAssetName(asset.getName());
        }
        assetLogMapper.insert(log);
        return log.getId();
    }

    @Override
    public AssetLogDO getAssetLog(Long id) {
        return assetLogMapper.selectById(id);
    }

    @Override
    public PageResult<AssetLogDO> getAssetLogPage(AssetLogPageReqVO pageReqVO) {
        return assetLogMapper.selectPage(pageReqVO);
    }

    @Override
    public List<AssetLogDO> getAssetLogListByAssetId(Long assetId) {
        return assetLogMapper.selectListByAssetId(assetId);
    }

}
