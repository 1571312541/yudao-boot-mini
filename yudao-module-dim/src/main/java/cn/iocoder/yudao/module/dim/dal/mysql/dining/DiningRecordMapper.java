package cn.iocoder.yudao.module.dim.dal.mysql.dining;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.DiningRecordPageReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.dining.DiningRecordDO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface DiningRecordMapper extends BaseMapperX<DiningRecordDO> {

    default PageResult<DiningRecordDO> selectPage(DiningRecordPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DiningRecordDO>()
                .eqIfPresent(DiningRecordDO::getUserId, reqVO.getUserId())
                .likeIfPresent(DiningRecordDO::getUserName, reqVO.getUserName())
                .eqIfPresent(DiningRecordDO::getMealType, reqVO.getMealType())
                .betweenIfPresent(DiningRecordDO::getDiningDate, reqVO.getDiningDate())
                .eqIfPresent(DiningRecordDO::getPersonType, reqVO.getPersonType())
                .likeIfPresent(DiningRecordDO::getCardId, reqVO.getCardId())
                .eqIfPresent(DiningRecordDO::getDiningClass, reqVO.getDiningClass())
                .eqIfPresent(DiningRecordDO::getSettlementId, reqVO.getSettlementId())
                .orderByDesc(DiningRecordDO::getId));
    }

    default List<DiningRecordDO> selectListByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        return selectList(new LambdaQueryWrapperX<DiningRecordDO>()
                .eq(DiningRecordDO::getUserId, userId)
                .ge(DiningRecordDO::getDiningDate, startDate)
                .le(DiningRecordDO::getDiningDate, endDate));
    }

    /**
     * 根据餐卡号、日期、餐别查询就餐记录
     */
    default List<DiningRecordDO> selectListByCardIdAndDateAndMealType(String cardId, LocalDate date, Integer mealType) {
        return selectList(new LambdaQueryWrapperX<DiningRecordDO>()
                .eq(DiningRecordDO::getCardId, cardId)
                .eq(DiningRecordDO::getDiningDate, date)
                .eqIfPresent(DiningRecordDO::getMealType, mealType));
    }

    /**
     * 根据用户姓名、部门、日期范围、是否已就餐查询就餐记录
     */
    default List<DiningRecordDO> selectListByCondition(String userName, String deptName, LocalDate startDate, LocalDate endDate) {
        return selectList(new LambdaQueryWrapperX<DiningRecordDO>()
                .likeIfPresent(DiningRecordDO::getUserName, userName)
                .likeIfPresent(DiningRecordDO::getDeptName, deptName)
                .geIfPresent(DiningRecordDO::getDiningDate, startDate)
                .leIfPresent(DiningRecordDO::getDiningDate, endDate));
    }

    /**
     * 根据日期范围和餐别查询就餐记录（用于统计）
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @param mealType  餐别（可选）
     * @return 就餐记录列表
     */
    default List<DiningRecordDO> selectListByDateRangeAndMealType(LocalDate startDate, LocalDate endDate, Integer mealType) {
        return selectList(new LambdaQueryWrapperX<DiningRecordDO>()
                .eqIfPresent(DiningRecordDO::getMealType, mealType)
                .ge(DiningRecordDO::getDiningDate, startDate)
                .le(DiningRecordDO::getDiningDate, endDate)
        );
    }

    /**
     * 按人员类型统计就餐数据
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @param mealType  餐别（可选）
     * @return 按人员类型分组的就餐记录
     */
    default List<DiningRecordDO> selectListForStatsByPersonType(LocalDate startDate, LocalDate endDate, Integer mealType) {
        return selectList(new LambdaQueryWrapperX<DiningRecordDO>()
                .eqIfPresent(DiningRecordDO::getMealType, mealType)
                .ge(DiningRecordDO::getDiningDate, startDate)
                .le(DiningRecordDO::getDiningDate, endDate)
                .orderByAsc(DiningRecordDO::getPersonType));
    }

    /**
     * 按单位统计就餐数据
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 按单位分组的就餐记录
     */
    default List<DiningRecordDO> selectListForStatsByDept(LocalDate startDate, LocalDate endDate) {
        return selectList(new LambdaQueryWrapperX<DiningRecordDO>()
                .ge(DiningRecordDO::getDiningDate, startDate)
                .le(DiningRecordDO::getDiningDate, endDate)
                .orderByAsc(DiningRecordDO::getDeptName));
    }

}
