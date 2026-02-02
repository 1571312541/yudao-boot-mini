package cn.iocoder.yudao.module.dim.service.visitor;

import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.dim.controller.admin.visitor.vo.VisitorCardBindReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.visitor.VisitorCardDO;
import cn.iocoder.yudao.module.dim.dal.mysql.visitor.VisitorCardMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * 访客卡 Service 实现类
 */
@Service
@Validated
public class VisitorCardServiceImpl implements VisitorCardService {

    @Resource
    private VisitorCardMapper visitorCardMapper;

    @Override
    public Long bindCard(VisitorCardBindReqVO bindReqVO) {
        // 查询是否已有绑定
        VisitorCardDO existingCard = visitorCardMapper.selectByVisitorId(bindReqVO.getVisitorId());

        if (existingCard != null) {
            // 更新
            existingCard.setCardId(bindReqVO.getCardId());
            existingCard.setQrCode(bindReqVO.getQrCode());
            existingCard.setAreaId(bindReqVO.getAreaId());
            visitorCardMapper.updateById(existingCard);
            return existingCard.getId();
        } else {
            // 新增
            VisitorCardDO cardDO = BeanUtils.toBean(bindReqVO, VisitorCardDO.class);
            visitorCardMapper.insert(cardDO);
            return cardDO.getId();
        }
    }

    @Override
    public VisitorCardDO getCardByVisitorId(Long visitorId) {
        return visitorCardMapper.selectByVisitorId(visitorId);
    }

    @Override
    public VisitorCardDO getCardByCardId(String cardId) {
        return visitorCardMapper.selectByCardId(cardId);
    }

    @Override
    public void unbindCard(Long visitorId) {
        VisitorCardDO card = visitorCardMapper.selectByVisitorId(visitorId);
        if (card != null) {
            visitorCardMapper.deleteById(card.getId());
        }
    }

}
