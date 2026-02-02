package cn.iocoder.yudao.module.dim.service.visitor;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.dim.controller.admin.visitor.vo.ReservationVisitorImportVO;
import cn.iocoder.yudao.module.dim.controller.admin.visitor.vo.ReservationVisitorPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.visitor.vo.ReservationVisitorSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.visitor.ReservationVisitorDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 预约访客 Service 接口
 */
public interface ReservationVisitorService {

    /**
     * 创建预约访客
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createReservationVisitor(@Valid ReservationVisitorSaveReqVO createReqVO);

    /**
     * 更新预约访客
     *
     * @param updateReqVO 更新信息
     */
    void updateReservationVisitor(@Valid ReservationVisitorSaveReqVO updateReqVO);

    /**
     * 删除预约访客
     *
     * @param id 编号
     */
    void deleteReservationVisitor(Long id);

    /**
     * 获得预约访客
     *
     * @param id 编号
     * @return 预约访客
     */
    ReservationVisitorDO getReservationVisitor(Long id);

    /**
     * 获得预约访客分页
     *
     * @param pageReqVO 分页查询
     * @return 预约访客分页
     */
    PageResult<ReservationVisitorDO> getReservationVisitorPage(ReservationVisitorPageReqVO pageReqVO);

    /**
     * 取消预约
     *
     * @param id 编号
     */
    void cancel(Long id);

    /**
     * 预约来访，转为正式访客
     *
     * @param id 预约编号
     * @return 访客编号
     */
    Long checkIn(Long id);

    /**
     * 批量导入预约访客
     *
     * @param list 导入列表
     * @param updateSupport 是否更新已存在的记录
     * @return 导入结果
     */
    ReservationVisitorImportRespVO importReservationVisitors(List<ReservationVisitorImportVO> list, boolean updateSupport);

    /**
     * 导入结果 VO
     */
    class ReservationVisitorImportRespVO {
        public int createCount;
        public int updateCount;
        public List<String> failMessages;
    }

}
