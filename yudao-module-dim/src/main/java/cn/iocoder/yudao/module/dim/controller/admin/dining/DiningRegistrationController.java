package cn.iocoder.yudao.module.dim.controller.admin.dining;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.DiningRegistrationPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.DiningRegistrationRespVO;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.DiningRegistrationSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.dining.DiningRegistrationDO;
import cn.iocoder.yudao.module.dim.service.dining.DiningRegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;

/**
 * 报餐登记 Controller
 */
@Tag(name = "管理后台 - 报餐登记")
@RestController
@RequestMapping("/dim/dining-registration")
@Validated
public class DiningRegistrationController {

    @Resource
    private DiningRegistrationService diningRegistrationService;

    @PostMapping("/create")
    @Operation(summary = "创建报餐登记")
    @PreAuthorize("@ss.hasPermission('dim:dining-registration:create')")
    public CommonResult<Long> createDiningRegistration(@Valid @RequestBody DiningRegistrationSaveReqVO createReqVO) {
        return success(diningRegistrationService.createDiningRegistration(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新报餐登记")
    @PreAuthorize("@ss.hasPermission('dim:dining-registration:update')")
    public CommonResult<Boolean> updateDiningRegistration(@Valid @RequestBody DiningRegistrationSaveReqVO updateReqVO) {
        diningRegistrationService.updateDiningRegistration(updateReqVO);
        return success(true);
    }

    @PutMapping("/confirm")
    @Operation(summary = "确认报餐")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('dim:dining-registration:update')")
    public CommonResult<Boolean> confirmDiningRegistration(@RequestParam("id") Long id) {
        diningRegistrationService.confirmDiningRegistration(id);
        return success(true);
    }

    @PutMapping("/cancel")
    @Operation(summary = "取消报餐")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('dim:dining-registration:update')")
    public CommonResult<Boolean> cancelDiningRegistration(@RequestParam("id") Long id) {
        diningRegistrationService.cancelDiningRegistration(id);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除报餐登记")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('dim:dining-registration:delete')")
    public CommonResult<Boolean> deleteDiningRegistration(@RequestParam("id") Long id) {
        diningRegistrationService.deleteDiningRegistration(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得报餐登记")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('dim:dining-registration:query')")
    public CommonResult<DiningRegistrationRespVO> getDiningRegistration(@RequestParam("id") Long id) {
        DiningRegistrationDO registration = diningRegistrationService.getDiningRegistration(id);
        return success(BeanUtils.toBean(registration, DiningRegistrationRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得报餐登记分页")
    @PreAuthorize("@ss.hasPermission('dim:dining-registration:query')")
    public CommonResult<PageResult<DiningRegistrationRespVO>> getDiningRegistrationPage(
            @Valid DiningRegistrationPageReqVO pageReqVO) {
        PageResult<DiningRegistrationDO> pageResult = diningRegistrationService.getDiningRegistrationPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DiningRegistrationRespVO.class));
    }

    @GetMapping("/list-by-date")
    @Operation(summary = "根据日期和餐别获得报餐列表")
    @Parameters({
            @Parameter(name = "date", description = "日期", required = true),
            @Parameter(name = "mealType", description = "餐别", required = true)
    })
    @PreAuthorize("@ss.hasPermission('dim:dining-registration:query')")
    public CommonResult<List<DiningRegistrationRespVO>> getDiningRegistrationListByDateAndMealType(
            @RequestParam("date") @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY) LocalDate date,
            @RequestParam("mealType") Integer mealType) {
        List<DiningRegistrationDO> list = diningRegistrationService.getDiningRegistrationListByDateAndMealType(date, mealType);
        return success(BeanUtils.toBean(list, DiningRegistrationRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出报餐登记 Excel")
    @PreAuthorize("@ss.hasPermission('dim:dining-registration:export')")
    public void exportDiningRegistrationExcel(@Valid DiningRegistrationPageReqVO pageReqVO,
                                              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DiningRegistrationDO> list = diningRegistrationService.getDiningRegistrationPage(pageReqVO).getList();
        ExcelUtils.write(response, "报餐登记.xls", "数据", DiningRegistrationRespVO.class,
                BeanUtils.toBean(list, DiningRegistrationRespVO.class));
    }

}
