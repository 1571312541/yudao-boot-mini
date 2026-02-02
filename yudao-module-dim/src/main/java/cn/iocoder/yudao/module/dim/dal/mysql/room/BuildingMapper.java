package cn.iocoder.yudao.module.dim.dal.mysql.room;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.BuildingPageReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.room.BuildingDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BuildingMapper extends BaseMapperX<BuildingDO> {

    default PageResult<BuildingDO> selectPage(BuildingPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<BuildingDO>()
                .likeIfPresent(BuildingDO::getName, reqVO.getName())
                .eqIfPresent(BuildingDO::getStatus, reqVO.getStatus())
                .orderByAsc(BuildingDO::getSort)
                .orderByDesc(BuildingDO::getId));
    }

    default List<BuildingDO> selectList(String name, Integer status) {
        return selectList(new LambdaQueryWrapperX<BuildingDO>()
                .likeIfPresent(BuildingDO::getName, name)
                .eqIfPresent(BuildingDO::getStatus, status)
                .orderByAsc(BuildingDO::getSort));
    }

    default BuildingDO selectByCode(String code) {
        return selectOne(BuildingDO::getCode, code);
    }

    default BuildingDO selectByName(String name) {
        return selectOne(BuildingDO::getName, name);
    }

}
