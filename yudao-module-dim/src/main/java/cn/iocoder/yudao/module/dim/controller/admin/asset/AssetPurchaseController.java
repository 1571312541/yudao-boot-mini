package cn.iocoder.yudao.module.dim.controller.admin.asset;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.*;
import cn.iocoder.yudao.module.dim.dal.dataobject.asset.AssetPurchaseDO;
import cn.iocoder.yudao.module.dim.dal.dataobject.asset.AssetPurchaseItemDO;
import cn.iocoder.yudao.module.dim.service.asset.AssetPurchaseService;
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

@Tag(name = "管理后台 - 资产采购")
@RestController
@RequestMapping("/dim/asset-purchase")
@Validated
public class AssetPurchaseController {

    @Resource
    private AssetPurchaseService assetPurchaseService;

    @PostMapping("/create")
    @Operation(summary = "创建资产采购单")
    @PreAuthorize("@ss.hasPermission('dim:asset-purchase:create')")
    public CommonResult<Long> createAssetPurchase(@Valid @RequestBody AssetPurchaseSaveReqVO createReqVO) {
        return success(assetPurchaseService.createAssetPurchase(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新资产采购单")
    @PreAuthorize("@ss.hasPermission('dim:asset-purchase:update')")
    public CommonResult<Boolean> updateAssetPurchase(@Valid @RequestBody AssetPurchaseSaveReqVO updateReqVO) {
        assetPurchaseService.updateAssetPurchase(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除资产采购单")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('dim:asset-purchase:delete')")
    public CommonResult<Boolean> deleteAssetPurchase(@RequestParam("id") Long id) {
        assetPurchaseService.deleteAssetPurchase(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得资产采购单详情")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('dim:asset-purchase:query')")
    public CommonResult<AssetPurchaseRespVO> getAssetPurchase(@RequestParam("id") Long id) {
        AssetPurchaseDO purchase = assetPurchaseService.getAssetPurchase(id);
        AssetPurchaseRespVO respVO = BeanUtils.toBean(purchase, AssetPurchaseRespVO.class);
        // 获取明细列表
        if (respVO != null) {
            List<AssetPurchaseItemDO> items = assetPurchaseService.getAssetPurchaseItemList(id);
            respVO.setItems(BeanUtils.toBean(items, AssetPurchaseItemRespVO.class));
        }
        return success(respVO);
    }

    @GetMapping("/page")
    @Operation(summary = "获得资产采购单分页")
    @PreAuthorize("@ss.hasPermission('dim:asset-purchase:query')")
    public CommonResult<PageResult<AssetPurchaseRespVO>> getAssetPurchasePage(@Valid AssetPurchasePageReqVO pageReqVO) {
        PageResult<AssetPurchaseDO> pageResult = assetPurchaseService.getAssetPurchasePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, AssetPurchaseRespVO.class));
    }

    @PostMapping("/submit")
    @Operation(summary = "提交采购单审核")
    @Parameter(name = "id", description = "采购单编号", required = true)
    @PreAuthorize("@ss.hasPermission('dim:asset-purchase:update')")
    public CommonResult<Boolean> submitAssetPurchase(@RequestParam("id") Long id) {
        assetPurchaseService.submitAssetPurchase(id);
        return success(true);
    }

    @PostMapping("/audit")
    @Operation(summary = "审核采购单")
    @PreAuthorize("@ss.hasPermission('dim:asset-purchase:audit')")
    public CommonResult<Boolean> auditAssetPurchase(@Valid @RequestBody AssetPurchaseAuditReqVO auditReqVO) {
        assetPurchaseService.auditAssetPurchase(auditReqVO);
        return success(true);
    }

    @PostMapping("/warehouse")
    @Operation(summary = "采购入库")
    @Parameter(name = "id", description = "采购单编号", required = true)
    @PreAuthorize("@ss.hasPermission('dim:asset-purchase:warehouse')")
    public CommonResult<Boolean> warehouseAssetPurchase(@RequestParam("id") Long id) {
        assetPurchaseService.warehouseAssetPurchase(id);
        return success(true);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出资产采购单 Excel")
    @PreAuthorize("@ss.hasPermission('dim:asset-purchase:export')")
    public void exportAssetPurchaseExcel(@Valid AssetPurchasePageReqVO pageReqVO,
                                          HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<AssetPurchaseDO> list = assetPurchaseService.getAssetPurchasePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "资产采购单.xls", "数据", AssetPurchaseRespVO.class,
                BeanUtils.toBean(list, AssetPurchaseRespVO.class));
    }

}
