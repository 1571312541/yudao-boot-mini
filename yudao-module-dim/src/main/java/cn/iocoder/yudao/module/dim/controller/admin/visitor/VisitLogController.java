package cn.iocoder.yudao.module.dim.controller.admin.visitor;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.dim.controller.admin.visitor.vo.*;
import cn.iocoder.yudao.module.dim.dal.dataobject.visitor.VisitLogDO;
import cn.iocoder.yudao.module.dim.dal.dataobject.visitor.VisitorDO;
import cn.iocoder.yudao.module.dim.service.visitor.VisitLogService;
import cn.iocoder.yudao.module.dim.service.visitor.VisitorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 来访日志")
@RestController
@RequestMapping("/dim/visit-log")
@Validated
public class VisitLogController {

    @Resource
    private VisitLogService visitLogService;

    @Resource
    private VisitorService visitorService;

    @PostMapping("/create")
    @Operation(summary = "创建来访日志")
    @PreAuthorize("@ss.hasPermission('dim:visit-log:create')")
    public CommonResult<Long> createVisitLog(@Valid @RequestBody VisitLogSaveReqVO createReqVO) {
        return success(visitLogService.createVisitLog(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新来访日志")
    @PreAuthorize("@ss.hasPermission('dim:visit-log:update')")
    public CommonResult<Boolean> updateVisitLog(@Valid @RequestBody VisitLogSaveReqVO updateReqVO) {
        visitLogService.updateVisitLog(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除来访日志")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('dim:visit-log:delete')")
    public CommonResult<Boolean> deleteVisitLog(@RequestParam("id") Long id) {
        visitLogService.deleteVisitLog(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得来访日志")
    @Parameter(name = "id", description = "编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('dim:visit-log:query')")
    public CommonResult<VisitLogRespVO> getVisitLog(@RequestParam("id") Long id) {
        VisitLogDO visitLog = visitLogService.getVisitLog(id);
        VisitLogRespVO respVO = BeanUtils.toBean(visitLog, VisitLogRespVO.class);
        // 填充访客姓名
        if (visitLog != null && visitLog.getVisitorId() != null) {
            VisitorDO visitor = visitorService.getVisitor(visitLog.getVisitorId());
            if (visitor != null) {
                respVO.setVisitorName(visitor.getName());
            }
        }
        return success(respVO);
    }

    @GetMapping("/page")
    @Operation(summary = "获得来访日志分页")
    @PreAuthorize("@ss.hasPermission('dim:visit-log:query')")
    public CommonResult<PageResult<VisitLogRespVO>> getVisitLogPage(@Valid VisitLogPageReqVO pageReqVO) {
        PageResult<VisitLogDO> pageResult = visitLogService.getVisitLogPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, VisitLogRespVO.class));
    }

    @PutMapping("/leave")
    @Operation(summary = "访客离场")
    @Parameter(name = "id", description = "来访日志编号", required = true)
    @PreAuthorize("@ss.hasPermission('dim:visit-log:update')")
    public CommonResult<Boolean> leave(@RequestParam("id") Long id) {
        visitLogService.leave(id);
        return success(true);
    }

}
