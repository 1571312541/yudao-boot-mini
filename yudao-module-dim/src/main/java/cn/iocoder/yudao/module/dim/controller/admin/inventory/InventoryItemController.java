package cn.iocoder.yudao.module.dim.controller.admin.inventory;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.dim.controller.admin.inventory.vo.InventoryItemImportVO;
import cn.iocoder.yudao.module.dim.controller.admin.inventory.vo.InventoryItemPageReqVO;
import cn.iocoder.yudao.module.dim.controller.admin.inventory.vo.InventoryItemRespVO;
import cn.iocoder.yudao.module.dim.controller.admin.inventory.vo.InventoryItemSaveReqVO;
import cn.iocoder.yudao.module.dim.dal.dataobject.inventory.InventoryItemDO;
import cn.iocoder.yudao.module.dim.service.inventory.InventoryItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 库存物品 Controller
 */
@Tag(name = "管理后台 - 库存物品")
@RestController
@RequestMapping("/dim/inventory/item")
@Validated
public class InventoryItemController {

    @Resource
    private InventoryItemService inventoryItemService;

    @PostMapping("/create")
    @Operation(summary = "创建库存物品")
    @PreAuthorize("@ss.hasPermission('dim:inventory:item:create')")
    public CommonResult<Long> createItem(@Valid @RequestBody InventoryItemSaveReqVO createReqVO) {
        return success(inventoryItemService.createItem(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新库存物品")
    @PreAuthorize("@ss.hasPermission('dim:inventory:item:update')")
    public CommonResult<Boolean> updateItem(@Valid @RequestBody InventoryItemSaveReqVO updateReqVO) {
        inventoryItemService.updateItem(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除库存物品")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('dim:inventory:item:delete')")
    public CommonResult<Boolean> deleteItem(@RequestParam("id") Long id) {
        inventoryItemService.deleteItem(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得库存物品")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('dim:inventory:item:query')")
    public CommonResult<InventoryItemRespVO> getItem(@RequestParam("id") Long id) {
        InventoryItemDO item = inventoryItemService.getItem(id);
        return success(BeanUtils.toBean(item, InventoryItemRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得库存物品分页")
    @PreAuthorize("@ss.hasPermission('dim:inventory:item:query')")
    public CommonResult<PageResult<InventoryItemRespVO>> getItemPage(@Valid InventoryItemPageReqVO pageReqVO) {
        PageResult<InventoryItemDO> pageResult = inventoryItemService.getItemPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, InventoryItemRespVO.class));
    }

    @GetMapping("/list-by-type")
    @Operation(summary = "根据类型获得物品列表")
    @Parameter(name = "type", description = "类型：1-物资 2-耗材", required = true)
    @PreAuthorize("@ss.hasPermission('dim:inventory:item:query')")
    public CommonResult<List<InventoryItemRespVO>> getItemListByType(@RequestParam("type") Integer type) {
        List<InventoryItemDO> list = inventoryItemService.getItemListByType(type);
        return success(BeanUtils.toBean(list, InventoryItemRespVO.class));
    }

    @GetMapping("/alert-list")
    @Operation(summary = "获得库存预警物品列表")
    @PreAuthorize("@ss.hasPermission('dim:inventory:item:query')")
    public CommonResult<List<InventoryItemRespVO>> getAlertItems() {
        List<InventoryItemDO> list = inventoryItemService.getAlertItems();
        return success(BeanUtils.toBean(list, InventoryItemRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出库存物品 Excel")
    @PreAuthorize("@ss.hasPermission('dim:inventory:item:export')")
    public void exportItemExcel(@Valid InventoryItemPageReqVO pageReqVO,
                                HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<InventoryItemDO> list = inventoryItemService.getItemPage(pageReqVO).getList();
        ExcelUtils.write(response, "库存物品.xls", "数据", InventoryItemRespVO.class,
                BeanUtils.toBean(list, InventoryItemRespVO.class));
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "获取库存物品导入模板")
    public void getImportTemplate(HttpServletResponse response) throws IOException {
        List<InventoryItemImportVO> list = Arrays.asList();
        ExcelUtils.write(response, "库存物品导入模板.xls", "物品信息", InventoryItemImportVO.class, list);
    }

    @PostMapping("/import")
    @Operation(summary = "导入库存物品")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
            @Parameter(name = "updateSupport", description = "是否更新已存在的物品")
    })
    @PreAuthorize("@ss.hasPermission('dim:inventory:item:import')")
    public CommonResult<InventoryItemService.ItemImportRespVO> importExcel(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws IOException {
        List<InventoryItemImportVO> list = ExcelUtils.read(file, InventoryItemImportVO.class);
        return success(inventoryItemService.importItems(list, updateSupport));
    }

}
