package cn.iocoder.yudao.module.dim.dal.mysql.fire;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.dim.dal.dataobject.fire.FireEquipmentCheckLogDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FireEquipmentCheckLogMapper extends BaseMapperX<FireEquipmentCheckLogDO> {

    /**
     * 按设备ID分页查询巡检历史
     */
    default PageResult<FireEquipmentCheckLogDO> selectPageByEquipmentId(Long equipmentId, PageParam pageParam) {
        return selectPage(pageParam, new LambdaQueryWrapperX<FireEquipmentCheckLogDO>()
                .eq(FireEquipmentCheckLogDO::getEquipmentId, equipmentId)
                .orderByDesc(FireEquipmentCheckLogDO::getCheckTime));
    }

    /**
     * 查询设备最近一次巡检记录
     */
    default FireEquipmentCheckLogDO selectLatestByEquipmentId(Long equipmentId) {
        return selectOne(new LambdaQueryWrapperX<FireEquipmentCheckLogDO>()
                .eq(FireEquipmentCheckLogDO::getEquipmentId, equipmentId)
                .orderByDesc(FireEquipmentCheckLogDO::getCheckTime)
                .last("LIMIT 1"));
    }

}
