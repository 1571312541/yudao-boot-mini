package cn.iocoder.yudao.module.dim.service.visitor;

import cn.iocoder.yudao.module.dim.controller.admin.visitor.vo.VisitorCardBindReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.visitor.VisitorCardDO;

import javax.validation.Valid;

/**
 * 访客卡 Service 接口
 */
public interface VisitorCardService {

    /**
     * 绑定/更新访客卡
     *
     * @param bindReqVO 绑定信息
     * @return 卡关联ID
     */
    Long bindCard(@Valid VisitorCardBindReqVO bindReqVO);

    /**
     * 获取访客卡信息
     *
     * @param visitorId 访客ID
     * @return 卡信息
     */
    VisitorCardDO getCardByVisitorId(Long visitorId);

    /**
     * 根据卡号获取访客卡信息
     *
     * @param cardId 卡号
     * @return 卡信息
     */
    VisitorCardDO getCardByCardId(String cardId);

    /**
     * 删除访客卡绑定
     *
     * @param visitorId 访客ID
     */
    void unbindCard(Long visitorId);

}
