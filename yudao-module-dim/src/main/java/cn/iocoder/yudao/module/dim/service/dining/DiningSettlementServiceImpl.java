package cn.iocoder.yudao.module.dim.service.dining;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.DiningSettlementAutoCreateReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.DiningSettlementPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.DiningSettlementSaveReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.statistics.DiningUnsettledExpenseVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.dining.DiningRegistrationDO;
import cn.iocoder.yudao.module.dim.dal.dataobject.dining.DiningSettlementDO;
import cn.iocoder.yudao.module.dim.dal.mysql.dining.DiningRegistrationMapper;
import cn.iocoder.yudao.module.dim.dal.mysql.dining.DiningSettlementMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.dim.enums.ErrorCodeConstants.*;

/**
 * 餐饮结算 Service 实现类
 */
@Slf4j
@Service
@Validated
public class DiningSettlementServiceImpl implements DiningSettlementService {

    /** 状态：未结算 */
    private static final Integer STATUS_UNSETTLED = 0;
    /** 状态：已结算 */
    private static final Integer STATUS_SETTLED = 1;
    /** 已就餐标记 */
    private static final Integer USED_YES = 1;

    @Resource
    private DiningSettlementMapper diningSettlementMapper;

    @Resource
    private DiningRegistrationMapper diningRegistrationMapper;

    @Resource
    private DiningPriceService diningPriceService;

    @Override
    public Long createDiningSettlement(DiningSettlementSaveReqVO createReqVO) {
        DiningSettlementDO settlement = BeanUtils.toBean(createReqVO, DiningSettlementDO.class);
        if (settlement.getStatus() == null) {
            settlement.setStatus(STATUS_UNSETTLED);
        }
        diningSettlementMapper.insert(settlement);
        return settlement.getId();
    }

    @Override
    public void updateDiningSettlement(DiningSettlementSaveReqVO updateReqVO) {
        validateDiningSettlementExists(updateReqVO.getId());
        DiningSettlementDO updateObj = BeanUtils.toBean(updateReqVO, DiningSettlementDO.class);
        diningSettlementMapper.updateById(updateObj);
    }

    @Override
    public void confirmSettlement(Long id) {
        DiningSettlementDO settlement = diningSettlementMapper.selectById(id);
        if (settlement == null) {
            throw exception(DINING_SETTLEMENT_NOT_EXISTS);
        }
        if (STATUS_SETTLED.equals(settlement.getStatus())) {
            throw exception(DINING_SETTLEMENT_ALREADY_SETTLED);
        }
        DiningSettlementDO updateObj = new DiningSettlementDO();
        updateObj.setId(id);
        updateObj.setStatus(STATUS_SETTLED);
        diningSettlementMapper.updateById(updateObj);
    }

    @Override
    public void deleteDiningSettlement(Long id) {
        validateDiningSettlementExists(id);
        diningSettlementMapper.deleteById(id);
    }

    private void validateDiningSettlementExists(Long id) {
        if (diningSettlementMapper.selectById(id) == null) {
            throw exception(DINING_SETTLEMENT_NOT_EXISTS);
        }
    }

    @Override
    public DiningSettlementDO getDiningSettlement(Long id) {
        return diningSettlementMapper.selectById(id);
    }

    @Override
    public PageResult<DiningSettlementDO> getDiningSettlementPage(DiningSettlementPageReqVO pageReqVO) {
        return diningSettlementMapper.selectPage(pageReqVO);
    }

