package cn.iocoder.yudao.module.dim.controller.admin.visitor;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.dim.controller.admin.visitor.vo.*;
import cn.iocoder.yudao.module.dim.dal.dataobject.visitor.ReservationVisitorDO;
import cn.iocoder.yudao.module.dim.service.visitor.ReservationVisitorService;
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

@Tag(name = "管理后台 - 预约访客")
@RestController
@RequestMapping("/dim/reservation-visitor")
@Validated
public class ReservationVisitorController {

    @Resource
    private ReservationVisitorService reservationVisitorService;

    @PostMapping("/create")
    @Operation(summary = "创建预约访客")
    @PreAuthorize("@ss.hasPermission('dim:reservation-visitor:create')")
    public CommonResult<Long> createReservationVisitor(@Valid @RequestBody ReservationVisitorSaveReqVO createReqVO) {
        return success(reservationVisitorService.createReservationVisitor(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新预约访客")
    @PreAuthorize("@ss.hasPermission('dim:reservation-visitor:update')")
    public CommonResult<Boolean> updateReservationVisitor(@Valid @RequestBody ReservationVisitorSaveReqVO updateReqVO) {
        reservationVisitorService.updateReservationVisitor(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除预约访客")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('dim:reservation-visitor:delete')")
    public CommonResult<Boolean> deleteReservationVisitor(@RequestParam("id") Long id) {
        reservationVisitorService.deleteReservationVisitor(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得预约访客")
    @Parameter(name = "id", description = "编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('dim:reservation-visitor:query')")
    public CommonResult<ReservationVisitorRespVO> getReservationVisitor(@RequestParam("id") Long id) {
        ReservationVisitorDO reservationVisitor = reservationVisitorService.getReservationVisitor(id);
        return success(BeanUtils.toBean(reservationVisitor, ReservationVisitorRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得预约访客分页")
    @PreAuthorize("@ss.hasPermission('dim:reservation-visitor:query')")
    public CommonResult<PageResult<ReservationVisitorRespVO>> getReservationVisitorPage(@Valid ReservationVisitorPageReqVO pageReqVO) {
        PageResult<ReservationVisitorDO> pageResult = reservationVisitorService.getReservationVisitorPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ReservationVisitorRespVO.class));
    }

    @PutMapping("/cancel")
    @Operation(summary = "取消预约")
    @Parameter(name = "id", description = "预约编号", required = true)
    @PreAuthorize("@ss.hasPermission('dim:reservation-visitor:update')")
    public CommonResult<Boolean> cancel(@RequestParam("id") Long id) {
        reservationVisitorService.cancel(id);
        return success(true);
    }

    @PostMapping("/check-in")
    @Operation(summary = "预约来访")
    @Parameter(name = "id", description = "预约编号", required = true)
    @PreAuthorize("@ss.hasPermission('dim:reservation-visitor:update')")
    public CommonResult<Long> checkIn(@RequestParam("id") Long id) {
        return success(reservationVisitorService.checkIn(id));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出预约访客 Excel")
    @PreAuthorize("@ss.hasPermission('dim:reservation-visitor:export')")
    public void exportReservationVisitorExcel(@Valid ReservationVisitorPageReqVO pageReqVO,
                                               HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ReservationVisitorDO> list = reservationVisitorService.getReservationVisitorPage(pageReqVO).getList();
        ExcelUtils.write(response, "预约访客信息.xls", "数据", ReservationVisitorRespVO.class,
                BeanUtils.toBean(list, ReservationVisitorRespVO.class));
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "获得预约访客导入模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        List<ReservationVisitorImportVO> list = Arrays.asList();
        ExcelUtils.write(response, "预约访客导入模板.xls", "预约访客信息", ReservationVisitorImportVO.class, list);
    }

    @PostMapping("/import")
    @Operation(summary = "导入预约访客")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
            @Parameter(name = "updateSupport", description = "是否更新已存在的预约")
    })
    @PreAuthorize("@ss.hasPermission('dim:reservation-visitor:import')")
    public CommonResult<ReservationVisitorService.ReservationVisitorImportRespVO> importExcel(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws IOException {
        List<ReservationVisitorImportVO> list = ExcelUtils.read(file, ReservationVisitorImportVO.class);
        return success(reservationVisitorService.importReservationVisitors(list, updateSupport));
    }

}
