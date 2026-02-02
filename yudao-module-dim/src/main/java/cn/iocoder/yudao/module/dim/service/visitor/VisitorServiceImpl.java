package cn.iocoder.yudao.module.dim.service.visitor;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.dim.controller.admin.visitor.vo.VisitorImportVO;
import cn.iocoder.yudao.module.dim.controller.admin.visitor.vo.VisitorPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.visitor.vo.VisitorSaveReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.visitor.vo.VisitLogSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.visitor.VisitorDO;
import cn.iocoder.yudao.module.dim.dal.mysql.visitor.VisitorMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.dim.enums.ErrorCodeConstants.VISITOR_ID_NUM_DUPLICATE;
import static cn.iocoder.yudao.module.dim.enums.ErrorCodeConstants.VISITOR_NOT_EXISTS;

/**
 * 访客 Service 实现类
 */
@Service
@Validated
public class VisitorServiceImpl implements VisitorService {

    @Resource
    private VisitorMapper visitorMapper;

    @Resource
    private VisitLogService visitLogService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createVisitor(VisitorSaveReqVO createReqVO) {
        // 校验证件号唯一
        validateIdNumUnique(null, createReqVO.getIdNum());
        // 设置登记日期
        VisitorDO visitor = BeanUtils.toBean(createReqVO, VisitorDO.class);
        if (visitor.getRegDate() == null) {
            visitor.setRegDate(LocalDateTime.now());
        }
        visitorMapper.insert(visitor);

        // 自动创建来访日志
        VisitLogSaveReqVO visitLog = new VisitLogSaveReqVO();
        visitLog.setVisitorId(visitor.getId());
        visitLog.setVisitDate(LocalDateTime.now());
        visitLog.setStatus(0); // 来访状态
        visitLog.setPurpose(createReqVO.getRemarks()); // 使用备注作为来访事由
        visitLogService.createVisitLog(visitLog);

        return visitor.getId();
    }

    @Override
    public void updateVisitor(VisitorSaveReqVO updateReqVO) {
        // 校验存在
        validateVisitorExists(updateReqVO.getId());
        // 校验证件号唯一
        validateIdNumUnique(updateReqVO.getId(), updateReqVO.getIdNum());
        // 更新
        VisitorDO updateObj = BeanUtils.toBean(updateReqVO, VisitorDO.class);
        visitorMapper.updateById(updateObj);
    }

    @Override
    public void deleteVisitor(Long id) {
        // 校验存在
        validateVisitorExists(id);
        // 删除
        visitorMapper.deleteById(id);
    }

    private void validateVisitorExists(Long id) {
        if (visitorMapper.selectById(id) == null) {
            throw exception(VISITOR_NOT_EXISTS);
        }
    }

    private void validateIdNumUnique(Long id, String idNum) {
        if (idNum == null || idNum.isEmpty()) {
            return;
        }
        VisitorDO visitor = visitorMapper.selectByIdNum(idNum);
        if (visitor == null) {
            return;
        }
        if (id == null) {
            throw exception(VISITOR_ID_NUM_DUPLICATE);
        }
        if (!visitor.getId().equals(id)) {
            throw exception(VISITOR_ID_NUM_DUPLICATE);
        }
    }

    @Override
    public VisitorDO getVisitor(Long id) {
        return visitorMapper.selectById(id);
    }

    @Override
    public PageResult<VisitorDO> getVisitorPage(VisitorPageReqVO pageReqVO) {
        return visitorMapper.selectPage(pageReqVO);
    }

    @Override
    public void departure(Long id) {
        VisitorDO visitor = visitorMapper.selectById(id);
        if (visitor == null) {
            throw exception(VISITOR_NOT_EXISTS);
        }
        visitor.setDepartureDate(LocalDateTime.now());
        visitorMapper.updateById(visitor);
    }

    @Override
    public VisitorImportRespVO importVisitors(List<VisitorImportVO> importList, boolean updateSupport) {
        VisitorImportRespVO result = new VisitorImportRespVO();
        result.createCount = 0;
        result.updateCount = 0;
        result.failMessages = new ArrayList<>();

        if (CollUtil.isEmpty(importList)) {
            return result;
        }

        for (int i = 0; i < importList.size(); i++) {
            VisitorImportVO importVO = importList.get(i);
            try {
                // 根据证件号查找是否已存在
                VisitorDO existVisitor = null;
                if (importVO.getIdNum() != null && !importVO.getIdNum().isEmpty()) {
                    existVisitor = visitorMapper.selectByIdNum(importVO.getIdNum());
                }

                if (existVisitor == null) {
                    // 新增
                    VisitorDO visitor = BeanUtils.toBean(importVO, VisitorDO.class);
                    visitor.setRegDate(LocalDateTime.now());
                    visitorMapper.insert(visitor);
                    result.createCount++;
                } else if (updateSupport) {
                    // 更新
                    VisitorDO updateObj = BeanUtils.toBean(importVO, VisitorDO.class);
                    updateObj.setId(existVisitor.getId());
                    visitorMapper.updateById(updateObj);
                    result.updateCount++;
                } else {
                    result.failMessages.add("第 " + (i + 2) + " 行：证件号码已存在");
                }
            } catch (Exception e) {
                result.failMessages.add("第 " + (i + 2) + " 行：导入失败 - " + e.getMessage());
            }
        }

        return result;
    }

    @Override
    public void updateVisitorImgInfo(Long visitorId, String imgInfo) {
        VisitorDO visitor = visitorMapper.selectById(visitorId);
        if (visitor == null) {
            throw exception(VISITOR_NOT_EXISTS);
        }
        visitor.setImgInfo(imgInfo);
        visitorMapper.updateById(visitor);
    }

}
