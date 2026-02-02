package cn.iocoder.yudao.module.dim.service.dining;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.DiningRecordPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.DiningRecordSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.dining.DiningRecordDO;
import cn.iocoder.yudao.module.dim.dal.dataobject.dining.DiningRegistrationDO;
import cn.iocoder.yudao.module.dim.dal.dataobject.visitor.VisitorDO;
import cn.iocoder.yudao.module.dim.dal.mysql.dining.DiningRecordMapper;
import cn.iocoder.yudao.module.dim.dal.mysql.dining.DiningRegistrationMapper;
import cn.iocoder.yudao.module.dim.dal.mysql.visitor.VisitorMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.dim.enums.ErrorCodeConstants.*;

/**
 * 就餐记录 Service 实现类
 */
@Slf4j
@Service
@Validated
public class DiningRecordServiceImpl implements DiningRecordService {

    /** 物业人员类型 */
    private static final Integer PERSON_TYPE_PROPERTY = 3;
    /** 已就餐标记 */
    private static final Integer USED_YES = 1;

    @Resource
    private DiningRecordMapper diningRecordMapper;

    @Resource
    private DiningRegistrationMapper diningRegistrationMapper;

    @Resource
    private VisitorMapper visitorMapper;

    @Resource
    private DiningPriceService diningPriceService;

    @Override
    public Long createDiningRecord(DiningRecordSaveReqVO createReqVO) {
        DiningRecordDO record = BeanUtils.toBean(createReqVO, DiningRecordDO.class);
        if (record.getDiningDate() == null) {
            record.setDiningDate(LocalDate.now());
        }
        diningRecordMapper.insert(record);
        return record.getId();
    }

    @Override
    public void updateDiningRecord(DiningRecordSaveReqVO updateReqVO) {
        validateDiningRecordExists(updateReqVO.getId());
        DiningRecordDO updateObj = BeanUtils.toBean(updateReqVO, DiningRecordDO.class);
        diningRecordMapper.updateById(updateObj);
    }

    @Override
    public void deleteDiningRecord(Long id) {
        validateDiningRecordExists(id);
        diningRecordMapper.deleteById(id);
    }

    private void validateDiningRecordExists(Long id) {
        if (diningRecordMapper.selectById(id) == null) {
            throw exception(DINING_RECORD_NOT_EXISTS);
        }
    }

    @Override
    public DiningRecordDO getDiningRecord(Long id) {
        return diningRecordMapper.selectById(id);
    }

    @Override
    public PageResult<DiningRecordDO> getDiningRecordPage(DiningRecordPageReqVO pageReqVO) {
        return diningRecordMapper.selectPage(pageReqVO);
    }

    @Override
    public List<DiningRecordDO> getDiningRecordListByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        return diningRecordMapper.selectListByUserIdAndDateRange(userId, startDate, endDate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int swipeCard(String cardNo, Integer mealType) {
        // 1. 根据餐卡号查询访客信息
        VisitorDO visitor = visitorMapper.selectByDiningNum(cardNo);
        if (visitor == null) {
            log.warn("[swipeCard][餐卡号({})不存在]", cardNo);
            return -1; // 该卡不可用
        }

        LocalDate today = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();

        // 2. 如果未指定餐类，根据当前时间自动判断
        if (mealType == null) {
            mealType = getMealTypeByTime(now);
        }

        // 3. 检查是否已刷卡（当天同餐类是否已有就餐记录）
        List<DiningRecordDO> existingRecords = diningRecordMapper.selectListByCardIdAndDateAndMealType(cardNo, today, mealType);
        if (!CollectionUtils.isEmpty(existingRecords)) {
            log.warn("[swipeCard][餐卡号({})今日餐类({})已刷卡]", cardNo, mealType);
            return -2; // 已经刷过卡
        }

        // 4. 查询报餐登记记录
        List<DiningRegistrationDO> registrations = diningRegistrationMapper.selectListByCardIdAndDateAndMealType(cardNo, today, mealType);

        // 5. 非物业人员需验证是否已报餐
        if (!PERSON_TYPE_PROPERTY.equals(visitor.getType())) {
            if (CollectionUtils.isEmpty(registrations)) {
                log.warn("[swipeCard][餐卡号({})未报餐]", cardNo);
                return -3; // 未报餐
            }
        }

        // 6. 获取报餐登记信息（如有）
        DiningRegistrationDO registration = CollectionUtils.isEmpty(registrations) ? null : registrations.get(0);
        Integer personType = visitor.getType();
        Integer diningClass = registration != null ? registration.getDiningClass() : 0; // 默认客餐
        Integer guestCount = registration != null && registration.getGuestCount() != null ? registration.getGuestCount() : 1;

        // 7. 计算价格
        BigDecimal price = diningPriceService.getPrice(personType, mealType, diningClass);
        if (price == null) {
            price = BigDecimal.ZERO;
        }
        // 如果有人数，价格乘以人数
        BigDecimal totalAmount = price.multiply(BigDecimal.valueOf(guestCount));

        // 8. 创建就餐记录
        DiningRecordDO record = new DiningRecordDO();
        record.setUserId(visitor.getId());
        record.setUserName(visitor.getName());
        record.setDeptName(visitor.getUnitName());
        record.setDiningDate(today);
        record.setMealType(mealType);
        record.setPayType(0); // 刷卡
        record.setAmount(totalAmount);
        record.setPersonType(personType);
        record.setCardId(cardNo);
        record.setDiningClass(diningClass);
        if (registration != null) {
            record.setRegistrationId(registration.getId());
        }
        diningRecordMapper.insert(record);

        // 9. 更新报餐登记的 used 字段为 1（已就餐）
        if (registration != null) {
            DiningRegistrationDO updateReg = new DiningRegistrationDO();
            updateReg.setId(registration.getId());
            updateReg.setUsed(USED_YES);
            diningRegistrationMapper.updateById(updateReg);
        }

        log.info("[swipeCard][餐卡号({})刷卡成功，就餐记录ID({})]", cardNo, record.getId());
        return 0; // 成功
    }

    /**
     * 根据当前时间判断餐类
     * 9点前 = 早餐(0)
     * 9-14点 = 午餐(1)
     * 14点后 = 晚餐(2)
     */
    private Integer getMealTypeByTime(LocalDateTime time) {
        int hour = time.getHour();
        if (hour < 9) {
            return 0; // 早餐
        } else if (hour < 14) {
            return 1; // 午餐
        } else {
            return 2; // 晚餐
        }
    }

}
