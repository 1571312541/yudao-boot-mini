package cn.iocoder.yudao.module.dim.controller.admin.room;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.StaySettlementPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.room.vo.StaySettlementRespVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.room.StaySettlementDO;
import cn.iocoder.yudao.module.dim.service.room.StaySettlementService;
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

@Tag(name = "管理后台 - 住宿结算")
@RestController
@RequestMapping("/dim/stay-settlement")
@Validated
public class StaySettlementController {

    @Resource
    private StaySettlementService staySettlementService;

    @GetMapping("/get")
    @Operation(summary = "获得结算详情")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('dim:stay-settlement:query')")
    public CommonResult<StaySettlementRespVO> getSettlement(@RequestParam("id") Long id) {
        StaySettlementDO settlement = staySettlementService.getSettlement(id);
        return success(BeanUtils.toBean(settlement, StaySettlementRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询结算记录")
    @PreAuthorize("@ss.hasPermission('dim:stay-settlement:query')")
    public CommonResult<PageResult<StaySettlementRespVO>> getSettlementPage(@Valid StaySettlementPageReqVO pageReqVO) {
        PageResult<StaySettlementDO> pageResult = staySettlementService.getSettlementPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, StaySettlementRespVO.class));
    }

    @PostMapping("/pay")
    @Operation(summary = "标记已结算")
    @Parameter(name = "id", description = "结算记录编号", required = true)
    @PreAuthorize("@ss.hasPermission('dim:stay-settlement:update')")
    public CommonResult<Boolean> pay(@RequestParam("id") Long id) {
        staySettlementService.updatePaymentStatus(id);
        return success(true);
    }

    @PostMapping("/invoice")
    @Operation(summary = "标记已开票")
    @Parameter(name = "id", description = "结算记录编号", required = true)
    @PreAuthorize("@ss.hasPermission('dim:stay-settlement:update')")
    public CommonResult<Boolean> invoice(@RequestParam("id") Long id) {
        staySettlementService.updateInvoiceStatus(id);
        return success(true);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出结算 Excel")
    @PreAuthorize("@ss.hasPermission('dim:stay-settlement:export')")
    public void exportSettlementExcel(@Valid StaySettlementPageReqVO pageReqVO,
                                      HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<StaySettlementDO> list = staySettlementService.getSettlementPage(pageReqVO).getList();
        ExcelUtils.write(response, "住宿结算.xls", "数据", StaySettlementRespVO.class,
                BeanUtils.toBean(list, StaySettlementRespVO.class));
    }

}
