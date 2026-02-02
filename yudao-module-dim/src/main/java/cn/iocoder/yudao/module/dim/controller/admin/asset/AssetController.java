package cn.iocoder.yudao.module.dim.controller.admin.asset;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.AssetPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.AssetRespVO;
import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.AssetSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.asset.AssetDO;
import cn.iocoder.yudao.module.dim.service.asset.AssetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 资产")
@RestController
@RequestMapping("/dim/asset")
@Validated
public class AssetController {

    @Resource
    private AssetService assetService;

    @PostMapping("/create")
    @Operation(summary = "创建资产")
    @PreAuthorize("@ss.hasPermission('dim:asset:create')")
    public CommonResult<Long> createAsset(@Valid @RequestBody AssetSaveReqVO createReqVO) {
        return success(assetService.createAsset(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新资产")
    @PreAuthorize("@ss.hasPermission('dim:asset:update')")
    public CommonResult<Boolean> updateAsset(@Valid @RequestBody AssetSaveReqVO updateReqVO) {
        assetService.updateAsset(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除资产")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('dim:asset:delete')")
    public CommonResult<Boolean> deleteAsset(@RequestParam("id") Long id) {
        assetService.deleteAsset(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得资产")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('dim:asset:query')")
    public CommonResult<AssetRespVO> getAsset(@RequestParam("id") Long id) {
        AssetDO asset = assetService.getAsset(id);
        return success(BeanUtils.toBean(asset, AssetRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得资产分页")
    @PreAuthorize("@ss.hasPermission('dim:asset:query')")
    public CommonResult<PageResult<AssetRespVO>> getAssetPage(@Valid AssetPageReqVO pageReqVO) {
        PageResult<AssetDO> pageResult = assetService.getAssetPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, AssetRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出资产 Excel")
    @PreAuthorize("@ss.hasPermission('dim:asset:export')")
    public void exportAssetExcel(@Valid AssetPageReqVO pageReqVO,
                                 HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<AssetDO> list = assetService.getAssetPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "资产.xls", "数据", AssetRespVO.class,
                BeanUtils.toBean(list, AssetRespVO.class));
    }

}
