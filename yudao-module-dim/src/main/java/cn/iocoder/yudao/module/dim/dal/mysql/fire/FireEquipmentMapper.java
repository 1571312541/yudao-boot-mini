package cn.iocoder.yudao.module.dim.dal.mysql.fire;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.dim.controller.admin.fire.vo.FireEquipmentPageReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.fire.FireEquipmentDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
public interface FireEquipmentMapper extends BaseMapperX<FireEquipmentDO> {

    default PageResult<FireEquipmentDO> selectPage(FireEquipmentPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FireEquipmentDO>()
                .likeIfPresent(FireEquipmentDO::getName, reqVO.getName())
                .likeIfPresent(FireEquipmentDO::getCode, reqVO.getCode())
                .eqIfPresent(FireEquipmentDO::getType, reqVO.getType())
                .eqIfPresent(FireEquipmentDO::getBuildingNo, reqVO.getBuildingNo())
                .eqIfPresent(FireEquipmentDO::getFloor, reqVO.getFloor())
                .eqIfPresent(FireEquipmentDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(FireEquipmentDO::getCreateTime, reqVO.getCreateTime())
                .orderByAsc(FireEquipmentDO::getBuildingNo)
                .orderByAsc(FireEquipmentDO::getFloor)
                .orderByDesc(FireEquipmentDO::getId));
    }

    default List<FireEquipmentDO> selectListByBuildingAndFloor(Integer buildingNo, Integer floor) {
        return selectList(new LambdaQueryWrapperX<FireEquipmentDO>()
                .eqIfPresent(FireEquipmentDO::getBuildingNo, buildingNo)
                .eqIfPresent(FireEquipmentDO::getFloor, floor)
                .orderByAsc(FireEquipmentDO::getId));
    }

    @Select("SELECT DISTINCT building_no FROM dim_fire_equipment WHERE deleted = 0 ORDER BY building_no")
    List<Integer> selectDistinctBuildingNos();

    @Select("SELECT DISTINCT floor FROM dim_fire_equipment WHERE deleted = 0 AND building_no = #{buildingNo} ORDER BY floor")
    List<Integer> selectDistinctFloorsByBuilding(Integer buildingNo);

    /**
     * 按设备类型统计数量
     */
    @Select("SELECT type, COUNT(*) as count FROM dim_fire_equipment WHERE deleted = 0 GROUP BY type")
    List<Map<String, Object>> selectCountGroupByType();

    /**
     * 按状态统计数量
     */
    @Select("SELECT status, COUNT(*) as count FROM dim_fire_equipment WHERE deleted = 0 GROUP BY status")
    List<Map<String, Object>> selectCountGroupByStatus();

    /**
     * 按楼号统计数量
     */
    @Select("SELECT building_no as buildingNo, COUNT(*) as count FROM dim_fire_equipment WHERE deleted = 0 GROUP BY building_no ORDER BY building_no")
    List<Map<String, Object>> selectCountGroupByBuilding();

    /**
     * 查询即将到期的设备(N天内)
     */
    default List<FireEquipmentDO> selectExpiringEquipments(int days) {
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusDays(days);
        return selectList(new LambdaQueryWrapperX<FireEquipmentDO>()
                .ge(FireEquipmentDO::getExpiryDate, today)
                .le(FireEquipmentDO::getExpiryDate, endDate)
                .orderByAsc(FireEquipmentDO::getExpiryDate));
    }

    /**
     * 查询需巡检的设备(N天内)
     */
    default List<FireEquipmentDO> selectNeedCheckEquipments(int days) {
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusDays(days);
        return selectList(new LambdaQueryWrapperX<FireEquipmentDO>()
                .ge(FireEquipmentDO::getNextCheckDate, today)
                .le(FireEquipmentDO::getNextCheckDate, endDate)
                .orderByAsc(FireEquipmentDO::getNextCheckDate));
    }

    /**
     * 统计指定楼号的设备数量(按状态)
     */
    @Select("SELECT status, COUNT(*) as count FROM dim_fire_equipment WHERE deleted = 0 AND building_no = #{buildingNo} GROUP BY status")
    List<Map<String, Object>> selectCountByBuildingGroupByStatus(@Param("buildingNo") Integer buildingNo);

}
