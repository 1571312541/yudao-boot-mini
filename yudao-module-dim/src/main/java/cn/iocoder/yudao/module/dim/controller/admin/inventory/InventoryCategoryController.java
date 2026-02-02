package cn.iocoder.yudao.module.dim.controller.admin.inventory;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.dim.controller.admin.inventory.vo.InventoryCategoryPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.inventory.vo.InventoryCategoryRespVO;
import cn.iocoder.yudao.module.dim.controller.admin.inventory.vo.InventoryCategorySaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.inventory.InventoryCategoryDO;
import cn.iocoder.yudao.module.dim.service.inventory.InventoryCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 库存分类 Controller
 */
@Tag(name = "管理后台 - 库存分类")
@RestController
@RequestMapping("/dim/inventory/category")
@Validated
public class InventoryCategoryController {

    @Resource
    private InventoryCategoryService inventoryCategoryService;

    @PostMapping("/create")
    @Operation(summary = "创建库存分类")
    @PreAuthorize("@ss.hasPermission('dim:inventory:category:create')")
    public CommonResult<Long> createCategory(@Valid @RequestBody InventoryCategorySaveReqVO createReqVO) {
        return success(inventoryCategoryService.createCategory(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新库存分类")
    @PreAuthorize("@ss.hasPermission('dim:inventory:category:update')")
    public CommonResult<Boolean> updateCategory(@Valid @RequestBody InventoryCategorySaveReqVO updateReqVO) {
        inventoryCategoryService.updateCategory(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除库存分类")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('dim:inventory:category:delete')")
    public CommonResult<Boolean> deleteCategory(@RequestParam("id") Long id) {
        inventoryCategoryService.deleteCategory(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得库存分类")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('dim:inventory:category:query')")
    public CommonResult<InventoryCategoryRespVO> getCategory(@RequestParam("id") Long id) {
        InventoryCategoryDO category = inventoryCategoryService.getCategory(id);
        return success(BeanUtils.toBean(category, InventoryCategoryRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得库存分类分页")
    @PreAuthorize("@ss.hasPermission('dim:inventory:category:query')")
    public CommonResult<PageResult<InventoryCategoryRespVO>> getCategoryPage(@Valid InventoryCategoryPageReqVO pageReqVO) {
        PageResult<InventoryCategoryDO> pageResult = inventoryCategoryService.getCategoryPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, InventoryCategoryRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得库存分类列表")
    @PreAuthorize("@ss.hasPermission('dim:inventory:category:query')")
    public CommonResult<List<InventoryCategoryRespVO>> getCategoryList(@Valid InventoryCategoryPageReqVO reqVO) {
        List<InventoryCategoryDO> list = inventoryCategoryService.getCategoryList(reqVO);
        return success(BeanUtils.toBean(list, InventoryCategoryRespVO.class));
    }

    @GetMapping("/list-by-type")
    @Operation(summary = "根据类型获得分类列表")
    @Parameter(name = "type", description = "类型：1-物资 2-耗材", required = true)
    @PreAuthorize("@ss.hasPermission('dim:inventory:category:query')")
    public CommonResult<List<InventoryCategoryRespVO>> getCategoryListByType(@RequestParam("type") Integer type) {
        List<InventoryCategoryDO> list = inventoryCategoryService.getCategoryListByType(type);
        return success(BeanUtils.toBean(list, InventoryCategoryRespVO.class));
    }

}
