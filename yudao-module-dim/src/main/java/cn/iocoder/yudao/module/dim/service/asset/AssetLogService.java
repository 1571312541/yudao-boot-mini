package cn.iocoder.yudao.module.dim.service.asset;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.AssetLogPageReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.asset.AssetLogDO;

import java.util.List;

/**
 * 资产日志 Service 接口
 */
public interface AssetLogService {

    /**
     * 创建资产日志
     *
     * @param assetId  资产ID
     * @param type     操作类型（0-入库 1-出库 2-外借 3-归还 4-报废）
     * @param quantity 数量
     * @param remarks  备注
     * @return 编号
     */
    Long createAssetLog(Long assetId, Integer type, Integer quantity, String remarks);

    /**
     * 获得资产日志
     *
     * @param id 编号
     * @return 资产日志
     */
    AssetLogDO getAssetLog(Long id);

    /**
     * 获得资产日志分页
     *
     * @param pageReqVO 分页查询
     * @return 资产日志分页
     */
    PageResult<AssetLogDO> getAssetLogPage(AssetLogPageReqVO pageReqVO);

    /**
     * 根据资产ID获得日志列表
     *
     * @param assetId 资产ID
     * @return 日志列表
     */
    List<AssetLogDO> getAssetLogListByAssetId(Long assetId);

}
