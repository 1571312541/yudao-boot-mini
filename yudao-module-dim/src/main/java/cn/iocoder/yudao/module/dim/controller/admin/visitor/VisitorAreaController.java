package cn.iocoder.yudao.module.dim.controller.admin.visitor;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.dim.controller.admin.visitor.vo.*;
import cn.iocoder.yudao.module.dim.dal.dataobject.visitor.VisitorAreaDO;
import cn.iocoder.yudao.module.dim.service.visitor.VisitorAreaService;
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
 * 管理后台 - 访客区域
 */
@Tag(name = "管理后台 - 访客区域")
@RestController
@RequestMapping("/dim/visitor-area")
@Validated
public class VisitorAreaController {

    @Resource
    private VisitorAreaService visitorAreaService;

    @PostMapping("/create")
    @Operation(summary = "创建访客区域")
    @PreAuthorize("@ss.hasPermission('dim:visitor-area:create')")
    public CommonResult<Long> createVisitorArea(@Valid @RequestBody VisitorAreaSaveReqVO createReqVO) {
        return success(visitorAreaService.createVisitorArea(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新访客区域")
    @PreAuthorize("@ss.hasPermission('dim:visitor-area:update')")
    public CommonResult<Boolean> updateVisitorArea(@Valid @RequestBody VisitorAreaSaveReqVO updateReqVO) {
        visitorAreaService.updateVisitorArea(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除访客区域")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('dim:visitor-area:delete')")
    public CommonResult<Boolean> deleteVisitorArea(@RequestParam("id") Long id) {
        visitorAreaService.deleteVisitorArea(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得访客区域")
    @Parameter(name = "id", description = "编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('dim:visitor-area:query')")
    public CommonResult<VisitorAreaRespVO> getVisitorArea(@RequestParam("id") Long id) {
        VisitorAreaDO visitorArea = visitorAreaService.getVisitorArea(id);
        return success(BeanUtils.toBean(visitorArea, VisitorAreaRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得访客区域分页")
    @PreAuthorize("@ss.hasPermission('dim:visitor-area:query')")
    public CommonResult<PageResult<VisitorAreaRespVO>> getVisitorAreaPage(@Valid VisitorAreaPageReqVO pageReqVO) {
        PageResult<VisitorAreaDO> pageResult = visitorAreaService.getVisitorAreaPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, VisitorAreaRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得访客区域列表")
    @PreAuthorize("@ss.hasPermission('dim:visitor-area:query')")
    public CommonResult<List<VisitorAreaRespVO>> getVisitorAreaList() {
        List<VisitorAreaDO> list = visitorAreaService.getVisitorAreaList();
        return success(BeanUtils.toBean(list, VisitorAreaRespVO.class));
    }

}
