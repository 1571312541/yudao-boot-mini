package cn.iocoder.yudao.module.dim.service.dining;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.DiningRecordPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.DiningRecordSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.dining.DiningRecordDO;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

/**
 * 就餐记录 Service 接口
 */
public interface DiningRecordService {

    /**
     * 创建就餐记录
     */
    Long createDiningRecord(@Valid DiningRecordSaveReqVO createReqVO);

    /**
     * 更新就餐记录
     */
    void updateDiningRecord(@Valid DiningRecordSaveReqVO updateReqVO);

    /**
     * 删除就餐记录
     */
    void deleteDiningRecord(Long id);

    /**
     * 获得就餐记录
     */
    DiningRecordDO getDiningRecord(Long id);

    /**
     * 获得就餐记录分页
     */
    PageResult<DiningRecordDO> getDiningRecordPage(DiningRecordPageReqVO pageReqVO);

    /**
     * 根据用户ID和日期范围获得就餐记录
     */
    List<DiningRecordDO> getDiningRecordListByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate);

    /**
     * 刷卡消费
     *
     * @param cardNo 餐卡号
     * @param mealType 餐类（可选，为空时根据当前时间自动判断）
     * @return 状态码: 0=成功, -1=卡不可用, -2=已刷卡, -3=未报餐
     */
    int swipeCard(String cardNo, Integer mealType);

}
