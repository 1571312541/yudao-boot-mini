package cn.iocoder.yudao.module.dim.service.room;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.StaySettlementPageReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.room.StaySettlementDO;

/**
 * 住宿结算 Service 接口
 */
public interface StaySettlementService {

    /**
     * 创建结算记录
     *
     * @param settlement 结算信息
     * @return 结算记录ID
     */
    Long createSettlement(StaySettlementDO settlement);

    /**
     * 更新为已结算
     *
     * @param id 结算记录ID
     */
    void updatePaymentStatus(Long id);

    /**
     * 更新为已开票
     *
     * @param id 结算记录ID
     */
    void updateInvoiceStatus(Long id);

    /**
     * 获得结算记录
     *
     * @param id 结算记录ID
     * @return 结算记录
     */
    StaySettlementDO getSettlement(Long id);

    /**
     * 获得结算分页
     *
     * @param reqVO 分页请求
     * @return 结算分页
     */
    PageResult<StaySettlementDO> getSettlementPage(StaySettlementPageReqVO reqVO);

    /**
     * 根据住客ID获得结算记录
     *
     * @param guestId 住客ID
     * @return 结算记录
     */
    StaySettlementDO getSettlementByGuestId(Long guestId);

}
