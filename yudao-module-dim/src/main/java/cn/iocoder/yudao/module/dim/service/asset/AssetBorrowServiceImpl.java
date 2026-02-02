package cn.iocoder.yudao.module.dim.service.asset;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.AssetBorrowPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.AssetBorrowSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.asset.AssetBorrowDO;
import cn.iocoder.yudao.module.dim.dal.mysql.asset.AssetBorrowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.dim.enums.ErrorCodeConstants.*;

/**
 * 资产外借 Service 实现类
 */
@Service
@Validated
public class AssetBorrowServiceImpl implements AssetBorrowService {

    /** 外借状态：借出中 */
    private static final Integer STATUS_BORROWED = 0;
    /** 外借状态：已归还 */
    private static final Integer STATUS_RETURNED = 1;

    @Resource
    private AssetBorrowMapper assetBorrowMapper;

    @Resource
    private AssetService assetService;

    @Resource
    private AssetLogService assetLogService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createAssetBorrow(AssetBorrowSaveReqVO createReqVO) {
        // 校验资产存在
        if (assetService.getAsset(createReqVO.getAssetId()) == null) {
            throw exception(ASSET_NOT_EXISTS);
        }
        // 减少库存
        assetService.updateAssetInventory(createReqVO.getAssetId(),
                -createReqVO.getQuantity());
        // 插入外借记录
        AssetBorrowDO borrow = BeanUtils.toBean(createReqVO, AssetBorrowDO.class);
        borrow.setStatus(STATUS_BORROWED);
        if (borrow.getBorrowDate() == null) {
            borrow.setBorrowDate(LocalDateTime.now());
        }
        assetBorrowMapper.insert(borrow);
        // 记录日志（外借）
        assetLogService.createAssetLog(createReqVO.getAssetId(), 2,
                createReqVO.getQuantity(), createReqVO.getRemarks());
        return borrow.getId();
    }

    @Override
    public void updateAssetBorrow(AssetBorrowSaveReqVO updateReqVO) {
        // 校验存在
        AssetBorrowDO oldBorrow = assetBorrowMapper.selectById(updateReqVO.getId());
        if (oldBorrow == null) {
            throw exception(ASSET_BORROW_NOT_EXISTS);
        }
        // 已归还的不能修改
        if (STATUS_RETURNED.equals(oldBorrow.getStatus())) {
            throw exception(ASSET_BORROW_ALREADY_RETURNED);
        }
        // 更新
        AssetBorrowDO updateObj = BeanUtils.toBean(updateReqVO, AssetBorrowDO.class);
        assetBorrowMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void returnAsset(Long id, String remarks) {
        // 校验存在
        AssetBorrowDO borrow = assetBorrowMapper.selectById(id);
        if (borrow == null) {
            throw exception(ASSET_BORROW_NOT_EXISTS);
        }
        // 已归还的不能重复归还
        if (STATUS_RETURNED.equals(borrow.getStatus())) {
            throw exception(ASSET_BORROW_ALREADY_RETURNED);
        }
        // 增加库存
        assetService.updateAssetInventory(borrow.getAssetId(), borrow.getQuantity());
        // 更新外借记录
        AssetBorrowDO updateObj = new AssetBorrowDO();
        updateObj.setId(id);
        updateObj.setStatus(STATUS_RETURNED);
        updateObj.setReturnDate(LocalDateTime.now());
        updateObj.setRemarks(remarks);
        assetBorrowMapper.updateById(updateObj);
        // 记录日志（归还）
        assetLogService.createAssetLog(borrow.getAssetId(), 3,
                borrow.getQuantity(), remarks);
    }

    @Override
    public void deleteAssetBorrow(Long id) {
        // 校验存在
        if (assetBorrowMapper.selectById(id) == null) {
            throw exception(ASSET_BORROW_NOT_EXISTS);
        }
        // 删除
        assetBorrowMapper.deleteById(id);
    }

    @Override
    public AssetBorrowDO getAssetBorrow(Long id) {
        return assetBorrowMapper.selectById(id);
    }

    @Override
    public PageResult<AssetBorrowDO> getAssetBorrowPage(AssetBorrowPageReqVO pageReqVO) {
        return assetBorrowMapper.selectPage(pageReqVO);
    }

    @Override
    public List<AssetBorrowDO> getAssetBorrowListByAssetId(Long assetId) {
        return assetBorrowMapper.selectListByAssetId(assetId);
    }

}
