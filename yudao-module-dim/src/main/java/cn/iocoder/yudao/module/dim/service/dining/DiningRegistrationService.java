package cn.iocoder.yudao.module.dim.service.dining;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.DiningRegistrationPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.DiningRegistrationSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.dining.DiningRegistrationDO;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

/**
 * 报餐登记 Service 接口
 */
public interface DiningRegistrationService {

    /**
     * 创建报餐登记
     */
    Long createDiningRegistration(@Valid DiningRegistrationSaveReqVO createReqVO);

    /**
     * 更新报餐登记
     */
    void updateDiningRegistration(@Valid DiningRegistrationSaveReqVO updateReqVO);

    /**
     * 确认报餐
     */
    void confirmDiningRegistration(Long id);

    /**
     * 取消报餐
     */
    void cancelDiningRegistration(Long id);

    /**
     * 删除报餐登记
     */
    void deleteDiningRegistration(Long id);

    /**
     * 获得报餐登记
     */
    DiningRegistrationDO getDiningRegistration(Long id);

    /**
     * 获得报餐登记分页
     */
    PageResult<DiningRegistrationDO> getDiningRegistrationPage(DiningRegistrationPageReqVO pageReqVO);

    /**
     * 根据日期和餐别获得报餐列表
     */
    List<DiningRegistrationDO> getDiningRegistrationListByDateAndMealType(LocalDate date, Integer mealType);

}
