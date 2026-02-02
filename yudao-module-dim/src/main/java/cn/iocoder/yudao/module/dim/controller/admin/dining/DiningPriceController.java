package cn.iocoder.yudao.module.dim.controller.admin.dining;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.price.DiningPricePageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.price.DiningPriceRespVO;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.price.DiningPriceSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.dining.DiningPriceDO;
import cn.iocoder.yudao.module.dim.service.dining.DiningPriceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 餐饮价格配置 Controller
 */
@Tag(name = "管理后台 - 餐饮价格配置")
@RestController
@RequestMapping("/dim/dining-price")
@Validated
public class DiningPriceController {

    @Resource
    private DiningPriceService diningPriceService;

    @PostMapping("/create")
    @Operation(summary = "创建餐饮价格配置")
    @PreAuthorize("@ss.hasPermission('dim:dining-price:create')")
    public CommonResult<Long> createDiningPrice(@Valid @RequestBody DiningPriceSaveReqVO createReqVO) {
        return success(diningPriceService.createDiningPrice(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新餐饮价格配置")
    @PreAuthorize("@ss.hasPermission('dim:dining-price:update')")
    public CommonResult<Boolean> updateDiningPrice(@Valid @RequestBody DiningPriceSaveReqVO updateReqVO) {
        diningPriceService.updateDiningPrice(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除餐饮价格配置")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('dim:dining-price:delete')")
    public CommonResult<Boolean> deleteDiningPrice(@RequestParam("id") Long id) {
        diningPriceService.deleteDiningPrice(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得餐饮价格配置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('dim:dining-price:query')")
    public CommonResult<DiningPriceRespVO> getDiningPrice(@RequestParam("id") Long id) {
        DiningPriceDO diningPrice = diningPriceService.getDiningPrice(id);
        return success(BeanUtils.toBean(diningPrice, DiningPriceRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得餐饮价格配置分页")
    @PreAuthorize("@ss.hasPermission('dim:dining-price:query')")
    public CommonResult<PageResult<DiningPriceRespVO>> getDiningPricePage(@Valid DiningPricePageReqVO pageReqVO) {
        PageResult<DiningPriceDO> pageResult = diningPriceService.getDiningPricePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DiningPriceRespVO.class));
    }

    @GetMapping("/get-price")
    @Operation(summary = "根据条件获取价格")
    @Parameters({
            @Parameter(name = "personType", description = "人员类型", required = true),
            @Parameter(name = "mealType", description = "餐别", required = true),
            @Parameter(name = "diningClass", description = "分类", required = true)
    })
    @PreAuthorize("@ss.hasPermission('dim:dining-price:query')")
    public CommonResult<BigDecimal> getPrice(
            @RequestParam("personType") Integer personType,
            @RequestParam("mealType") Integer mealType,
            @RequestParam("diningClass") Integer diningClass) {
        return success(diningPriceService.getPrice(personType, mealType, diningClass));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出餐饮价格配置 Excel")
    @PreAuthorize("@ss.hasPermission('dim:dining-price:export')")
    public void exportDiningPriceExcel(@Valid DiningPricePageReqVO pageReqVO,
                                       HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DiningPriceDO> list = diningPriceService.getDiningPricePage(pageReqVO).getList();
        ExcelUtils.write(response, "餐饮价格配置.xls", "数据", DiningPriceRespVO.class,
                BeanUtils.toBean(list, DiningPriceRespVO.class));
    }

}
