package cn.iocoder.yudao.module.dim.controller.admin.asset;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.AssetLogPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.asset.vo.AssetLogRespVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.asset.AssetLogDO;
import cn.iocoder.yudao.module.dim.service.asset.AssetLogService;
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

@Tag(name = "管理后台 - 资产日志")
@RestController
@RequestMapping("/dim/asset-log")
@Validated
public class AssetLogController {

    @Resource
    private AssetLogService assetLogService;

    @GetMapping("/get")
    @Operation(summary = "获得资产日志")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('dim:asset-log:query')")
    public CommonResult<AssetLogRespVO> getAssetLog(@RequestParam("id") Long id) {
        AssetLogDO log = assetLogService.getAssetLog(id);
        return success(BeanUtils.toBean(log, AssetLogRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得资产日志分页")
    @PreAuthorize("@ss.hasPermission('dim:asset-log:query')")
    public CommonResult<PageResult<AssetLogRespVO>> getAssetLogPage(@Valid AssetLogPageReqVO pageReqVO) {
        PageResult<AssetLogDO> pageResult = assetLogService.getAssetLogPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, AssetLogRespVO.class));
    }

    @GetMapping("/list-by-asset")
    @Operation(summary = "根据资产ID获得日志列表")
    @Parameter(name = "assetId", description = "资产ID", required = true)
    @PreAuthorize("@ss.hasPermission('dim:asset-log:query')")
    public CommonResult<List<AssetLogRespVO>> getAssetLogListByAssetId(
            @RequestParam("assetId") Long assetId) {
        List<AssetLogDO> list = assetLogService.getAssetLogListByAssetId(assetId);
        return success(BeanUtils.toBean(list, AssetLogRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出资产日志 Excel")
    @PreAuthorize("@ss.hasPermission('dim:asset-log:export')")
    public void exportAssetLogExcel(@Valid AssetLogPageReqVO pageReqVO,
                                    HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<AssetLogDO> list = assetLogService.getAssetLogPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "资产日志.xls", "数据", AssetLogRespVO.class,
                BeanUtils.toBean(list, AssetLogRespVO.class));
    }

}
