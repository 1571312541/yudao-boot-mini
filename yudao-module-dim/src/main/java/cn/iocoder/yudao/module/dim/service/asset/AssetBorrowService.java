package cn.iocoder.yudao.module.dim.service.asset;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.AssetBorrowPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.AssetBorrowSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.asset.AssetBorrowDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 资产外借 Service 接口
 */
public interface AssetBorrowService {

    /**
     * 创建资产外借记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createAssetBorrow(@Valid AssetBorrowSaveReqVO createReqVO);

    /**
     * 更新资产外借记录
     *
     * @param updateReqVO 更新信息
     */
    void updateAssetBorrow(@Valid AssetBorrowSaveReqVO updateReqVO);

    /**
     * 归还资产
     *
     * @param id      外借记录编号
     * @param remarks 归还备注
     */
    void returnAsset(Long id, String remarks);

    /**
     * 删除资产外借记录
     *
     * @param id 编号
     */
    void deleteAssetBorrow(Long id);

    /**
     * 获得资产外借记录
     *
     * @param id 编号
     * @return 资产外借记录
     */
    AssetBorrowDO getAssetBorrow(Long id);

    /**
     * 获得资产外借记录分页
     *
     * @param pageReqVO 分页查询
     * @return 资产外借记录分页
     */
    PageResult<AssetBorrowDO> getAssetBorrowPage(AssetBorrowPageReqVO pageReqVO);

    /**
     * 根据资产ID获得外借记录列表
     *
     * @param assetId 资产ID
     * @return 外借记录列表
     */
    List<AssetBorrowDO> getAssetBorrowListByAssetId(Long assetId);

}
