package cn.iocoder.yudao.module.dim.service.dining;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.DiningPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.DiningSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.dining.DiningDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 餐饮设置 Service 接口
 */
public interface DiningService {

    /**
     * 创建餐饮设置
     */
    Long createDining(@Valid DiningSaveReqVO createReqVO);

    /**
     * 更新餐饮设置
     */
    void updateDining(@Valid DiningSaveReqVO updateReqVO);

    /**
     * 删除餐饮设置
     */
    void deleteDining(Long id);

    /**
     * 获得餐饮设置
     */
    DiningDO getDining(Long id);

    /**
     * 获得餐饮设置分页
     */
    PageResult<DiningDO> getDiningPage(DiningPageReqVO pageReqVO);

    /**
     * 获得餐饮设置列表（启用状态）
     */
    List<DiningDO> getDiningList();

}
