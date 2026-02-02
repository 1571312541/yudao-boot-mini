package cn.iocoder.yudao.module.dim.service.visitor;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.dim.controller.admin.visitor.vo.VisitorAreaPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.visitor.vo.VisitorAreaSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.visitor.VisitorAreaDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 访客区域 Service 接口
 */
public interface VisitorAreaService {

    /**
     * 创建访客区域
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createVisitorArea(@Valid VisitorAreaSaveReqVO createReqVO);

    /**
     * 更新访客区域
     *
     * @param updateReqVO 更新信息
     */
    void updateVisitorArea(@Valid VisitorAreaSaveReqVO updateReqVO);

    /**
     * 删除访客区域
     *
     * @param id 编号
     */
    void deleteVisitorArea(Long id);

    /**
     * 获得访客区域
     *
     * @param id 编号
     * @return 访客区域
     */
    VisitorAreaDO getVisitorArea(Long id);

    /**
     * 获得访客区域分页
     *
     * @param pageReqVO 分页查询
     * @return 访客区域分页
     */
    PageResult<VisitorAreaDO> getVisitorAreaPage(VisitorAreaPageReqVO pageReqVO);

    /**
     * 获得访客区域列表
     *
     * @return 访客区域列表
     */
    List<VisitorAreaDO> getVisitorAreaList();

}
