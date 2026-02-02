package cn.iocoder.yudao.module.dim.controller.admin.room;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.StayLogPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.StayLogRespVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.room.StayLogDO;
import cn.iocoder.yudao.module.dim.service.room.StayLogService;
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

@Tag(name = "管理后台 - 住宿日志")
@RestController
@RequestMapping("/dim/stay-log")
@Validated
public class StayLogController {

    @Resource
    private StayLogService stayLogService;

    @GetMapping("/get")
    @Operation(summary = "获得住宿日志")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('dim:stay-log:query')")
    public CommonResult<StayLogRespVO> getStayLog(@RequestParam("id") Long id) {
        StayLogDO log = stayLogService.getStayLog(id);
        return success(BeanUtils.toBean(log, StayLogRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得住宿日志分页")
    @PreAuthorize("@ss.hasPermission('dim:stay-log:query')")
    public CommonResult<PageResult<StayLogRespVO>> getStayLogPage(@Valid StayLogPageReqVO pageReqVO) {
        PageResult<StayLogDO> pageResult = stayLogService.getStayLogPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, StayLogRespVO.class));
    }

    @GetMapping("/list-by-room")
    @Operation(summary = "根据房间ID获得日志列表")
    @Parameter(name = "roomId", description = "房间ID", required = true)
    @PreAuthorize("@ss.hasPermission('dim:stay-log:query')")
    public CommonResult<List<StayLogRespVO>> getStayLogListByRoomId(
            @RequestParam("roomId") Long roomId) {
        List<StayLogDO> list = stayLogService.getStayLogListByRoomId(roomId);
        return success(BeanUtils.toBean(list, StayLogRespVO.class));
    }

}
