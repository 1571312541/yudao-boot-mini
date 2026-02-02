package cn.iocoder.yudao.module.dim.dal.dataobject.visitor;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 访客卡关联 DO
 */
@TableName("dim_visitor_card")
@KeySequence("dim_visitor_card_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class VisitorCardDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 访客ID
     */
    private Long visitorId;
    /**
     * 卡号
     */
    private String cardId;
    /**
     * 二维码
     */
    private String qrCode;
    /**
     * 区域ID
     */
    private Long areaId;

}
