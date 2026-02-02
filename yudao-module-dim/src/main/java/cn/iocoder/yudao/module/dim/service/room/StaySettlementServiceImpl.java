package cn.iocoder.yudao.module.dim.service.room;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.StaySettlementPageReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.room.StaySettlementDO;
import cn.iocoder.yudao.module.dim.dal.mysql.room.StaySettlementMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.dim.enums.ErrorCodeConstants.*;

/**
 * 住宿结算 Service 实现类
 */
@Service
@Validated
public class StaySettlementServiceImpl implements StaySettlementService {

    /** 支付状态：未结算 */
    private static final Integer PAYMENT_STATUS_UNSETTLED = 0;
    /** 支付状态：已结算 */
    private static final Integer PAYMENT_STATUS_SETTLED = 1;
    /** 开票状态：未开票 */
    private static final Integer INVOICE_STATUS_NOT_INVOICED = 0;
    /** 开票状态：已开票 */
    private static final Integer INVOICE_STATUS_INVOICED = 1;

    @Resource
    private StaySettlementMapper staySettlementMapper;

    @Override
    public Long createSettlement(StaySettlementDO settlement) {
        // 设置默认状态
        if (settlement.getPaymentStatus() == null) {
            settlement.setPaymentStatus(PAYMENT_STATUS_UNSETTLED);
        }
        if (settlement.getInvoiceStatus() == null) {
            settlement.setInvoiceStatus(INVOICE_STATUS_NOT_INVOICED);
        }
        staySettlementMapper.insert(settlement);
        return settlement.getId();
    }

    @Override
    public void updatePaymentStatus(Long id) {
        // 校验存在
        StaySettlementDO settlement = staySettlementMapper.selectById(id);
        if (settlement == null) {
            throw exception(STAY_SETTLEMENT_NOT_EXISTS);
        }
        // 校验未结算
        if (PAYMENT_STATUS_SETTLED.equals(settlement.getPaymentStatus())) {
            throw exception(STAY_SETTLEMENT_ALREADY_SETTLED);
        }
        // 更新状态
        StaySettlementDO updateObj = new StaySettlementDO();
        updateObj.setId(id);
        updateObj.setPaymentStatus(PAYMENT_STATUS_SETTLED);
        updateObj.setPaymentTime(LocalDateTime.now());
        staySettlementMapper.updateById(updateObj);
    }

    @Override
    public void updateInvoiceStatus(Long id) {
        // 校验存在
        StaySettlementDO settlement = staySettlementMapper.selectById(id);
        if (settlement == null) {
            throw exception(STAY_SETTLEMENT_NOT_EXISTS);
        }
        // 校验未开票
        if (INVOICE_STATUS_INVOICED.equals(settlement.getInvoiceStatus())) {
            throw exception(STAY_SETTLEMENT_ALREADY_INVOICED);
        }
        // 更新状态
        StaySettlementDO updateObj = new StaySettlementDO();
        updateObj.setId(id);
        updateObj.setInvoiceStatus(INVOICE_STATUS_INVOICED);
        updateObj.setInvoiceTime(LocalDateTime.now());
        staySettlementMapper.updateById(updateObj);
    }

    @Override
    public StaySettlementDO getSettlement(Long id) {
        return staySettlementMapper.selectById(id);
    }

    @Override
    public PageResult<StaySettlementDO> getSettlementPage(StaySettlementPageReqVO reqVO) {
        return staySettlementMapper.selectPage(reqVO);
    }

    @Override
    public StaySettlementDO getSettlementByGuestId(Long guestId) {
        return staySettlementMapper.selectByGuestId(guestId);
    }

}
