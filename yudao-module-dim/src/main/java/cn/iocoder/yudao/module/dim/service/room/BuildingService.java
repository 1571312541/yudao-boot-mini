package cn.iocoder.yudao.module.dim.service.room;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.BuildingPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.BuildingSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.room.BuildingDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 楼栋 Service 接口
 */
public interface BuildingService {

    /**
     * 创建楼栋
     */
    Long createBuilding(@Valid BuildingSaveReqVO createReqVO);

    /**
     * 更新楼栋
     */
    void updateBuilding(@Valid BuildingSaveReqVO updateReqVO);

    /**
     * 删除楼栋
     */
    void deleteBuilding(Long id);

    /**
     * 获得楼栋
     */
    BuildingDO getBuilding(Long id);

    /**
     * 获得楼栋分页
     */
    PageResult<BuildingDO> getBuildingPage(BuildingPageReqVO pageReqVO);

    /**
     * 获得楼栋列表
     */
    List<BuildingDO> getBuildingList(String name, Integer status);

}
