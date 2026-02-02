package cn.iocoder.yudao.module.dim.service.dining;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.price.DiningPricePageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.price.DiningPriceSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.dining.DiningPriceDO;
import cn.iocoder.yudao.module.dim.dal.mysql.dining.DiningPriceMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.dim.enums.ErrorCodeConstants.*;

/**
 * 餐饮价格配置 Service 实现类
 */
@Service
@Validated
public class DiningPriceServiceImpl implements DiningPriceService {

    @Resource
    private DiningPriceMapper diningPriceMapper;

    @Override
    public Long createDiningPrice(DiningPriceSaveReqVO createReqVO) {
        DiningPriceDO diningPrice = BeanUtils.toBean(createReqVO, DiningPriceDO.class);
        if (diningPrice.getStatus() == null) {
            diningPrice.setStatus(0); // 默认启用
        }
        diningPriceMapper.insert(diningPrice);
        return diningPrice.getId();
    }

    @Override
    public void updateDiningPrice(DiningPriceSaveReqVO updateReqVO) {
        validateDiningPriceExists(updateReqVO.getId());
        DiningPriceDO updateObj = BeanUtils.toBean(updateReqVO, DiningPriceDO.class);
        diningPriceMapper.updateById(updateObj);
    }

    @Override
    public void deleteDiningPrice(Long id) {
        validateDiningPriceExists(id);
        diningPriceMapper.deleteById(id);
    }

    private void validateDiningPriceExists(Long id) {
        if (diningPriceMapper.selectById(id) == null) {
            throw exception(DINING_PRICE_NOT_EXISTS);
        }
    }

    @Override
    public DiningPriceDO getDiningPrice(Long id) {
        return diningPriceMapper.selectById(id);
    }

    @Override
    public PageResult<DiningPriceDO> getDiningPricePage(DiningPricePageReqVO pageReqVO) {
        return diningPriceMapper.selectPage(pageReqVO);
    }

    @Override
    public BigDecimal getPrice(Integer personType, Integer mealType, Integer diningClass) {
        DiningPriceDO diningPrice = diningPriceMapper.selectByCondition(personType, mealType, diningClass);
        return diningPrice != null ? diningPrice.getPrice() : null;
    }

}
