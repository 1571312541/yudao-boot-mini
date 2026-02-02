package cn.iocoder.yudao.module.dim.dal.mysql.visitor;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.dim.controller.admin.visitor.vo.VisitorPageReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.visitor.VisitorDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VisitorMapper extends BaseMapperX<VisitorDO> {

    default PageResult<VisitorDO> selectPage(VisitorPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<VisitorDO>()
                .likeIfPresent(VisitorDO::getName, reqVO.getName())
                .likeIfPresent(VisitorDO::getPhone, reqVO.getPhone())
                .likeIfPresent(VisitorDO::getIdNum, reqVO.getIdNum())
                .eqIfPresent(VisitorDO::getType, reqVO.getType())
                .likeIfPresent(VisitorDO::getUnitName, reqVO.getUnitName())
                .betweenIfPresent(VisitorDO::getRegDate, reqVO.getRegDate())
                .orderByDesc(VisitorDO::getId));
    }

    default VisitorDO selectByIdNum(String idNum) {
        return selectOne(VisitorDO::getIdNum, idNum);
    }

    default VisitorDO selectByPhone(String phone) {
        return selectOne(VisitorDO::getPhone, phone);
    }

    default VisitorDO selectByDiningNum(String diningNum) {
        return selectOne(VisitorDO::getDiningNum, diningNum);
    }

}
