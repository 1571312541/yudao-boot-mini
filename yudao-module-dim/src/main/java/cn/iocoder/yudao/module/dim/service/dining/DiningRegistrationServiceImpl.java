package cn.iocoder.yudao.module.dim.service.dining;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.DiningRegistrationPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.DiningRegistrationSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.dining.DiningRegistrationDO;
import cn.iocoder.yudao.module.dim.dal.mysql.dining.DiningRegistrationMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.dim.enums.ErrorCodeConstants.*;

/**
 * 报餐登记 Service 实现类
 */
@Service
@Validated
public class DiningRegistrationServiceImpl implements DiningRegistrationService {

    /** 状态：待确认 */
    private static final Integer STATUS_PENDING = 0;
    /** 状态：已确认 */
    private static final Integer STATUS_CONFIRMED = 1;
    /** 状态：已取消 */
    private static final Integer STATUS_CANCELLED = 2;

    @Resource
    private DiningRegistrationMapper diningRegistrationMapper;

    @Override
    public Long createDiningRegistration(DiningRegistrationSaveReqVO createReqVO) {
        DiningRegistrationDO registration = BeanUtils.toBean(createReqVO, DiningRegistrationDO.class);
        if (registration.getRegistrationDate() == null) {
            registration.setRegistrationDate(LocalDate.now());
        }
        if (registration.getStatus() == null) {
            registration.setStatus(STATUS_PENDING);
        }
        if (registration.getGuestCount() == null) {
            registration.setGuestCount(1);
        }
        diningRegistrationMapper.insert(registration);
        return registration.getId();
    }

    @Override
    public void updateDiningRegistration(DiningRegistrationSaveReqVO updateReqVO) {
        validateDiningRegistrationExists(updateReqVO.getId());
        DiningRegistrationDO updateObj = BeanUtils.toBean(updateReqVO, DiningRegistrationDO.class);
        diningRegistrationMapper.updateById(updateObj);
    }

    @Override
    public void confirmDiningRegistration(Long id) {
        DiningRegistrationDO registration = diningRegistrationMapper.selectById(id);
        if (registration == null) {
            throw exception(DINING_REGISTRATION_NOT_EXISTS);
        }
        if (!STATUS_PENDING.equals(registration.getStatus())) {
            throw exception(DINING_REGISTRATION_STATUS_ERROR);
        }
        DiningRegistrationDO updateObj = new DiningRegistrationDO();
        updateObj.setId(id);
        updateObj.setStatus(STATUS_CONFIRMED);
        diningRegistrationMapper.updateById(updateObj);
    }

    @Override
    public void cancelDiningRegistration(Long id) {
        DiningRegistrationDO registration = diningRegistrationMapper.selectById(id);
        if (registration == null) {
            throw exception(DINING_REGISTRATION_NOT_EXISTS);
        }
        if (STATUS_CANCELLED.equals(registration.getStatus())) {
            throw exception(DINING_REGISTRATION_ALREADY_CANCELLED);
        }
        DiningRegistrationDO updateObj = new DiningRegistrationDO();
        updateObj.setId(id);
        updateObj.setStatus(STATUS_CANCELLED);
        diningRegistrationMapper.updateById(updateObj);
    }

    @Override
    public void deleteDiningRegistration(Long id) {
        validateDiningRegistrationExists(id);
        diningRegistrationMapper.deleteById(id);
    }

    private void validateDiningRegistrationExists(Long id) {
        if (diningRegistrationMapper.selectById(id) == null) {
            throw exception(DINING_REGISTRATION_NOT_EXISTS);
        }
    }

    @Override
    public DiningRegistrationDO getDiningRegistration(Long id) {
        return diningRegistrationMapper.selectById(id);
    }

    @Override
    public PageResult<DiningRegistrationDO> getDiningRegistrationPage(DiningRegistrationPageReqVO pageReqVO) {
        return diningRegistrationMapper.selectPage(pageReqVO);
    }

    @Override
    public List<DiningRegistrationDO> getDiningRegistrationListByDateAndMealType(LocalDate date, Integer mealType) {
        return diningRegistrationMapper.selectListByDateAndMealType(date, mealType);
    }

}
