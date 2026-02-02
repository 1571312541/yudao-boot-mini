package cn.iocoder.yudao.module.dim.dal.mysql.visitor;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.dim.controller.admin.visitor.vo.VisitLogPageReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.visitor.VisitLogDO;
import org.apache.ibatis.annotations.Mapper;

import cn.iocoder.yudao.module.dim.controller.admin.visitor.vo.VisitorMonthStatisticsVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface VisitLogMapper extends BaseMapperX<VisitLogDO> {

    /**
     * 统计指定年份每月的访客数量
     *
     * @param year 年份
     * @return 每月统计列表
     */
    @Select("SELECT MONTH(visit_date) AS month, COUNT(*) AS total " +
            "FROM dim_visit_log " +
            "WHERE YEAR(visit_date) = #{year} AND deleted = 0 " +
            "GROUP BY MONTH(visit_date) " +
            "ORDER BY month")
    List<VisitorMonthStatisticsVO> countByYear(@Param("year") Integer year);

    default PageResult<VisitLogDO> selectPage(VisitLogPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<VisitLogDO>()
                .eqIfPresent(VisitLogDO::getVisitorId, reqVO.getVisitorId())
                .eqIfPresent(VisitLogDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(VisitLogDO::getVisitDate, reqVO.getVisitDate())
                .orderByDesc(VisitLogDO::getId));
    }

    default List<VisitLogDO> selectListByVisitorId(Long visitorId) {
        return selectList(VisitLogDO::getVisitorId, visitorId);
    }

}
