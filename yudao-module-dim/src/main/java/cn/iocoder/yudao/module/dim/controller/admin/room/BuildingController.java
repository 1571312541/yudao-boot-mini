package cn.iocoder.yudao.module.dim.controller.admin.room;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.BuildingPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.BuildingRespVO;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.BuildingSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.room.BuildingDO;
import cn.iocoder.yudao.module.dim.service.room.BuildingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 楼栋")
@RestController
@RequestMapping("/dim/building")
@Validated
public class BuildingController {

    @Resource
    private BuildingService buildingService;

    @PostMapping("/create")
    @Operation(summary = "创建楼栋")
    @PreAuthorize("@ss.hasPermission('dim:building:create')")
    public CommonResult<Long> createBuilding(@Valid @RequestBody BuildingSaveReqVO createReqVO) {
        return success(buildingService.createBuilding(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新楼栋")
    @PreAuthorize("@ss.hasPermission('dim:building:update')")
    public CommonResult<Boolean> updateBuilding(@Valid @RequestBody BuildingSaveReqVO updateReqVO) {
        buildingService.updateBuilding(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除楼栋")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('dim:building:delete')")
    public CommonResult<Boolean> deleteBuilding(@RequestParam("id") Long id) {
        buildingService.deleteBuilding(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得楼栋")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('dim:building:query')")
    public CommonResult<BuildingRespVO> getBuilding(@RequestParam("id") Long id) {
        BuildingDO building = buildingService.getBuilding(id);
        return success(BeanUtils.toBean(building, BuildingRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得楼栋分页")
    @PreAuthorize("@ss.hasPermission('dim:building:query')")
    public CommonResult<PageResult<BuildingRespVO>> getBuildingPage(@Valid BuildingPageReqVO pageReqVO) {
        PageResult<BuildingDO> pageResult = buildingService.getBuildingPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, BuildingRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得楼栋列表")
    @Parameters({
            @Parameter(name = "name", description = "楼栋名称"),
            @Parameter(name = "status", description = "状态")
    })
    @PreAuthorize("@ss.hasPermission('dim:building:query')")
    public CommonResult<List<BuildingRespVO>> getBuildingList(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "status", required = false) Integer status) {
        List<BuildingDO> list = buildingService.getBuildingList(name, status);
        return success(BeanUtils.toBean(list, BuildingRespVO.class));
    }

}
