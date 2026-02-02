package cn.iocoder.yudao.module.dim.controller.admin.dining;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.DiningPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.DiningRespVO;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.DiningSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.dining.DiningDO;
import cn.iocoder.yudao.module.dim.service.dining.DiningService;
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

/**
 * 餐饮设置 Controller
 */
@Tag(name = "管理后台 - 餐饮设置")
@RestController
@RequestMapping("/dim/dining")
@Validated
public class DiningController {

    @Resource
    private DiningService diningService;

    @PostMapping("/create")
    @Operation(summary = "创建餐饮设置")
    @PreAuthorize("@ss.hasPermission('dim:dining:create')")
    public CommonResult<Long> createDining(@Valid @RequestBody DiningSaveReqVO createReqVO) {
        return success(diningService.createDining(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新餐饮设置")
    @PreAuthorize("@ss.hasPermission('dim:dining:update')")
    public CommonResult<Boolean> updateDining(@Valid @RequestBody DiningSaveReqVO updateReqVO) {
        diningService.updateDining(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除餐饮设置")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('dim:dining:delete')")
    public CommonResult<Boolean> deleteDining(@RequestParam("id") Long id) {
        diningService.deleteDining(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得餐饮设置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('dim:dining:query')")
    public CommonResult<DiningRespVO> getDining(@RequestParam("id") Long id) {
        DiningDO dining = diningService.getDining(id);
        return success(BeanUtils.toBean(dining, DiningRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得餐饮设置分页")
    @PreAuthorize("@ss.hasPermission('dim:dining:query')")
    public CommonResult<PageResult<DiningRespVO>> getDiningPage(@Valid DiningPageReqVO pageReqVO) {
        PageResult<DiningDO> pageResult = diningService.getDiningPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DiningRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得餐饮设置列表")
    @PreAuthorize("@ss.hasPermission('dim:dining:query')")
    public CommonResult<List<DiningRespVO>> getDiningList() {
        List<DiningDO> list = diningService.getDiningList();
        return success(BeanUtils.toBean(list, DiningRespVO.class));
    }

}
