package cn.iocoder.yudao.module.dim.dal.mysql.room;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.StaySettlementPageReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.room.StaySettlementDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface StaySettlementMapper extends BaseMapperX<StaySettlementDO> {

    default PageResult<StaySettlementDO> selectPage(StaySettlementPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<StaySettlementDO>()
                .eqIfPresent(StaySettlementDO::getGuestId, reqVO.getGuestId())
                .eqIfPresent(StaySettlementDO::getRoomId, reqVO.getRoomId())
                .eqIfPresent(StaySettlementDO::getPaymentStatus, reqVO.getPaymentStatus())
                .eqIfPresent(StaySettlementDO::getInvoiceStatus, reqVO.getInvoiceStatus())
                .betweenIfPresent(StaySettlementDO::getCheckOutDate, reqVO.getCheckOutDate())
                .orderByDesc(StaySettlementDO::getId));
    }

    default StaySettlementDO selectByGuestId(Long guestId) {
        return selectOne(StaySettlementDO::getGuestId, guestId);
    }

    /**
     * 统计总金额
     */
    @Select("SELECT COALESCE(SUM(total_amount), 0) FROM dim_stay_settlement WHERE deleted = 0")
    BigDecimal selectTotalAmount();

    /**
     * 统计已结算金额
     */
    @Select("SELECT COALESCE(SUM(total_amount), 0) FROM dim_stay_settlement WHERE deleted = 0 AND payment_status = 1")
    BigDecimal selectSettledAmount();

    /**
     * 统计未结算金额
     */
    @Select("SELECT COALESCE(SUM(total_amount), 0) FROM dim_stay_settlement WHERE deleted = 0 AND payment_status = 0")
    BigDecimal selectUnsettledAmount();

    /**
     * 按时间范围统计金额
     */
    @Select("SELECT COALESCE(SUM(total_amount), 0) FROM dim_stay_settlement " +
            "WHERE deleted = 0 AND check_out_date >= #{startTime} AND check_out_date <= #{endTime}")
    BigDecimal selectTotalAmountByTimeRange(@Param("startTime") LocalDateTime startTime,
                                            @Param("endTime") LocalDateTime endTime);

    /**
     * 按时间范围统计已结算金额
     */
    @Select("SELECT COALESCE(SUM(total_amount), 0) FROM dim_stay_settlement " +
            "WHERE deleted = 0 AND payment_status = 1 AND check_out_date >= #{startTime} AND check_out_date <= #{endTime}")
    BigDecimal selectSettledAmountByTimeRange(@Param("startTime") LocalDateTime startTime,
                                               @Param("endTime") LocalDateTime endTime);

}