    @Override
    public List<DiningSettlementDO> getDiningSettlementListByUserId(Long userId) {
        return diningSettlementMapper.selectListByUserId(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long autoCreateSettlement(DiningSettlementAutoCreateReqVO reqVO) {
        // 1. 查询时间范围内的报餐登记记录（已就餐且未关联结算单）
        List<DiningRegistrationDO> registrations = diningRegistrationMapper.selectListByConditionForSettlement(
                reqVO.getUserName(),
                reqVO.getDeptName(),
                reqVO.getStartDate(),
                reqVO.getEndDate(),
                USED_YES
        );

        if (CollectionUtils.isEmpty(registrations)) {
            log.warn("[autoCreateSettlement][未找到符合条件的报餐记录]");
            throw exception(DINING_REGISTRATION_NOT_EXISTS);
        }

        // 2. 统计早/午/晚餐次数和计算费用
        int breakfastCount = 0;
        int lunchCount = 0;
        int dinnerCount = 0;
        BigDecimal totalAmount = BigDecimal.ZERO;

        // 获取第一条记录的用户信息作为结算单信息
        DiningRegistrationDO firstReg = registrations.get(0);
        String userName = firstReg.getUserName();
        String deptName = firstReg.getDeptName();

        for (DiningRegistrationDO reg : registrations) {
            Integer mealType = reg.getMealType();
            Integer guestCount = reg.getGuestCount() != null ? reg.getGuestCount() : 1;

            // 统计餐次
            if (mealType != null) {
                switch (mealType) {
                    case 0:
                        breakfastCount += guestCount;
                        break;
                    case 1:
                        lunchCount += guestCount;
                        break;
                    case 2:
                        dinnerCount += guestCount;
                        break;
                }
            }

            // 计算费用
            // 使用人员类型字段，如果没有则默认 0
            Integer personType = reg.getPersonType() != null ? reg.getPersonType() : 0;
            Integer diningClass = reg.getDiningClass() != null ? reg.getDiningClass() : 0;

            BigDecimal price = diningPriceService.getPrice(personType, mealType, diningClass);
            if (price != null) {
                totalAmount = totalAmount.add(price.multiply(BigDecimal.valueOf(guestCount)));
            }
        }

        // 3. 创建结算单记录
        DiningSettlementDO settlement = new DiningSettlementDO();
        settlement.setUserName(userName);
        settlement.setDeptName(deptName);
        settlement.setStartDate(reqVO.getStartDate());
        settlement.setEndDate(reqVO.getEndDate());
        settlement.setBreakfastCount(breakfastCount);
        settlement.setLunchCount(lunchCount);
        settlement.setDinnerCount(dinnerCount);
        settlement.setTotalAmount(totalAmount);
        settlement.setPaidAmount(BigDecimal.ZERO);
        settlement.setStatus(STATUS_UNSETTLED);
        settlement.setIsPaid(reqVO.getIsPaid() != null ? reqVO.getIsPaid() : "N");
        settlement.setIsInvoiced(reqVO.getIsInvoiced() != null ? reqVO.getIsInvoiced() : "N");

        diningSettlementMapper.insert(settlement);

        // 4. 批量更新关联的报餐登记的 settlementId 和支付状态
        for (DiningRegistrationDO reg : registrations) {
            DiningRegistrationDO updateReg = new DiningRegistrationDO();
            updateReg.setId(reg.getId());
            updateReg.setSettlementId(settlement.getId());
            updateReg.setIsPaid(settlement.getIsPaid());
            updateReg.setIsInvoiced(settlement.getIsInvoiced());
            diningRegistrationMapper.updateById(updateReg);
        }

        log.info("[autoCreateSettlement][自动创建结算单成功，ID({}), 关联报餐记录数({})]",
                settlement.getId(), registrations.size());

        return settlement.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePayInfo(Long id, String isPaid, String isInvoiced) {
        // 1. 校验结算单存在
        DiningSettlementDO settlement = diningSettlementMapper.selectById(id);
        if (settlement == null) {
            throw exception(DINING_SETTLEMENT_NOT_EXISTS);
        }

        // 2. 更新结算单的支付/开票状态
        DiningSettlementDO updateSettlement = new DiningSettlementDO();
        updateSettlement.setId(id);
        if (isPaid != null) {
            updateSettlement.setIsPaid(isPaid);
        }
        if (isInvoiced != null) {
            updateSettlement.setIsInvoiced(isInvoiced);
        }
        diningSettlementMapper.updateById(updateSettlement);

        // 3. 同步更新关联的所有报餐登记记录
        List<DiningRegistrationDO> registrations = diningRegistrationMapper.selectListBySettlementId(id);
        for (DiningRegistrationDO reg : registrations) {
            DiningRegistrationDO updateReg = new DiningRegistrationDO();
            updateReg.setId(reg.getId());
            if (isPaid != null) {
                updateReg.setIsPaid(isPaid);
            }
            if (isInvoiced != null) {
                updateReg.setIsInvoiced(isInvoiced);
            }
            diningRegistrationMapper.updateById(updateReg);
        }

        log.info("[updatePayInfo][更新支付状态成功，结算单ID({}), 关联记录数({})]", id, registrations.size());
    }

    @Override
    public DiningUnsettledExpenseVO getUnsettledExpense(Long userId, String cardId) {
        // 查询未结算的报餐登记（已就餐且未关联结算单）
        List<DiningRegistrationDO> registrations = diningRegistrationMapper.selectListByUserIdOrCardIdUnsettled(userId, cardId);

        DiningUnsettledExpenseVO result = new DiningUnsettledExpenseVO();
        List<Long> registrationIds = new ArrayList<>();

        if (CollectionUtils.isEmpty(registrations)) {
            result.setRegistrationIds(registrationIds);
            return result;
        }

        int breakfastCount = 0;
        int lunchCount = 0;
        int dinnerCount = 0;
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (DiningRegistrationDO reg : registrations) {
            registrationIds.add(reg.getId());

            Integer mealType = reg.getMealType();
            Integer guestCount = reg.getGuestCount() != null ? reg.getGuestCount() : 1;

            // 统计餐次
            if (mealType != null) {
                switch (mealType) {
                    case 0:
                        breakfastCount += guestCount;
                        break;
                    case 1:
                        lunchCount += guestCount;
                        break;
                    case 2:
                        dinnerCount += guestCount;
                        break;
                }
            }

            // 计算费用
            Integer personType = reg.getPersonType() != null ? reg.getPersonType() : 0;
            Integer diningClass = reg.getDiningClass() != null ? reg.getDiningClass() : 0;

            BigDecimal price = diningPriceService.getPrice(personType, mealType, diningClass);
            if (price != null) {
                totalAmount = totalAmount.add(price.multiply(BigDecimal.valueOf(guestCount)));
            }
        }

        result.setBreakfastCount(breakfastCount);
        result.setLunchCount(lunchCount);
        result.setDinnerCount(dinnerCount);
        result.setTotalAmount(totalAmount);
        result.setRegistrationIds(registrationIds);

        log.info("[getUnsettledExpense][查询未结算费用成功，用户ID({}), 餐卡号({}), 总金额({})]",
                userId, cardId, totalAmount);

        return result;
    }

}
