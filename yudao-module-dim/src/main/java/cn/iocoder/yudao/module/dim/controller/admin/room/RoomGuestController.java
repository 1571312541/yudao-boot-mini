package cn.iocoder.yudao.module.dim.controller.admin.room;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.CancelReserveReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.ExtendStayReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.RoomGuestPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.RoomGuestRespVO;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.RoomGuestSaveReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.TransferRoomReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.room.RoomGuestDO;
import cn.iocoder.yudao.module.dim.service.room.RoomGuestService;
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

@Tag(name = "管理后台 - 住客")
@RestController
@RequestMapping("/dim/room-guest")
@Validated
public class RoomGuestController {

    @Resource
    private RoomGuestService roomGuestService;

    @PostMapping("/check-in")
    @Operation(summary = "办理入住")
    @PreAuthorize("@ss.hasPermission('dim:room-guest:create')")
    public CommonResult<Long> checkIn(@Valid @RequestBody RoomGuestSaveReqVO createReqVO) {
        return success(roomGuestService.checkIn(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新住客信息")
    @PreAuthorize("@ss.hasPermission('dim:room-guest:update')")
    public CommonResult<Boolean> updateRoomGuest(@Valid @RequestBody RoomGuestSaveReqVO updateReqVO) {
        roomGuestService.updateRoomGuest(updateReqVO);
        return success(true);
    }

    @PostMapping("/check-out")
    @Operation(summary = "办理退房")
    @Parameters({
            @Parameter(name = "id", description = "住客记录编号", required = true),
            @Parameter(name = "remarks", description = "退房备注")
    })
    @PreAuthorize("@ss.hasPermission('dim:room-guest:update')")
    public CommonResult<Boolean> checkOut(@RequestParam("id") Long id,
                                          @RequestParam(value = "remarks", required = false) String remarks) {
        roomGuestService.checkOut(id, remarks);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除住客记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('dim:room-guest:delete')")
    public CommonResult<Boolean> deleteRoomGuest(@RequestParam("id") Long id) {
        roomGuestService.deleteRoomGuest(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得住客记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('dim:room-guest:query')")
    public CommonResult<RoomGuestRespVO> getRoomGuest(@RequestParam("id") Long id) {
        RoomGuestDO guest = roomGuestService.getRoomGuest(id);
        return success(BeanUtils.toBean(guest, RoomGuestRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得住客分页")
    @PreAuthorize("@ss.hasPermission('dim:room-guest:query')")
    public CommonResult<PageResult<RoomGuestRespVO>> getRoomGuestPage(@Valid RoomGuestPageReqVO pageReqVO) {
        PageResult<RoomGuestDO> pageResult = roomGuestService.getRoomGuestPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, RoomGuestRespVO.class));
    }

    @GetMapping("/list-active")
    @Operation(summary = "获得房间在住住客列表")
    @Parameter(name = "roomId", description = "房间ID", required = true)
    @PreAuthorize("@ss.hasPermission('dim:room-guest:query')")
    public CommonResult<List<RoomGuestRespVO>> getActiveGuestsByRoomId(
            @RequestParam("roomId") Long roomId) {
        List<RoomGuestDO> list = roomGuestService.getActiveGuestsByRoomId(roomId);
        return success(BeanUtils.toBean(list, RoomGuestRespVO.class));
    }

    @PostMapping("/extend-stay")
    @Operation(summary = "续住")
    @PreAuthorize("@ss.hasPermission('dim:room-guest:update')")
    public CommonResult<Boolean> extendStay(@Valid @RequestBody ExtendStayReqVO reqVO) {
        roomGuestService.extendStay(reqVO);
        return success(true);
    }

    @PostMapping("/reserve")
    @Operation(summary = "预约")
    @PreAuthorize("@ss.hasPermission('dim:room-guest:create')")
    public CommonResult<Long> reserve(@Valid @RequestBody RoomGuestSaveReqVO reqVO) {
        return success(roomGuestService.reserve(reqVO));
    }

    @PostMapping("/confirm-reserve")
    @Operation(summary = "预约转入住")
    @Parameter(name = "id", description = "住客记录编号", required = true)
    @PreAuthorize("@ss.hasPermission('dim:room-guest:update')")
    public CommonResult<Boolean> confirmReserve(@RequestParam("id") Long id) {
        roomGuestService.confirmReserve(id);
        return success(true);
    }

    @PostMapping("/cancel-reserve")
    @Operation(summary = "取消预约")
    @PreAuthorize("@ss.hasPermission('dim:room-guest:update')")
    public CommonResult<Boolean> cancelReserve(@Valid @RequestBody CancelReserveReqVO reqVO) {
        roomGuestService.cancelReserve(reqVO);
        return success(true);
    }

    @PostMapping("/transfer-room")
    @Operation(summary = "换房")
    @PreAuthorize("@ss.hasPermission('dim:room-guest:update')")
    public CommonResult<Boolean> transferRoom(@Valid @RequestBody TransferRoomReqVO reqVO) {
        roomGuestService.transferRoom(reqVO);
        return success(true);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出住客 Excel")
    @PreAuthorize("@ss.hasPermission('dim:room-guest:export')")
    public void exportRoomGuestExcel(@Valid RoomGuestPageReqVO pageReqVO,
                                     HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<RoomGuestDO> list = roomGuestService.getRoomGuestPage(pageReqVO).getList();
        ExcelUtils.write(response, "住客.xls", "数据", RoomGuestRespVO.class,
                BeanUtils.toBean(list, RoomGuestRespVO.class));
    }

    @PostMapping("/add-co-guest")
    @Operation(summary = "添加同住人")
    @Parameter(name = "primaryGuestId", description = "主住客ID", required = true)
    @PreAuthorize("@ss.hasPermission('dim:room-guest:create')")
    public CommonResult<Long> addCoGuest(@Valid @RequestBody RoomGuestSaveReqVO reqVO,
                                         @RequestParam("primaryGuestId") Long primaryGuestId) {
        return success(roomGuestService.addCoGuest(reqVO, primaryGuestId));
    }

    @DeleteMapping("/remove-co-guest")
    @Operation(summary = "移除同住人")
    @Parameter(name = "id", description = "同住人记录编号", required = true)
    @PreAuthorize("@ss.hasPermission('dim:room-guest:delete')")
    public CommonResult<Boolean> removeCoGuest(@RequestParam("id") Long id) {
        roomGuestService.removeCoGuest(id);
        return success(true);
    }

    @GetMapping("/co-guests")
    @Operation(summary = "获取同住人列表")
    @Parameter(name = "primaryGuestId", description = "主住客ID", required = true)
    @PreAuthorize("@ss.hasPermission('dim:room-guest:query')")
    public CommonResult<List<RoomGuestRespVO>> getCoGuests(@RequestParam("primaryGuestId") Long primaryGuestId) {
        List<RoomGuestDO> list = roomGuestService.getCoGuests(primaryGuestId);
        return success(BeanUtils.toBean(list, RoomGuestRespVO.class));
    }

}
