package cn.iocoder.yudao.module.dim.controller.admin.fire;

import cn.hutool.json.JSONUtil;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.dim.controller.admin.fire.vo.BuildingConfigRespVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.fire.BuildingConfigDO;
import cn.iocoder.yudao.module.dim.dal.mysql.fire.FireEquipmentMapper;
import cn.iocoder.yudao.module.dim.service.fire.BuildingConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 楼栋配置")
@RestController
@RequestMapping("/dim/building-config")
@Validated
public class BuildingConfigController {

    @Resource
    private BuildingConfigService buildingConfigService;

    @Resource
    private FireEquipmentMapper fireEquipmentMapper;

    @GetMapping("/list")
    @Operation(summary = "获得楼栋列表(含设备统计)")
    @PreAuthorize("@ss.hasPermission('dim:fire-equipment:query')")
    public CommonResult<List<BuildingConfigRespVO>> getBuildingList() {
        List<BuildingConfigDO> list = buildingConfigService.getBuildingList();
        List<BuildingConfigRespVO> result = new ArrayList<>();

        for (BuildingConfigDO config : list) {
            BuildingConfigRespVO respVO = new BuildingConfigRespVO();
            respVO.setId(config.getId());
            respVO.setBuildingNo(config.getBuildingNo());
            respVO.setBuildingName(config.getBuildingName());
            respVO.setFloors(JSONUtil.toList(config.getFloors(), Integer.class));
            respVO.setSort(config.getSort());
            respVO.setIconUrl(config.getIconUrl());
            respVO.setPlanUrl(config.getPlanUrl());
            respVO.setStatus(config.getStatus());

            // 统计该楼栋设备数量
            List<Map<String, Object>> stats = fireEquipmentMapper.selectCountByBuildingGroupByStatus(config.getBuildingNo());
            int total = 0;
            int normalCount = 0;
            int faultCount = 0;
            for (Map<String, Object> stat : stats) {
                Integer status = (Integer) stat.get("status");
                Long count = (Long) stat.get("count");
                total += count.intValue();
                if (status != null) {
                    if (status == 0) {
                        normalCount = count.intValue();
                    } else if (status == 2) {
                        faultCount = count.intValue();
                    }
                }
            }
            respVO.setEquipmentCount(total);
            respVO.setNormalCount(normalCount);
            respVO.setFaultCount(faultCount);

            result.add(respVO);
        }

        return success(result);
    }

    @GetMapping("/floors")
    @Operation(summary = "获得指定楼号的楼层列表(从配置表)")
    @Parameter(name = "buildingNo", description = "楼号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('dim:fire-equipment:query')")
    public CommonResult<List<Integer>> getFloorsByBuildingNo(@RequestParam("buildingNo") Integer buildingNo) {
        return success(buildingConfigService.getFloorsByBuildingNo(buildingNo));
    }

    @GetMapping("/get")
    @Operation(summary = "获得楼栋配置详情")
    @Parameter(name = "buildingNo", description = "楼号", required = true, example = "1")
    @PreAuthorize("@ss.hasPermission('dim:fire-equipment:query')")
    public CommonResult<BuildingConfigRespVO> getBuildingByNo(@RequestParam("buildingNo") Integer buildingNo) {
        BuildingConfigDO config = buildingConfigService.getBuildingByNo(buildingNo);
        if (config == null) {
            return success(null);
        }

        BuildingConfigRespVO respVO = new BuildingConfigRespVO();
        respVO.setId(config.getId());
        respVO.setBuildingNo(config.getBuildingNo());
        respVO.setBuildingName(config.getBuildingName());
        respVO.setFloors(JSONUtil.toList(config.getFloors(), Integer.class));
        respVO.setSort(config.getSort());
        respVO.setIconUrl(config.getIconUrl());
        respVO.setPlanUrl(config.getPlanUrl());
        respVO.setStatus(config.getStatus());

        return success(respVO);
    }

}
