package cn.iocoder.yudao.module.dim.dal.mysql.visitor;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.dim.dal.dataobject.visitor.VisitorCardDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VisitorCardMapper extends BaseMapperX<VisitorCardDO> {

    default VisitorCardDO selectByVisitorId(Long visitorId) {
        return selectOne(VisitorCardDO::getVisitorId, visitorId);
    }

    default VisitorCardDO selectByCardId(String cardId) {
        return selectOne(new LambdaQueryWrapperX<VisitorCardDO>()
                .eq(VisitorCardDO::getCardId, cardId));
    }

    default int countByVisitorId(Long visitorId) {
        return selectCount(VisitorCardDO::getVisitorId, visitorId).intValue();
    }

}
