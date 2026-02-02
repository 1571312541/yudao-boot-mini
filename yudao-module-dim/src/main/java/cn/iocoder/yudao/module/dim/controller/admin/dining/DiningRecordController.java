package cn.iocoder.yudao.module.dim.controller.admin.dining;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.DiningRecordPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.DiningRecordRespVO;
import cn.iocoder.yudao.module.dim.controller.admin.dining.vo.DiningRecordSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.dining.DiningRecordDO;
import cn.iocoder.yudao.module.dim.service.dining.DiningRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;

/**
 * 就餐记录 Controller
 */
@Tag(name = "管理后台 - 就餐记录")
@RestController
@RequestMapping("/dim/dining-record")
@Validated
public class DiningRecordController {

    @Resource
    private DiningRecordService diningRecordService;

    @PostMapping("/create")
    @Operation(summary = "创建就餐记录")
    @PreAuthorize("@ss.hasPermission('dim:dining-record:create')")
    public CommonResult<Long> createDiningRecord(@Valid @RequestBody DiningRecordSaveReqVO createReqVO) {
        return success(diningRecordService.createDiningRecord(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新就餐记录")
    @PreAuthorize("@ss.hasPermission('dim:dining-record:update')")
    public CommonResult<Boolean> updateDiningRecord(@Valid @RequestBody DiningRecordSaveReqVO updateReqVO) {
        diningRecordService.updateDiningRecord(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除就餐记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('dim:dining-record:delete')")
    public CommonResult<Boolean> deleteDiningRecord(@RequestParam("id") Long id) {
        diningRecordService.deleteDiningRecord(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得就餐记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('dim:dining-record:query')")
    public CommonResult<DiningRecordRespVO> getDiningRecord(@RequestParam("id") Long id) {
        DiningRecordDO record = diningRecordService.getDiningRecord(id);
        return success(BeanUtils.toBean(record, DiningRecordRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得就餐记录分页")
    @PreAuthorize("@ss.hasPermission('dim:dining-record:query')")
    public CommonResult<PageResult<DiningRecordRespVO>> getDiningRecordPage(@Valid DiningRecordPageReqVO pageReqVO) {
        PageResult<DiningRecordDO> pageResult = diningRecordService.getDiningRecordPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DiningRecordRespVO.class));
    }

    @GetMapping("/list-by-user")
    @Operation(summary = "根据用户ID和日期范围获得就餐记录列表")
    @Parameters({
            @Parameter(name = "userId", description = "用户ID", required = true),
            @Parameter(name = "startDate", description = "开始日期", required = true),
            @Parameter(name = "endDate", description = "结束日期", required = true)
    })
    @PreAuthorize("@ss.hasPermission('dim:dining-record:query')")
    public CommonResult<List<DiningRecordRespVO>> getDiningRecordListByUserIdAndDateRange(
            @RequestParam("userId") Long userId,
            @RequestParam("startDate") @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY) LocalDate endDate) {
        List<DiningRecordDO> list = diningRecordService.getDiningRecordListByUserIdAndDateRange(userId, startDate, endDate);
        return success(BeanUtils.toBean(list, DiningRecordRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出就餐记录 Excel")
    @PreAuthorize("@ss.hasPermission('dim:dining-record:export')")
    public void exportDiningRecordExcel(@Valid DiningRecordPageReqVO pageReqVO,
                                        HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DiningRecordDO> list = diningRecordService.getDiningRecordPage(pageReqVO).getList();
        ExcelUtils.write(response, "就餐记录.xls", "数据", DiningRecordRespVO.class,
                BeanUtils.toBean(list, DiningRecordRespVO.class));
    }

    /**
     * 二维码设备刷卡接口 - 兼容旧设备格式
     * 请求体格式: cardNo=xxx&&其他参数 或 直接卡号字符串
     * 响应格式:
     *   code=0000 刷卡成功
     *   code=0001 该卡不可用
     *   code=0002 已经刷过卡
     *   code=0003 未报餐
     */
    @PostMapping("/swipe-card")
    @Operation(summary = "二维码设备刷卡接口")
    public void swipeCard(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String cardNo = "";

        // 读取请求体
        try (InputStream is = request.getInputStream();
             InputStreamReader isr = new InputStreamReader(is);
             BufferedReader br = new BufferedReader(isr)) {

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            String body = sb.toString();
            // 解析请求体：支持 cardNo=xxx&&其他参数 或 直接卡号字符串
            if (body.contains("&&")) {
                // 格式: cardNo=xxx&&其他参数
                String[] parts = body.split("&&")[0].split("=");
                if (parts.length > 1) {
                    cardNo = parts[1];
                }
            } else if (body.contains("=")) {
                // 格式: cardNo=xxx
                String[] parts = body.split("=");
                if (parts.length > 1) {
                    cardNo = parts[1];
                }
            } else {
                // 直接是卡号字符串
                cardNo = body;
            }
        }

        // 调用刷卡服务
        int result = diningRecordService.swipeCard(cardNo, null);

        // 设置响应
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        switch (result) {
            case 0:
                response.getWriter().write("code=0000"); // 刷卡成功
                break;
            case -1:
                response.getWriter().write("code=0001"); // 该卡不可用
                break;
            case -2:
                response.getWriter().write("code=0002"); // 已经刷过卡
                break;
            case -3:
                response.getWriter().write("code=0003"); // 未报餐
                break;
            default:
                response.getWriter().write("code=9999"); // 未知错误
        }
    }

}
