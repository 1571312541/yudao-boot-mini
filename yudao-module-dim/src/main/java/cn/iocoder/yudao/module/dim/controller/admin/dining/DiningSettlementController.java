package cn.iocoder.yudao.module.dim.controller.admin.dining;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.DiningSettlementAutoCreateReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.DiningSettlementPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.DiningSettlementPayInfoReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.DiningSettlementRespVO;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.DiningSettlementSaveReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.statistics.DiningUnsettledExpenseVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.dining.DiningSettlementDO;
import cn.iocoder.yudao.module.dim.service.dining.DiningSettlementService;
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
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 餐饮结算 Controller
 */
@Tag(name = "管理后台 - 餐饮结算")
@RestController
@RequestMapping("/dim/dining-settlement")
@Validated
public class DiningSettlementController {

    @Resource
    private DiningSettlementService diningSettlementService;

    @PostMapping("/create")
    @Operation(summary = "创建餐饮结算")
    @PreAuthorize("@ss.hasPermission('dim:dining-settlement:create')")
    public CommonResult<Long> createDiningSettlement(@Valid @RequestBody DiningSettlementSaveReqVO createReqVO) {
        return success(diningSettlementService.createDiningSettlement(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新餐饮结算")
    @PreAuthorize("@ss.hasPermission('dim:dining-settlement:update')")
    public CommonResult<Boolean> updateDiningSettlement(@Valid @RequestBody DiningSettlementSaveReqVO updateReqVO) {
        diningSettlementService.updateDiningSettlement(updateReqVO);
        return success(true);
    }

    @PutMapping("/confirm")
    @Operation(summary = "确认结算")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('dim:dining-settlement:update')")
    public CommonResult<Boolean> confirmSettlement(@RequestParam("id") Long id) {
        diningSettlementService.confirmSettlement(id);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除餐饮结算")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('dim:dining-settlement:delete')")
    public CommonResult<Boolean> deleteDiningSettlement(@RequestParam("id") Long id) {
        diningSettlementService.deleteDiningSettlement(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得餐饮结算")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('dim:dining-settlement:query')")
    public CommonResult<DiningSettlementRespVO> getDiningSettlement(@RequestParam("id") Long id) {
        DiningSettlementDO settlement = diningSettlementService.getDiningSettlement(id);
        return success(BeanUtils.toBean(settlement, DiningSettlementRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得餐饮结算分页")
    @PreAuthorize("@ss.hasPermission('dim:dining-settlement:query')")
    public CommonResult<PageResult<DiningSettlementRespVO>> getDiningSettlementPage(
            @Valid DiningSettlementPageReqVO pageReqVO) {
        PageResult<DiningSettlementDO> pageResult = diningSettlementService.getDiningSettlementPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DiningSettlementRespVO.class));
    }

    @GetMapping("/list-by-user")
    @Operation(summary = "根据用户ID获得结算记录列表")
    @Parameter(name = "userId", description = "用户ID", required = true)
    @PreAuthorize("@ss.hasPermission('dim:dining-settlement:query')")
    public CommonResult<List<DiningSettlementRespVO>> getDiningSettlementListByUserId(
            @RequestParam("userId") Long userId) {
        List<DiningSettlementDO> list = diningSettlementService.getDiningSettlementListByUserId(userId);
        return success(BeanUtils.toBean(list, DiningSettlementRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出餐饮结算 Excel")
    @PreAuthorize("@ss.hasPermission('dim:dining-settlement:export')")
    public void exportDiningSettlementExcel(@Valid DiningSettlementPageReqVO pageReqVO,
                                            HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DiningSettlementDO> list = diningSettlementService.getDiningSettlementPage(pageReqVO).getList();
        ExcelUtils.write(response, "餐饮结算.xls", "数据", DiningSettlementRespVO.class,
                BeanUtils.toBean(list, DiningSettlementRespVO.class));
    }

    /**
     * 自动创建结算单
     * 根据时间范围自动统计就餐次数和金额
     */
    @PostMapping("/auto-create")
    @Operation(summary = "自动创建结算单")
    @PreAuthorize("@ss.hasPermission('dim:dining-settlement:create')")
    public CommonResult<Long> autoCreateSettlement(@Valid @RequestBody DiningSettlementAutoCreateReqVO reqVO) {
        return success(diningSettlementService.autoCreateSettlement(reqVO));
    }

    /**
     * 更新支付/开票状态
     * 同时更新结算单和关联的报餐登记记录
     */
    @PutMapping("/update-pay-info")
    @Operation(summary = "更新支付/开票状态")
    @PreAuthorize("@ss.hasPermission('dim:dining-settlement:update')")
    public CommonResult<Boolean> updatePayInfo(@Valid @RequestBody DiningSettlementPayInfoReqVO reqVO) {
        diningSettlementService.updatePayInfo(reqVO.getId(), reqVO.getIsPaid(), reqVO.getIsInvoiced());
        return success(true);
    }

    /**
     * 获取未结算的餐饮费用
     * 供住宿模块退房时调用
     */
    @GetMapping("/unsettled-expense")
    @Operation(summary = "获取未结算餐饮费用", description = "根据用户ID或餐卡号获取未结算的餐饮费用，供住宿模块退房时调用")
    @Parameters({
            @Parameter(name = "userId", description = "用户ID", example = "1"),
            @Parameter(name = "cardId", description = "餐卡号", example = "C001")
    })
    @PreAuthorize("@ss.hasPermission('dim:dining-settlement:query')")
    public CommonResult<DiningUnsettledExpenseVO> getUnsettledExpense(
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "cardId", required = false) String cardId) {
        DiningUnsettledExpenseVO result = diningSettlementService.getUnsettledExpense(userId, cardId);
        return success(result);
    }

}
