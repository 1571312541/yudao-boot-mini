package cn.iocoder.yudao.module.dim.service.dining;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.DiningPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.DiningSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.dining.DiningDO;
import cn.iocoder.yudao.module.dim.dal.mysql.dining.DiningMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.dim.enums.ErrorCodeConstants.*;

/**
 * 餐饮设置 Service 实现类
 */
@Service
@Validated
public class DiningServiceImpl implements DiningService {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    @Resource
    private DiningMapper diningMapper;

    @Override
    public Long createDining(DiningSaveReqVO createReqVO) {
        DiningDO dining = BeanUtils.toBean(createReqVO, DiningDO.class);
        // 处理时间字符串转换
        if (createReqVO.getStartTime() != null) {
            dining.setStartTime(LocalTime.parse(createReqVO.getStartTime(), TIME_FORMATTER));
        }
        if (createReqVO.getEndTime() != null) {
            dining.setEndTime(LocalTime.parse(createReqVO.getEndTime(), TIME_FORMATTER));
        }
        diningMapper.insert(dining);
        return dining.getId();
    }

    @Override
    public void updateDining(DiningSaveReqVO updateReqVO) {
        validateDiningExists(updateReqVO.getId());
        DiningDO updateObj = BeanUtils.toBean(updateReqVO, DiningDO.class);
        // 处理时间字符串转换
        if (updateReqVO.getStartTime() != null) {
            updateObj.setStartTime(LocalTime.parse(updateReqVO.getStartTime(), TIME_FORMATTER));
        }
        if (updateReqVO.getEndTime() != null) {
            updateObj.setEndTime(LocalTime.parse(updateReqVO.getEndTime(), TIME_FORMATTER));
        }
        diningMapper.updateById(updateObj);
    }

    @Override
    public void deleteDining(Long id) {
        validateDiningExists(id);
        diningMapper.deleteById(id);
    }

    private void validateDiningExists(Long id) {
        if (diningMapper.selectById(id) == null) {
            throw exception(DINING_NOT_EXISTS);
        }
    }

    @Override
    public DiningDO getDining(Long id) {
        return diningMapper.selectById(id);
    }

    @Override
    public PageResult<DiningDO> getDiningPage(DiningPageReqVO pageReqVO) {
        return diningMapper.selectPage(pageReqVO);
    }

    @Override
    public List<DiningDO> getDiningList() {
        // 返回启用状态的餐饮设置
        return diningMapper.selectListByStatus(0);
    }

}
