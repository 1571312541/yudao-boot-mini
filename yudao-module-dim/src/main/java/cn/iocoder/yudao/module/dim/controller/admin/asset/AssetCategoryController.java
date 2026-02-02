package cn.iocoder.yudao.module.dim.controller.admin.asset;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.AssetCategoryRespVO;
import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.AssetCategorySaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.asset.AssetCategoryDO;
import cn.iocoder.yudao.module.dim.service.asset.AssetCategoryService;
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

@Tag(name = "管理后台 - 资产分类")
@RestController
@RequestMapping("/dim/asset-category")
@Validated
public class AssetCategoryController {

    @Resource
    private AssetCategoryService assetCategoryService;

    @PostMapping("/create")
    @Operation(summary = "创建资产分类")
    @PreAuthorize("@ss.hasPermission('dim:asset-category:create')")
    public CommonResult<Long> createAssetCategory(@Valid @RequestBody AssetCategorySaveReqVO createReqVO) {
        return success(assetCategoryService.createAssetCategory(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新资产分类")
    @PreAuthorize("@ss.hasPermission('dim:asset-category:update')")
    public CommonResult<Boolean> updateAssetCategory(@Valid @RequestBody AssetCategorySaveReqVO updateReqVO) {
        assetCategoryService.updateAssetCategory(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除资产分类")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('dim:asset-category:delete')")
    public CommonResult<Boolean> deleteAssetCategory(@RequestParam("id") Long id) {
        assetCategoryService.deleteAssetCategory(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得资产分类")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('dim:asset-category:query')")
    public CommonResult<AssetCategoryRespVO> getAssetCategory(@RequestParam("id") Long id) {
        AssetCategoryDO category = assetCategoryService.getAssetCategory(id);
        return success(BeanUtils.toBean(category, AssetCategoryRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得资产分类列表")
    @PreAuthorize("@ss.hasPermission('dim:asset-category:query')")
    public CommonResult<List<AssetCategoryRespVO>> getAssetCategoryList() {
        List<AssetCategoryDO> list = assetCategoryService.getAssetCategoryList();
        return success(BeanUtils.toBean(list, AssetCategoryRespVO.class));
    }

    @GetMapping("/list-by-parent")
    @Operation(summary = "根据父级ID获得子分类列表")
    @Parameter(name = "parentId", description = "父级ID", required = true)
    @PreAuthorize("@ss.hasPermission('dim:asset-category:query')")
    public CommonResult<List<AssetCategoryRespVO>> getAssetCategoryListByParentId(
            @RequestParam("parentId") Long parentId) {
        List<AssetCategoryDO> list = assetCategoryService.getAssetCategoryListByParentId(parentId);
        return success(BeanUtils.toBean(list, AssetCategoryRespVO.class));
    }

}
