package cn.iocoder.yudao.module.dim.controller.admin.asset;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.AssetBorrowPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.AssetBorrowRespVO;
import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.AssetBorrowSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.asset.AssetBorrowDO;
import cn.iocoder.yudao.module.dim.service.asset.AssetBorrowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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

@Tag(name = "管理后台 - 资产外借")
@RestController
@RequestMapping("/dim/asset-borrow")
@Validated
public class AssetBorrowController {

    @Resource
    private AssetBorrowService assetBorrowService;

    @PostMapping("/create")
    @Operation(summary = "创建资产外借记录")
    @PreAuthorize("@ss.hasPermission('dim:asset-borrow:create')")
    public CommonResult<Long> createAssetBorrow(@Valid @RequestBody AssetBorrowSaveReqVO createReqVO) {
        return success(assetBorrowService.createAssetBorrow(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新资产外借记录")
    @PreAuthorize("@ss.hasPermission('dim:asset-borrow:update')")
    public CommonResult<Boolean> updateAssetBorrow(@Valid @RequestBody AssetBorrowSaveReqVO updateReqVO) {
        assetBorrowService.updateAssetBorrow(updateReqVO);
        return success(true);
    }

    @PostMapping("/return")
    @Operation(summary = "归还资产")
    @Parameters({
            @Parameter(name = "id", description = "外借记录编号", required = true),
            @Parameter(name = "remarks", description = "归还备注")
    })
    @PreAuthorize("@ss.hasPermission('dim:asset-borrow:update')")
    public CommonResult<Boolean> returnAsset(@RequestParam("id") Long id,
                                             @RequestParam(value = "remarks", required = false) String remarks) {
        assetBorrowService.returnAsset(id, remarks);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除资产外借记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('dim:asset-borrow:delete')")
    public CommonResult<Boolean> deleteAssetBorrow(@RequestParam("id") Long id) {
        assetBorrowService.deleteAssetBorrow(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得资产外借记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('dim:asset-borrow:query')")
    public CommonResult<AssetBorrowRespVO> getAssetBorrow(@RequestParam("id") Long id) {
        AssetBorrowDO borrow = assetBorrowService.getAssetBorrow(id);
        return success(BeanUtils.toBean(borrow, AssetBorrowRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得资产外借记录分页")
    @PreAuthorize("@ss.hasPermission('dim:asset-borrow:query')")
    public CommonResult<PageResult<AssetBorrowRespVO>> getAssetBorrowPage(@Valid AssetBorrowPageReqVO pageReqVO) {
        PageResult<AssetBorrowDO> pageResult = assetBorrowService.getAssetBorrowPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, AssetBorrowRespVO.class));
    }

    @GetMapping("/list-by-asset")
    @Operation(summary = "根据资产ID获得外借记录列表")
    @Parameter(name = "assetId", description = "资产ID", required = true)
    @PreAuthorize("@ss.hasPermission('dim:asset-borrow:query')")
    public CommonResult<List<AssetBorrowRespVO>> getAssetBorrowListByAssetId(
            @RequestParam("assetId") Long assetId) {
        List<AssetBorrowDO> list = assetBorrowService.getAssetBorrowListByAssetId(assetId);
        return success(BeanUtils.toBean(list, AssetBorrowRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出资产外借记录 Excel")
    @PreAuthorize("@ss.hasPermission('dim:asset-borrow:export')")
    public void exportAssetBorrowExcel(@Valid AssetBorrowPageReqVO pageReqVO,
                                       HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<AssetBorrowDO> list = assetBorrowService.getAssetBorrowPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "资产外借记录.xls", "数据", AssetBorrowRespVO.class,
                BeanUtils.toBean(list, AssetBorrowRespVO.class));
    }

}
