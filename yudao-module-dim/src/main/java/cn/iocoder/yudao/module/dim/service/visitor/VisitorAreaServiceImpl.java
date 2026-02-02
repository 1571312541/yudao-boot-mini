package cn.iocoder.yudao.module.dim.service.visitor;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.dim.controller.admin.visitor.vo.VisitorAreaPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.visitor.vo.VisitorAreaSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.visitor.VisitorAreaDO;
import cn.iocoder.yudao.module.dim.dal.mysql.visitor.VisitorAreaMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.dim.enums.ErrorCodeConstants.VISITOR_AREA_NOT_EXISTS;

/**
 * 访客区域 Service 实现类
 */
@Service
@Validated
public class VisitorAreaServiceImpl implements VisitorAreaService {

    @Resource
    private VisitorAreaMapper visitorAreaMapper;

    @Override
    public Long createVisitorArea(VisitorAreaSaveReqVO createReqVO) {
        VisitorAreaDO visitorArea = BeanUtils.toBean(createReqVO, VisitorAreaDO.class);
        visitorAreaMapper.insert(visitorArea);
        return visitorArea.getId();
    }

    @Override
    public void updateVisitorArea(VisitorAreaSaveReqVO updateReqVO) {
        // 校验存在
        validateVisitorAreaExists(updateReqVO.getId());
        // 更新
        VisitorAreaDO updateObj = BeanUtils.toBean(updateReqVO, VisitorAreaDO.class);
        visitorAreaMapper.updateById(updateObj);
    }

    @Override
    public void deleteVisitorArea(Long id) {
        // 校验存在
        validateVisitorAreaExists(id);
        // 删除
        visitorAreaMapper.deleteById(id);
    }

    private void validateVisitorAreaExists(Long id) {
        if (visitorAreaMapper.selectById(id) == null) {
            throw exception(VISITOR_AREA_NOT_EXISTS);
        }
    }

    @Override
    public VisitorAreaDO getVisitorArea(Long id) {
        return visitorAreaMapper.selectById(id);
    }

    @Override
    public PageResult<VisitorAreaDO> getVisitorAreaPage(VisitorAreaPageReqVO pageReqVO) {
        return visitorAreaMapper.selectPage(pageReqVO);
    }

    @Override
    public List<VisitorAreaDO> getVisitorAreaList() {
        return visitorAreaMapper.selectList();
    }

}
