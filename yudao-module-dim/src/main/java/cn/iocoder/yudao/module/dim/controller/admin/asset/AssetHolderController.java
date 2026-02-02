package cn.iocoder.yudao.module.dim.controller.admin.asset;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.AssetHolderPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.AssetHolderRespVO;
import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.AssetHolderSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.asset.AssetHolderDO;
import cn.iocoder.yudao.module.dim.service.asset.AssetHolderService;
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

@Tag(name = "管理后台 - 资产持有")
@RestController
@RequestMapping("/dim/asset-holder")
@Validated
public class AssetHolderController {

    @Resource
    private AssetHolderService assetHolderService;

    @PostMapping("/create")
    @Operation(summary = "创建资产持有记录")
    @PreAuthorize("@ss.hasPermission('dim:asset-holder:create')")
    public CommonResult<Long> createAssetHolder(@Valid @RequestBody AssetHolderSaveReqVO createReqVO) {
        return success(assetHolderService.createAssetHolder(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新资产持有记录")
    @PreAuthorize("@ss.hasPermission('dim:asset-holder:update')")
    public CommonResult<Boolean> updateAssetHolder(@Valid @RequestBody AssetHolderSaveReqVO updateReqVO) {
        assetHolderService.updateAssetHolder(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除资产持有记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('dim:asset-holder:delete')")
    public CommonResult<Boolean> deleteAssetHolder(@RequestParam("id") Long id) {
        assetHolderService.deleteAssetHolder(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得资产持有记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('dim:asset-holder:query')")
    public CommonResult<AssetHolderRespVO> getAssetHolder(@RequestParam("id") Long id) {
        AssetHolderDO holder = assetHolderService.getAssetHolder(id);
        return success(BeanUtils.toBean(holder, AssetHolderRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得资产持有记录分页")
    @PreAuthorize("@ss.hasPermission('dim:asset-holder:query')")
    public CommonResult<PageResult<AssetHolderRespVO>> getAssetHolderPage(@Valid AssetHolderPageReqVO pageReqVO) {
        PageResult<AssetHolderDO> pageResult = assetHolderService.getAssetHolderPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, AssetHolderRespVO.class));
    }

    @GetMapping("/list-by-asset")
    @Operation(summary = "根据资产ID获得持有记录列表")
    @Parameter(name = "assetId", description = "资产ID", required = true)
    @PreAuthorize("@ss.hasPermission('dim:asset-holder:query')")
    public CommonResult<List<AssetHolderRespVO>> getAssetHolderListByAssetId(
            @RequestParam("assetId") Long assetId) {
        List<AssetHolderDO> list = assetHolderService.getAssetHolderListByAssetId(assetId);
        return success(BeanUtils.toBean(list, AssetHolderRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出资产持有记录 Excel")
    @PreAuthorize("@ss.hasPermission('dim:asset-holder:export')")
    public void exportAssetHolderExcel(@Valid AssetHolderPageReqVO pageReqVO,
                                       HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<AssetHolderDO> list = assetHolderService.getAssetHolderPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "资产持有记录.xls", "数据", AssetHolderRespVO.class,
                BeanUtils.toBean(list, AssetHolderRespVO.class));
    }

}
