package cn.iocoder.yudao.module.dim.controller.admin.room;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.RoomImportRespVO;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.RoomImportVO;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.RoomPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.RoomRespVO;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.RoomSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.room.RoomDO;
import cn.iocoder.yudao.module.dim.service.room.RoomService;
import cn.idev.excel.EasyExcel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import java.util.Map;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 房间")
@RestController
@RequestMapping("/dim/room")
@Validated
public class RoomController {

    @Resource
    private RoomService roomService;

    @PostMapping("/create")
    @Operation(summary = "创建房间")
    @PreAuthorize("@ss.hasPermission('dim:room:create')")
    public CommonResult<Long> createRoom(@Valid @RequestBody RoomSaveReqVO createReqVO) {
        return success(roomService.createRoom(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新房间")
    @PreAuthorize("@ss.hasPermission('dim:room:update')")
    public CommonResult<Boolean> updateRoom(@Valid @RequestBody RoomSaveReqVO updateReqVO) {
        roomService.updateRoom(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除房间")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('dim:room:delete')")
    public CommonResult<Boolean> deleteRoom(@RequestParam("id") Long id) {
        roomService.deleteRoom(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得房间")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('dim:room:query')")
    public CommonResult<RoomRespVO> getRoom(@RequestParam("id") Long id) {
        RoomDO room = roomService.getRoom(id);
        return success(BeanUtils.toBean(room, RoomRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得房间分页")
    @PreAuthorize("@ss.hasPermission('dim:room:query')")
    public CommonResult<PageResult<RoomRespVO>> getRoomPage(@Valid RoomPageReqVO pageReqVO) {
        PageResult<RoomDO> pageResult = roomService.getRoomPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, RoomRespVO.class));
    }

    @GetMapping("/list-by-floor")
    @Operation(summary = "根据楼层ID获得房间列表")
    @Parameter(name = "floorId", description = "楼层ID", required = true)
    @PreAuthorize("@ss.hasPermission('dim:room:query')")
    public CommonResult<List<RoomRespVO>> getRoomListByFloorId(
            @RequestParam("floorId") Long floorId) {
        List<RoomDO> list = roomService.getRoomListByFloorId(floorId);
        return success(BeanUtils.toBean(list, RoomRespVO.class));
    }

    @GetMapping("/count-by-status")
    @Operation(summary = "按状态统计房间数量")
    @PreAuthorize("@ss.hasPermission('dim:room:query')")
    public CommonResult<Map<Integer, Long>> countRoomsByStatus() {
        return success(roomService.countRoomsByStatus());
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出房间 Excel")
    @PreAuthorize("@ss.hasPermission('dim:room:export')")
    public void exportRoomExcel(@Valid RoomPageReqVO pageReqVO,
                                HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<RoomDO> list = roomService.getRoomPage(pageReqVO).getList();
        ExcelUtils.write(response, "房间.xls", "数据", RoomRespVO.class,
                BeanUtils.toBean(list, RoomRespVO.class));
    }

    @PostMapping("/import")
    @Operation(summary = "导入房间")
    @PreAuthorize("@ss.hasPermission('dim:room:create')")
    public CommonResult<RoomImportRespVO> importRooms(@RequestParam("file") MultipartFile file) throws IOException {
        List<RoomImportVO> list = EasyExcel.read(file.getInputStream())
                .head(RoomImportVO.class)
                .sheet()
                .doReadSync();
        return success(roomService.importRooms(list));
    }

    @GetMapping("/import-template")
    @Operation(summary = "下载房间导入模板")
    @PreAuthorize("@ss.hasPermission('dim:room:query')")
    public void downloadImportTemplate(HttpServletResponse response) throws IOException {
        // 构建模板数据
        RoomImportVO template = new RoomImportVO();
        template.setBuildingName("1号楼");
        template.setFloorName("1层");
        template.setRoomNumber("101");
        template.setRoomType("单人间");
        template.setBedCount(1);
        template.setArea(new java.math.BigDecimal("20.00"));
        template.setPrice(new java.math.BigDecimal("200.00"));
        template.setFacilities("空调,电视,独卫");

        ExcelUtils.write(response, "房间导入模板.xls", "数据", RoomImportVO.class,
                Arrays.asList(template));
    }

}
