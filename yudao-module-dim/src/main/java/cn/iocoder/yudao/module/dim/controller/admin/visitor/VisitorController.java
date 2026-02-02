package cn.iocoder.yudao.module.dim.controller.admin.visitor;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.dim.controller.admin.visitor.vo.*;
import cn.iocoder.yudao.module.dim.dal.dataobject.visitor.VisitorDO;
import cn.iocoder.yudao.module.dim.service.visitor.VisitorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 访客")
@RestController
@RequestMapping("/dim/visitor")
@Validated
public class VisitorController {

    @Resource
    private VisitorService visitorService;

    @PostMapping("/create")
    @Operation(summary = "创建访客")
    @PreAuthorize("@ss.hasPermission('dim:visitor:create')")
    public CommonResult<Long> createVisitor(@Valid @RequestBody VisitorSaveReqVO createReqVO) {
        return success(visitorService.createVisitor(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新访客")
    @PreAuthorize("@ss.hasPermission('dim:visitor:update')")
    public CommonResult<Boolean> updateVisitor(@Valid @RequestBody VisitorSaveReqVO updateReqVO) {
        visitorService.updateVisitor(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除访客")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('dim:visitor:delete')")
    public CommonResult<Boolean> deleteVisitor(@RequestParam("id") Long id) {
        visitorService.deleteVisitor(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得访客")
    @Parameter(name = "id", description = "编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('dim:visitor:query')")
    public CommonResult<VisitorRespVO> getVisitor(@RequestParam("id") Long id) {
        VisitorDO visitor = visitorService.getVisitor(id);
        return success(BeanUtils.toBean(visitor, VisitorRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得访客分页")
    @PreAuthorize("@ss.hasPermission('dim:visitor:query')")
    public CommonResult<PageResult<VisitorRespVO>> getVisitorPage(@Valid VisitorPageReqVO pageReqVO) {
        PageResult<VisitorDO> pageResult = visitorService.getVisitorPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, VisitorRespVO.class));
    }

    @PutMapping("/departure")
    @Operation(summary = "访客离场")
    @Parameter(name = "id", description = "访客编号", required = true)
    @PreAuthorize("@ss.hasPermission('dim:visitor:update')")
    public CommonResult<Boolean> departure(@RequestParam("id") Long id) {
        visitorService.departure(id);
        return success(true);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出访客 Excel")
    @PreAuthorize("@ss.hasPermission('dim:visitor:export')")
    public void exportVisitorExcel(@Valid VisitorPageReqVO pageReqVO,
                                   HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<VisitorDO> list = visitorService.getVisitorPage(pageReqVO).getList();
        ExcelUtils.write(response, "访客信息.xls", "数据", VisitorRespVO.class,
                BeanUtils.toBean(list, VisitorRespVO.class));
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "获得访客导入模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        List<VisitorImportVO> list = Arrays.asList();
        ExcelUtils.write(response, "访客导入模板.xls", "访客信息", VisitorImportVO.class, list);
    }

    @PostMapping("/import")
    @Operation(summary = "导入访客")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
            @Parameter(name = "updateSupport", description = "是否更新已存在的访客")
    })
    @PreAuthorize("@ss.hasPermission('dim:visitor:import')")
    public CommonResult<VisitorService.VisitorImportRespVO> importExcel(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws IOException {
        List<VisitorImportVO> list = ExcelUtils.read(file, VisitorImportVO.class);
        return success(visitorService.importVisitors(list, updateSupport));
    }

}
