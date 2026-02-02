package cn.iocoder.yudao.module.dim.controller.admin.vehicle;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.dim.controller.admin.vehicle.vo.*;
import cn.iocoder.yudao.module.dim.dal.dataobject.vehicle.VehicleUsageDO;
import cn.iocoder.yudao.module.dim.service.vehicle.VehicleUsageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 用车记录")
@RestController
@RequestMapping("/dim/vehicle-usage")
@Validated
public class VehicleUsageController {

    @Resource
    private VehicleUsageService vehicleUsageService;

    @PostMapping("/create")
    @Operation(summary = "创建用车记录")
    @PreAuthorize("@ss.hasPermission('dim:vehicle-usage:create')")
    public CommonResult<Long> createVehicleUsage(@Valid @RequestBody VehicleUsageSaveReqVO createReqVO) {
        return success(vehicleUsageService.createVehicleUsage(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新用车记录")
    @PreAuthorize("@ss.hasPermission('dim:vehicle-usage:update')")
    public CommonResult<Boolean> updateVehicleUsage(@Valid @RequestBody VehicleUsageSaveReqVO updateReqVO) {
        vehicleUsageService.updateVehicleUsage(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除用车记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('dim:vehicle-usage:delete')")
    public CommonResult<Boolean> deleteVehicleUsage(@RequestParam("id") Long id) {
        vehicleUsageService.deleteVehicleUsage(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得用车记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('dim:vehicle-usage:query')")
    public CommonResult<VehicleUsageRespVO> getVehicleUsage(@RequestParam("id") Long id) {
        VehicleUsageDO usage = vehicleUsageService.getVehicleUsage(id);
        return success(BeanUtils.toBean(usage, VehicleUsageRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得用车记录分页")
    @PreAuthorize("@ss.hasPermission('dim:vehicle-usage:query')")
    public CommonResult<PageResult<VehicleUsageRespVO>> getVehicleUsagePage(@Valid VehicleUsagePageReqVO pageReqVO) {
        PageResult<VehicleUsageDO> pageResult = vehicleUsageService.getVehicleUsagePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, VehicleUsageRespVO.class));
    }

    @PutMapping("/return")
    @Operation(summary = "归还车辆")
    @Parameters({
            @Parameter(name = "id", description = "用车记录编号", required = true),
            @Parameter(name = "mileage", description = "行驶里程")
    })
    @PreAuthorize("@ss.hasPermission('dim:vehicle-usage:update')")
    public CommonResult<Boolean> returnVehicle(@RequestParam("id") Long id,
                                               @RequestParam(value = "mileage", required = false) Integer mileage) {
        vehicleUsageService.returnVehicle(id, mileage);
        return success(true);
    }

}
