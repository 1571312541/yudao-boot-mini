package cn.iocoder.yudao.module.dim.dal.dataobject.asset;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 资产外借 DO
 */
@TableName("dim_asset_borrow")
@KeySequence("dim_asset_borrow_seq")
@Data
@EqualsAndHashCode(callSuper = true)
public class AssetBorrowDO extends BaseDO {

    @TableId
    private Long id;
    /**
     * 资产ID
     */
    private Long assetId;
    /**
     * 外借数量
     */
    private Integer quantity;
    /**
     * 借用人ID
     */
    private Long borrowerId;
    /**
     * 借用人姓名
     */
    private String borrowerName;
    /**
     * 借出日期
     */
    private LocalDateTime borrowDate;
    /**
     * 预计归还日期
     */
    private LocalDateTime expectedReturnDate;
    /**
     * 实际归还日期
     */
    private LocalDateTime returnDate;
    /**
     * 状态：0待审核,1已借出,2已归还
     */
    private Integer status;
    /**
     * 备注
     */
    private String remarks;

}
