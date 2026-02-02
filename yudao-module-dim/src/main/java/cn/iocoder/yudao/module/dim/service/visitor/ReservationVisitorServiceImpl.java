package cn.iocoder.yudao.module.dim.service.visitor;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.dim.controller.admin.visitor.vo.ReservationVisitorImportVO;
import cn.iocoder.yudao.module.dim.controller.admin.visitor.vo.ReservationVisitorPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.visitor.vo.ReservationVisitorSaveReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.visitor.vo.VisitorSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.visitor.ReservationVisitorDO;
import cn.iocoder.yudao.module.dim.dal.mysql.visitor.ReservationVisitorMapper;
import cn.iocoder.yudao.module.dim.enums.visitor.ReservationVisitorStatusEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.dim.enums.ErrorCodeConstants.*;

/**
 * 预约访客 Service 实现类
 */
@Service
@Validated
public class ReservationVisitorServiceImpl implements ReservationVisitorService {

    @Resource
    private ReservationVisitorMapper reservationVisitorMapper;

    @Resource
    private VisitorService visitorService;

    @Override
    public Long createReservationVisitor(ReservationVisitorSaveReqVO createReqVO) {
        ReservationVisitorDO reservationVisitor = BeanUtils.toBean(createReqVO, ReservationVisitorDO.class);
        // 默认状态为待来访
        if (reservationVisitor.getStatus() == null) {
            reservationVisitor.setStatus(ReservationVisitorStatusEnum.PENDING.getStatus());
        }
        reservationVisitorMapper.insert(reservationVisitor);
        return reservationVisitor.getId();
    }

    @Override
    public void updateReservationVisitor(ReservationVisitorSaveReqVO updateReqVO) {
        // 校验存在
        validateReservationVisitorExists(updateReqVO.getId());
        // 更新
        ReservationVisitorDO updateObj = BeanUtils.toBean(updateReqVO, ReservationVisitorDO.class);
        reservationVisitorMapper.updateById(updateObj);
    }

    @Override
    public void deleteReservationVisitor(Long id) {
        // 校验存在
        validateReservationVisitorExists(id);
        // 删除
        reservationVisitorMapper.deleteById(id);
    }

    /**
     * 校验预约访客是否存在
     *
     * @param id 预约访客ID
     */
    private void validateReservationVisitorExists(Long id) {
        if (reservationVisitorMapper.selectById(id) == null) {
            throw exception(RESERVATION_VISITOR_NOT_EXISTS);
        }
    }

    /**
     * 获取预约访客并校验存在性
     *
     * @param id 预约访客ID
     * @return 预约访客实体
     */
    private ReservationVisitorDO getAndValidateReservationVisitor(Long id) {
        ReservationVisitorDO reservationVisitor = reservationVisitorMapper.selectById(id);
        if (reservationVisitor == null) {
            throw exception(RESERVATION_VISITOR_NOT_EXISTS);
        }
        return reservationVisitor;
    }

    /**
     * 校验预约访客状态是否可操作（非已来访、非已取消）
     *
     * @param reservationVisitor 预约访客实体
     * @param checkExpired 是否校验过期状态
     */
    private void validateReservationVisitorStatusForOperation(ReservationVisitorDO reservationVisitor, boolean checkExpired) {
        if (ReservationVisitorStatusEnum.CHECKED_IN.getStatus().equals(reservationVisitor.getStatus())) {
            throw exception(RESERVATION_VISITOR_ALREADY_CHECKED_IN);
        }
        if (ReservationVisitorStatusEnum.CANCELLED.getStatus().equals(reservationVisitor.getStatus())) {
            throw exception(RESERVATION_VISITOR_ALREADY_CANCELLED);
        }
        if (checkExpired && ReservationVisitorStatusEnum.EXPIRED.getStatus().equals(reservationVisitor.getStatus())) {
            throw exception(RESERVATION_VISITOR_EXPIRED);
        }
    }

    @Override
    public ReservationVisitorDO getReservationVisitor(Long id) {
        return reservationVisitorMapper.selectById(id);
    }

    @Override
    public PageResult<ReservationVisitorDO> getReservationVisitorPage(ReservationVisitorPageReqVO pageReqVO) {
        return reservationVisitorMapper.selectPage(pageReqVO);
    }

    @Override
    public void cancel(Long id) {
        ReservationVisitorDO reservationVisitor = getAndValidateReservationVisitor(id);
        // 校验状态：不校验过期（过期也可以取消）
        validateReservationVisitorStatusForOperation(reservationVisitor, false);
        // 更新状态为已取消
        reservationVisitor.setStatus(ReservationVisitorStatusEnum.CANCELLED.getStatus());
        reservationVisitorMapper.updateById(reservationVisitor);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long checkIn(Long id) {
        ReservationVisitorDO reservationVisitor = getAndValidateReservationVisitor(id);
        // 校验状态：需校验过期（过期不可来访）
        validateReservationVisitorStatusForOperation(reservationVisitor, true);

        // 创建正式访客
        VisitorSaveReqVO visitorReqVO = BeanUtils.toBean(reservationVisitor, VisitorSaveReqVO.class);
        visitorReqVO.setId(null); // 清空ID，创建新记录
        visitorReqVO.setRemarks(reservationVisitor.getPurpose()); // 来访事由作为备注
        Long visitorId = visitorService.createVisitor(visitorReqVO);

        // 更新预约状态为已来访
        reservationVisitor.setStatus(ReservationVisitorStatusEnum.CHECKED_IN.getStatus());
        reservationVisitorMapper.updateById(reservationVisitor);

        return visitorId;
    }

    @Override
    public ReservationVisitorImportRespVO importReservationVisitors(List<ReservationVisitorImportVO> list, boolean updateSupport) {
        ReservationVisitorImportRespVO result = new ReservationVisitorImportRespVO();
        result.createCount = 0;
        result.updateCount = 0;
        result.failMessages = new ArrayList<>();

        if (CollUtil.isEmpty(list)) {
            return result;
        }

        // Excel行号偏移量：第1行是表头，数据从第2行开始，所以 i+2 表示实际Excel行号
        final int EXCEL_ROW_OFFSET = 2;
        for (int i = 0; i < list.size(); i++) {
            ReservationVisitorImportVO importVO = list.get(i);
            try {
                // 根据证件号查找是否已存在
                ReservationVisitorDO existRecord = null;
                if (importVO.getIdNum() != null && !importVO.getIdNum().isEmpty()) {
                    existRecord = reservationVisitorMapper.selectByIdNum(importVO.getIdNum());
                }

                if (existRecord == null) {
                    // 新增
                    ReservationVisitorDO record = BeanUtils.toBean(importVO, ReservationVisitorDO.class);
                    record.setStatus(ReservationVisitorStatusEnum.PENDING.getStatus());
                    reservationVisitorMapper.insert(record);
                    result.createCount++;
                } else if (updateSupport) {
                    // 更新
                    ReservationVisitorDO updateObj = BeanUtils.toBean(importVO, ReservationVisitorDO.class);
                    updateObj.setId(existRecord.getId());
                    reservationVisitorMapper.updateById(updateObj);
                    result.updateCount++;
                } else {
                    result.failMessages.add("第 " + (i + EXCEL_ROW_OFFSET) + " 行：证件号码已存在");
                }
            } catch (Exception e) {
                result.failMessages.add("第 " + (i + EXCEL_ROW_OFFSET) + " 行：导入失败 - " + e.getMessage());
            }
        }

        return result;
    }

}
