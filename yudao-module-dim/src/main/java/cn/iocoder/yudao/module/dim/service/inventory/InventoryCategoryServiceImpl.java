package cn.iocoder.yudao.module.dim.service.inventory;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.dim.controller.admin.inventory.vo.InventoryCategoryPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.inventory.vo.InventoryCategorySaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.inventory.InventoryCategoryDO;
import cn.iocoder.yudao.module.dim.dal.mysql.inventory.InventoryCategoryMapper;
import cn.iocoder.yudao.module.dim.dal.mysql.inventory.InventoryItemMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.dim.enums.ErrorCodeConstants.*;

/**
 * 库存分类 Service 实现类
 */
@Service
@Validated
public class InventoryCategoryServiceImpl implements InventoryCategoryService {

    @Resource
    private InventoryCategoryMapper inventoryCategoryMapper;

    @Resource
    private InventoryItemMapper inventoryItemMapper;

    @Override
    public Long createCategory(InventoryCategorySaveReqVO createReqVO) {
        // 校验父分类存在
        validateParentCategory(createReqVO.getParentId());
        // 插入
        InventoryCategoryDO category = BeanUtils.toBean(createReqVO, InventoryCategoryDO.class);
        if (category.getSort() == null) {
            category.setSort(0);
        }
        if (category.getStatus() == null) {
            category.setStatus(0);
        }
        inventoryCategoryMapper.insert(category);
        return category.getId();
    }

    @Override
    public void updateCategory(InventoryCategorySaveReqVO updateReqVO) {
        // 校验存在
        validateCategoryExists(updateReqVO.getId());
        // 校验父分类存在
        validateParentCategory(updateReqVO.getParentId());
        // 更新
        InventoryCategoryDO updateObj = BeanUtils.toBean(updateReqVO, InventoryCategoryDO.class);
        inventoryCategoryMapper.updateById(updateObj);
    }

    @Override
    public void deleteCategory(Long id) {
        // 校验存在
        validateCategoryExists(id);
        // 校验是否有子分类
        List<InventoryCategoryDO> children = inventoryCategoryMapper.selectListByParentId(id);
        if (!children.isEmpty()) {
            throw exception(INVENTORY_CATEGORY_HAS_CHILDREN);
        }
        // 校验是否有物品使用此分类
        if (!inventoryItemMapper.selectListByCategoryId(id).isEmpty()) {
            throw exception(INVENTORY_CATEGORY_HAS_ITEMS);
        }
        // 删除
        inventoryCategoryMapper.deleteById(id);
    }

    private void validateCategoryExists(Long id) {
        if (inventoryCategoryMapper.selectById(id) == null) {
            throw exception(INVENTORY_CATEGORY_NOT_EXISTS);
        }
    }

    private void validateParentCategory(Long parentId) {
        if (parentId != null && parentId > 0) {
            if (inventoryCategoryMapper.selectById(parentId) == null) {
                throw exception(INVENTORY_CATEGORY_PARENT_NOT_EXISTS);
            }
        }
    }

    @Override
    public InventoryCategoryDO getCategory(Long id) {
        return inventoryCategoryMapper.selectById(id);
    }

    @Override
    public PageResult<InventoryCategoryDO> getCategoryPage(InventoryCategoryPageReqVO pageReqVO) {
        return inventoryCategoryMapper.selectPage(pageReqVO);
    }

    @Override
    public List<InventoryCategoryDO> getCategoryList(InventoryCategoryPageReqVO reqVO) {
        return inventoryCategoryMapper.selectList(reqVO);
    }

    @Override
    public List<InventoryCategoryDO> getCategoryListByType(Integer type) {
        return inventoryCategoryMapper.selectListByType(type);
    }

}
