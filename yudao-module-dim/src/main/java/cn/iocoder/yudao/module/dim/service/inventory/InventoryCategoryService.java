package cn.iocoder.yudao.module.dim.service.inventory;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.dim.controller.admin.inventory.vo.InventoryCategoryPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.inventory.vo.InventoryCategorySaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.inventory.InventoryCategoryDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 库存分类 Service 接口
 */
public interface InventoryCategoryService {

    /**
     * 创建分类
     */
    Long createCategory(@Valid InventoryCategorySaveReqVO createReqVO);

    /**
     * 更新分类
     */
    void updateCategory(@Valid InventoryCategorySaveReqVO updateReqVO);

    /**
     * 删除分类
     */
    void deleteCategory(Long id);

    /**
     * 获得分类
     */
    InventoryCategoryDO getCategory(Long id);

    /**
     * 获得分类分页
     */
    PageResult<InventoryCategoryDO> getCategoryPage(InventoryCategoryPageReqVO pageReqVO);

    /**
     * 获得分类列表
     */
    List<InventoryCategoryDO> getCategoryList(InventoryCategoryPageReqVO reqVO);

    /**
     * 根据类型获得分类列表
     */
    List<InventoryCategoryDO> getCategoryListByType(Integer type);

}
