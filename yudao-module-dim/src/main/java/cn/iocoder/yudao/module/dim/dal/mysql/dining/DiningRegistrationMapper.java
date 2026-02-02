package cn.iocoder.yudao.module.dim.dal.mysql.dining;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.DiningRegistrationPageReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.dining.DiningRegistrationDO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface DiningRegistrationMapper extends BaseMapperX<DiningRegistrationDO> {

    default PageResult<DiningRegistrationDO> selectPage(DiningRegistrationPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DiningRegistrationDO>()
                .eqIfPresent(DiningRegistrationDO::getUserId, reqVO.getUserId())
                .likeIfPresent(DiningRegistrationDO::getUserName, reqVO.getUserName())
                .eqIfPresent(DiningRegistrationDO::getMealType, reqVO.getMealType())
                .eqIfPresent(DiningRegistrationDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(DiningRegistrationDO::getRegistrationDate, reqVO.getRegistrationDate())
                .likeIfPresent(DiningRegistrationDO::getCardId, reqVO.getCardId())
                .eqIfPresent(DiningRegistrationDO::getUsed, reqVO.getUsed())
                .eqIfPresent(DiningRegistrationDO::getDiningClass, reqVO.getDiningClass())
                .eqIfPresent(DiningRegistrationDO::getSettlementId, reqVO.getSettlementId())
                .eqIfPresent(DiningRegistrationDO::getIsPaid, reqVO.getIsPaid())
                .eqIfPresent(DiningRegistrationDO::getIsInvoiced, reqVO.getIsInvoiced())
                .likeIfPresent(DiningRegistrationDO::getReceptionist, reqVO.getReceptionist())
                .orderByDesc(DiningRegistrationDO::getId));
    }

    default List<DiningRegistrationDO> selectListByDateAndMealType(LocalDate date, Integer mealType) {
        return selectList(new LambdaQueryWrapperX<DiningRegistrationDO>()
                .eq(DiningRegistrationDO::getRegistrationDate, date)
                .eqIfPresent(DiningRegistrationDO::getMealType, mealType));
    }

    /**
     * 根据餐卡号、日期、餐别查询报餐登记
     */
    default List<DiningRegistrationDO> selectListByCardIdAndDateAndMealType(String cardId, LocalDate date, Integer mealType) {
        return selectList(new LambdaQueryWrapperX<DiningRegistrationDO>()
                .eq(DiningRegistrationDO::getCardId, cardId)
                .eq(DiningRegistrationDO::getRegistrationDate, date)
                .eqIfPresent(DiningRegistrationDO::getMealType, mealType));
    }

    /**
     * 根据用户姓名、部门、日期范围、是否已就餐查询报餐登记
     */
    default List<DiningRegistrationDO> selectListByConditionForSettlement(String userName, String deptName,
            LocalDate startDate, LocalDate endDate, Integer used) {
        return selectList(new LambdaQueryWrapperX<DiningRegistrationDO>()
                .likeIfPresent(DiningRegistrationDO::getUserName, userName)
                .likeIfPresent(DiningRegistrationDO::getDeptName, deptName)
                .geIfPresent(DiningRegistrationDO::getRegistrationDate, startDate)
                .leIfPresent(DiningRegistrationDO::getRegistrationDate, endDate)
                .eqIfPresent(DiningRegistrationDO::getUsed, used)
                .isNull(DiningRegistrationDO::getSettlementId)); // 未关联结算单的记录
    }

    /**
     * 根据结算单ID查询报餐登记列表
     */
    default List<DiningRegistrationDO> selectListBySettlementId(Long settlementId) {
        return selectList(DiningRegistrationDO::getSettlementId, settlementId);
    }

    /**
     * 查询费用统计数据
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @param deptName  单位名称（可选）
     * @param userName  用户名称（可选）
     * @return 报餐登记列表
     */
    default List<DiningRegistrationDO> selectListForExpenseStats(LocalDate startDate, LocalDate endDate,
            String deptName, String userName) {
        return selectList(new LambdaQueryWrapperX<DiningRegistrationDO>()
                .geIfPresent(DiningRegistrationDO::getRegistrationDate, startDate)
                .leIfPresent(DiningRegistrationDO::getRegistrationDate, endDate)
                .likeIfPresent(DiningRegistrationDO::getDeptName, deptName)
                .likeIfPresent(DiningRegistrationDO::getUserName, userName)
                .orderByAsc(DiningRegistrationDO::getUserName)
                .orderByAsc(DiningRegistrationDO::getDeptName));
    }

    /**
     * 根据用户ID或餐卡号查询未结算的报餐登记
     *
     * @param userId 用户ID（可选）
     * @param cardId 餐卡号（可选）
     * @return 未结算的报餐登记列表
     */
    default List<DiningRegistrationDO> selectListByUserIdOrCardIdUnsettled(Long userId, String cardId) {
        return selectList(new LambdaQueryWrapperX<DiningRegistrationDO>()
                .and(userId != null || cardId != null, wrapper -> {
                    if (userId != null) {
                        wrapper.eq(DiningRegistrationDO::getUserId, userId);
                    }
                    if (userId != null && cardId != null) {
                        wrapper.or();
                    }
                    if (cardId != null) {
                        wrapper.eq(DiningRegistrationDO::getCardId, cardId);
                    }
                })
                .eq(DiningRegistrationDO::getUsed, 1) // 已就餐
                .isNull(DiningRegistrationDO::getSettlementId)); // 未结算
    }

}
