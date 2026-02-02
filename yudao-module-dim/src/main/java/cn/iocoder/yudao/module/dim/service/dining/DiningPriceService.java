package cn.iocoder.yudao.module.dim.service.dining;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.price.DiningPricePageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.price.DiningPriceSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.dining.DiningPriceDO;

import javax.validation.Valid;
import java.math.BigDecimal;

/**
 * 餐饮价格配置 Service 接口
 */
public interface DiningPriceService {

    /**
     * 创建餐饮价格配置
     */
    Long createDiningPrice(@Valid DiningPriceSaveReqVO createReqVO);

    /**
     * 更新餐饮价格配置
     */
    void updateDiningPrice(@Valid DiningPriceSaveReqVO updateReqVO);

    /**
     * 删除餐饮价格配置
     */
    void deleteDiningPrice(Long id);

    /**
     * 获得餐饮价格配置
     */
    DiningPriceDO getDiningPrice(Long id);

    /**
     * 获得餐饮价格配置分页
     */
    PageResult<DiningPriceDO> getDiningPricePage(DiningPricePageReqVO pageReqVO);

    /**
     * 根据人员类型、餐别、分类获取价格
     *
     * @param personType 人员类型
     * @param mealType 餐别
     * @param diningClass 分类
     * @return 价格，如果未找到返回null
     */
    BigDecimal getPrice(Integer personType, Integer mealType, Integer diningClass);

}
