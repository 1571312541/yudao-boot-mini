package cn.iocoder.yudao.module.dim.dal.mysql.dining;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.DiningPageReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.dining.DiningDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DiningMapper extends BaseMapperX<DiningDO> {

    default PageResult<DiningDO> selectPage(DiningPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DiningDO>()
                .likeIfPresent(DiningDO::getName, reqVO.getName())
                .eqIfPresent(DiningDO::getMealType, reqVO.getMealType())
                .eqIfPresent(DiningDO::getStatus, reqVO.getStatus())
                .orderByAsc(DiningDO::getMealType)
                .orderByDesc(DiningDO::getId));
    }

    default List<DiningDO> selectListByStatus(Integer status) {
        return selectList(new LambdaQueryWrapperX<DiningDO>()
                .eqIfPresent(DiningDO::getStatus, status)
                .orderByAsc(DiningDO::getMealType));
    }

}
