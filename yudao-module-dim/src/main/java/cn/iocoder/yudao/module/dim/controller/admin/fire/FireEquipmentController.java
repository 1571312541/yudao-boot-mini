package cn.iocoder.yudao.module.dim.controller.admin.fire;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.dim.controller.admin.fire.vo.*;
import cn.iocoder.yudao.module.dim.dal.dataobject.fire.FireEquipmentCheckLogDO;
import cn.iocoder.yudao.module.dim.dal.dataobject.fire.FireEquipmentDO;
import cn.iocoder.yudao.module.dim.service.fire.FireEquipmentService;
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
import java.util.Map;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 消防设备")
@RestController
@RequestMapping("/dim/fire-equipment")
@Validated
public class FireEquipmentController {

    @Resource
    private FireEquipmentService fireEquipmentService;

    @PostMapping("/create")
    @Operation(summary = "创建消防设备")
    @PreAuthorize("@ss.hasPermission('dim:fire-equipment:create')")
    public CommonResult<Long> createFireEquipment(@Valid @RequestBody FireEquipmentSaveReqVO createReqVO) {
        return success(fireEquipmentService.createFireEquipment(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新消防设备")
    @PreAuthorize("@ss.hasPermission('dim:fire-equipment:update')")
    public CommonResult<Boolean> updateFireEquipment(@Valid @RequestBody FireEquipmentSaveReqVO updateReqVO) {
        fireEquipmentService.updateFireEquipment(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除消防设备")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('dim:fire-equipment:delete')")
    public CommonResult<Boolean> deleteFireEquipment(@RequestParam("id") Long id) {
        fireEquipmentService.deleteFireEquipment(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得消防设备")
    @Parameter(name = "id", description = "编号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('dim:fire-equipment:query')")
    public CommonResult<FireEquipmentRespVO> getFireEquipment(@RequestParam("id") Long id) {
        FireEquipmentDO equipment = fireEquipmentService.getFireEquipment(id);
        return success(BeanUtils.toBean(equipment, FireEquipmentRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得消防设备分页")
    @PreAuthorize("@ss.hasPermission('dim:fire-equipment:query')")
    public CommonResult<PageResult<FireEquipmentRespVO>> getFireEquipmentPage(@Valid FireEquipmentPageReqVO pageReqVO) {
        PageResult<FireEquipmentDO> pageResult = fireEquipmentService.getFireEquipmentPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, FireEquipmentRespVO.class));
    }

    @GetMapping("/building-nos")
    @Operation(summary = "获得所有楼号列表")
    @PreAuthorize("@ss.hasPermission('dim:fire-equipment:query')")
    public CommonResult<List<Integer>> getBuildingNos() {
        return success(fireEquipmentService.getBuildingNos());
    }

    @GetMapping("/floors")
    @Operation(summary = "获得指定楼号的楼层列表")
    @Parameter(name = "buildingNo", description = "楼号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('dim:fire-equipment:query')")
    public CommonResult<List<Integer>> getFloorsByBuilding(@RequestParam("buildingNo") Integer buildingNo) {
        return success(fireEquipmentService.getFloorsByBuilding(buildingNo));
    }

    @GetMapping("/list-by-location")
    @Operation(summary = "按楼栋楼层获取设备列表")
    @PreAuthorize("@ss.hasPermission('dim:fire-equipment:query')")
    public CommonResult<List<FireEquipmentRespVO>> getListByBuildingAndFloor(
            @RequestParam(value = "buildingNo", required = false) Integer buildingNo,
            @RequestParam(value = "floor", required = false) Integer floor) {
        List<FireEquipmentDO> list = fireEquipmentService.getListByBuildingAndFloor(buildingNo, floor);
        return success(BeanUtils.toBean(list, FireEquipmentRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出消防设备 Excel")
    @PreAuthorize("@ss.hasPermission('dim:fire-equipment:export')")
    public void exportFireEquipmentExcel(@Valid FireEquipmentPageReqVO pageReqVO,
                                         HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<FireEquipmentDO> list = fireEquipmentService.getFireEquipmentPage(pageReqVO).getList();
        ExcelUtils.write(response, "消防设备.xls", "数据", FireEquipmentRespVO.class,
                BeanUtils.toBean(list, FireEquipmentRespVO.class));
    }

    // ========== 巡检相关接口 ==========

    @PostMapping("/check")
    @Operation(summary = "执行消防设备巡检")
    @PreAuthorize("@ss.hasPermission('dim:fire-equipment:check')")
    public CommonResult<Long> checkEquipment(@Valid @RequestBody FireEquipmentCheckReqVO checkReqVO) {
        return success(fireEquipmentService.checkEquipment(checkReqVO));
    }

    @GetMapping("/check-logs")
    @Operation(summary = "获得设备巡检历史分页")
    @Parameter(name = "equipmentId", description = "设备ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('dim:fire-equipment:query')")
    public CommonResult<PageResult<FireEquipmentCheckLogRespVO>> getCheckLogPage(
            @RequestParam("equipmentId") Long equipmentId,
            @Valid PageParam pageParam) {
        PageResult<FireEquipmentCheckLogDO> pageResult = fireEquipmentService.getCheckLogPage(equipmentId, pageParam);
        return success(BeanUtils.toBean(pageResult, FireEquipmentCheckLogRespVO.class));
    }

    @GetMapping("/check-log/latest")
    @Operation(summary = "获得设备最近一次巡检记录")
    @Parameter(name = "equipmentId", description = "设备ID", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('dim:fire-equipment:query')")
    public CommonResult<FireEquipmentCheckLogRespVO> getLatestCheckLog(@RequestParam("equipmentId") Long equipmentId) {
        FireEquipmentCheckLogDO checkLog = fireEquipmentService.getLatestCheckLog(equipmentId);
        return success(BeanUtils.toBean(checkLog, FireEquipmentCheckLogRespVO.class));
    }

    // ========== 统计相关接口 ==========

    @GetMapping("/statistics")
    @Operation(summary = "获得消防设备统计数据")
    @PreAuthorize("@ss.hasPermission('dim:fire-equipment:query')")
    public CommonResult<Map<String, Object>> getStatistics() {
        return success(fireEquipmentService.getStatistics());
    }

    @GetMapping("/expiring")
    @Operation(summary = "获得即将到期的设备列表")
    @Parameter(name = "days", description = "天数", example = "30")
    @PreAuthorize("@ss.hasPermission('dim:fire-equipment:query')")
    public CommonResult<List<FireEquipmentRespVO>> getExpiringEquipments(
            @RequestParam(value = "days", defaultValue = "30") Integer days) {
        List<FireEquipmentDO> list = fireEquipmentService.getExpiringEquipments(days);
        return success(BeanUtils.toBean(list, FireEquipmentRespVO.class));
    }

    @GetMapping("/need-check")
    @Operation(summary = "获得需巡检的设备列表")
    @Parameter(name = "days", description = "天数", example = "7")
    @PreAuthorize("@ss.hasPermission('dim:fire-equipment:query')")
    public CommonResult<List<FireEquipmentRespVO>> getNeedCheckEquipments(
            @RequestParam(value = "days", defaultValue = "7") Integer days) {
        List<FireEquipmentDO> list = fireEquipmentService.getNeedCheckEquipments(days);
        return success(BeanUtils.toBean(list, FireEquipmentRespVO.class));
    }

}
