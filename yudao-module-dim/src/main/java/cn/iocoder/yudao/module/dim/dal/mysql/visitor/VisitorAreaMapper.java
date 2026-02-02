package cn.iocoder.yudao.module.dim.dal.mysql.visitor;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.dim.controller.admin.visitor.vo.VisitorAreaPageReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.visitor.VisitorAreaDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VisitorAreaMapper extends BaseMapperX<VisitorAreaDO> {

    default PageResult<VisitorAreaDO> selectPage(VisitorAreaPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<VisitorAreaDO>()
                .likeIfPresent(VisitorAreaDO::getAreaName, reqVO.getAreaName())
                .likeIfPresent(VisitorAreaDO::getAreaIp, reqVO.getAreaIp())
                .orderByDesc(VisitorAreaDO::getId));
    }

    default List<VisitorAreaDO> selectList() {
        return selectList(new LambdaQueryWrapperX<VisitorAreaDO>()
                .orderByAsc(VisitorAreaDO::getAreaName));
    }

}
