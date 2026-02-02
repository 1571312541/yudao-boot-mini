package cn.iocoder.yudao.module.dim.service.room;

import cn.iocoder.yudao.module.dim.controller.admin.room.vo.FloorSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.room.FloorDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 楼层 Service 接口
 */
public interface FloorService {

    /**
     * 创建楼层
     */
    Long createFloor(@Valid FloorSaveReqVO createReqVO);

    /**
     * 更新楼层
     */
    void updateFloor(@Valid FloorSaveReqVO updateReqVO);

    /**
     * 删除楼层
     */
    void deleteFloor(Long id);

    /**
     * 获得楼层
     */
    FloorDO getFloor(Long id);

    /**
     * 根据楼栋ID获得楼层列表
     */
    List<FloorDO> getFloorListByBuildingId(Long buildingId);

}
