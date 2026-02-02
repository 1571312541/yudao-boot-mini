package cn.iocoder.yudao.module.dim.controller.admin.vehicle;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.dim.controller.admin.vehicle.vo.*;
import cn.iocoder.yudao.module.dim.dal.dataobject.vehicle.VehicleDO;
import cn.iocoder.yudao.module.dim.service.vehicle.VehicleService;
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

@Tag(name = "管理后台 - 公务车辆")
@RestController
@RequestMapping("/dim/vehicle")
@Validated
public class VehicleController {

    @Resource
    private VehicleService vehicleService;

    @PostMapping("/create")
    @Operation(summary = "创建公务车辆")
    @PreAuthorize("@ss.hasPermission('dim:vehicle:create')")
    public CommonResult<Long> createVehicle(@Valid @RequestBody VehicleSaveReqVO createReqVO) {
        return success(vehicleService.createVehicle(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新公务车辆")
    @PreAuthorize("@ss.hasPermission('dim:vehicle:update')")
    public CommonResult<Boolean> updateVehicle(@Valid @RequestBody VehicleSaveReqVO updateReqVO) {
        vehicleService.updateVehicle(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除公务车辆")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('dim:vehicle:delete')")
    public CommonResult<Boolean> deleteVehicle(@RequestParam("id") Long id) {
        vehicleService.deleteVehicle(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得公务车辆")
    @Parameter(name = "id", description = "编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('dim:vehicle:query')")
    public CommonResult<VehicleRespVO> getVehicle(@RequestParam("id") Long id) {
        VehicleDO vehicle = vehicleService.getVehicle(id);
        return success(BeanUtils.toBean(vehicle, VehicleRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得公务车辆分页")
    @PreAuthorize("@ss.hasPermission('dim:vehicle:query')")
    public CommonResult<PageResult<VehicleRespVO>> getVehiclePage(@Valid VehiclePageReqVO pageReqVO) {
        PageResult<VehicleDO> pageResult = vehicleService.getVehiclePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, VehicleRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得公务车辆列表")
    @PreAuthorize("@ss.hasPermission('dim:vehicle:query')")
    public CommonResult<List<VehicleRespVO>> getVehicleList() {
        List<VehicleDO> list = vehicleService.getVehicleList();
        return success(BeanUtils.toBean(list, VehicleRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出公务车辆 Excel")
    @PreAuthorize("@ss.hasPermission('dim:vehicle:export')")
    public void exportVehicleExcel(@Valid VehiclePageReqVO pageReqVO,
                                   HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<VehicleDO> list = vehicleService.getVehiclePage(pageReqVO).getList();
        ExcelUtils.write(response, "公务车辆.xls", "数据", VehicleRespVO.class,
                BeanUtils.toBean(list, VehicleRespVO.class));
    }

}
