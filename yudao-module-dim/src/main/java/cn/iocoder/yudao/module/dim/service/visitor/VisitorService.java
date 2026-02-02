package cn.iocoder.yudao.module.dim.service.visitor;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.dim.controller.admin.visitor.vo.VisitorImportVO;
import cn.iocoder.yudao.module.dim.controller.admin.visitor.vo.VisitorPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.visitor.vo.VisitorSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.visitor.VisitorDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 访客 Service 接口
 */
public interface VisitorService {

    /**
     * 创建访客
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createVisitor(@Valid VisitorSaveReqVO createReqVO);

    /**
     * 更新访客
     *
     * @param updateReqVO 更新信息
     */
    void updateVisitor(@Valid VisitorSaveReqVO updateReqVO);

    /**
     * 删除访客
     *
     * @param id 编号
     */
    void deleteVisitor(Long id);

    /**
     * 获得访客
     *
     * @param id 编号
     * @return 访客
     */
    VisitorDO getVisitor(Long id);

    /**
     * 获得访客分页
     *
     * @param pageReqVO 分页查询
     * @return 访客分页
     */
    PageResult<VisitorDO> getVisitorPage(VisitorPageReqVO pageReqVO);

    /**
     * 访客离场
     *
     * @param id 访客编号
     */
    void departure(Long id);

    /**
     * 批量导入访客
     *
     * @param importList 导入列表
     * @param updateSupport 是否更新已存在的访客
     * @return 导入结果
     */
    VisitorImportRespVO importVisitors(List<VisitorImportVO> importList, boolean updateSupport);

    /**
     * 更新访客人脸信息
     *
     * @param visitorId 访客ID
     * @param imgInfo 人脸图片信息
     */
    void updateVisitorImgInfo(Long visitorId, String imgInfo);

    /**
     * 导入结果 VO
     */
    class VisitorImportRespVO {
        public int createCount;
        public int updateCount;
        public List<String> failMessages;
    }

}
