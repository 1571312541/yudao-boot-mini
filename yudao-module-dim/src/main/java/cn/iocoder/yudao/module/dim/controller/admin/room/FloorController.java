package cn.iocoder.yudao.module.dim.controller.admin.room;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.FloorRespVO;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.FloorSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.room.FloorDO;
import cn.iocoder.yudao.module.dim.service.room.FloorService;
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

@Tag(name = "管理后台 - 楼层")
@RestController
@RequestMapping("/dim/floor")
@Validated
public class FloorController {

    @Resource
    private FloorService floorService;

    @PostMapping("/create")
    @Operation(summary = "创建楼层")
    @PreAuthorize("@ss.hasPermission('dim:floor:create')")
    public CommonResult<Long> createFloor(@Valid @RequestBody FloorSaveReqVO createReqVO) {
        return success(floorService.createFloor(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新楼层")
    @PreAuthorize("@ss.hasPermission('dim:floor:update')")
    public CommonResult<Boolean> updateFloor(@Valid @RequestBody FloorSaveReqVO updateReqVO) {
        floorService.updateFloor(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除楼层")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('dim:floor:delete')")
    public CommonResult<Boolean> deleteFloor(@RequestParam("id") Long id) {
        floorService.deleteFloor(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得楼层")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('dim:floor:query')")
    public CommonResult<FloorRespVO> getFloor(@RequestParam("id") Long id) {
        FloorDO floor = floorService.getFloor(id);
        return success(BeanUtils.toBean(floor, FloorRespVO.class));
    }

    @GetMapping("/list-by-building")
    @Operation(summary = "根据楼栋ID获得楼层列表")
    @Parameter(name = "buildingId", description = "楼栋ID", required = true)
    @PreAuthorize("@ss.hasPermission('dim:floor:query')")
    public CommonResult<List<FloorRespVO>> getFloorListByBuildingId(
            @RequestParam("buildingId") Long buildingId) {
        List<FloorDO> list = floorService.getFloorListByBuildingId(buildingId);
        return success(BeanUtils.toBean(list, FloorRespVO.class));
    }

}
