package cn.iocoder.yudao.module.dim.service.dining;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.DiningSettlementAutoCreateReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.DiningSettlementPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.DiningSettlementSaveReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.statistics.DiningUnsettledExpenseVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.dining.DiningSettlementDO;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

/**
 * 餐饮结算 Service 接口
 */
public interface DiningSettlementService {

    /**
     * 创建餐饮结算
     */
    Long createDiningSettlement(@Valid DiningSettlementSaveReqVO createReqVO);

    /**
     * 更新餐饮结算
     */
    void updateDiningSettlement(@Valid DiningSettlementSaveReqVO updateReqVO);

    /**
     * 确认结算
     */
    void confirmSettlement(Long id);

    /**
     * 删除餐饮结算
     */
    void deleteDiningSettlement(Long id);

    /**
     * 获得餐饮结算
     */
    DiningSettlementDO getDiningSettlement(Long id);

    /**
     * 获得餐饮结算分页
     */
    PageResult<DiningSettlementDO> getDiningSettlementPage(DiningSettlementPageReqVO pageReqVO);

    /**
     * 根据用户ID获得结算记录
     */
    List<DiningSettlementDO> getDiningSettlementListByUserId(Long userId);

    /**
     * 自动创建结算单
     *
     * @param reqVO 自动创建请求参数
     * @return 结算单ID
     */
    Long autoCreateSettlement(@Valid DiningSettlementAutoCreateReqVO reqVO);

    /**
     * 更新支付/开票状态
     * 同时更新结算单和关联的所有报餐登记记录
     *
     * @param id 结算单ID
     * @param isPaid 是否已支付
     * @param isInvoiced 是否已开票
     */
    void updatePayInfo(Long id, String isPaid, String isInvoiced);

    /**
     * 根据用户ID或餐卡号获取未结算的餐饮费用
     * 供住宿模块退房时调用
     *
     * @param userId 用户ID（可选）
     * @param cardId 餐卡号（可选，与userId二选一）
     * @return 未结算费用信息
     */
    DiningUnsettledExpenseVO getUnsettledExpense(Long userId, String cardId);

}
