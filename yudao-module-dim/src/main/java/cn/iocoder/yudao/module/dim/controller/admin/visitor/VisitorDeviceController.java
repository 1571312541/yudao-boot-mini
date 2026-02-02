package cn.iocoder.yudao.module.dim.controller.admin.visitor;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.dim.controller.admin.visitor.vo.*;
import cn.iocoder.yudao.module.dim.dal.dataobject.visitor.VisitorAreaDO;
import cn.iocoder.yudao.module.dim.dal.dataobject.visitor.VisitorCardDO;
import cn.iocoder.yudao.module.dim.dal.dataobject.visitor.VisitorDO;
import cn.iocoder.yudao.module.dim.service.visitor.VisitorAreaService;
import cn.iocoder.yudao.module.dim.service.visitor.VisitorCardService;
import cn.iocoder.yudao.module.dim.service.visitor.VisitorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

/**
 * 管理后台 - 访客人脸/刷卡接口
 *
 * 提供给门禁设备调用的接口
 */
@Tag(name = "管理后台 - 访客人脸/刷卡接口")
@RestController
@RequestMapping("/dim/visitor/device")
@Validated
public class VisitorDeviceController {

    @Resource
    private VisitorService visitorService;

    @Resource
    private VisitorCardService visitorCardService;

    @Resource
    private VisitorAreaService visitorAreaService;

    @PostMapping("/card/record")
    @Operation(summary = "刷卡记录 - 设备回调接口")
    public CommonResult<Map<String, Object>> handleCardRecord(@RequestBody CardRecordReqVO reqVO) {
        Map<String, Object> result = new HashMap<>();

        // 根据卡号/员工编号查找访客
        VisitorCardDO card = visitorCardService.getCardByCardId(reqVO.getEmployeeNumber());
        if (card == null) {
            result.put("success", false);
            result.put("message", "未找到对应的访客信息");
            return success(result);
        }

        // 获取访客信息
        VisitorDO visitor = visitorService.getVisitor(card.getVisitorId());
        if (visitor == null) {
            result.put("success", false);
            result.put("message", "访客信息不存在");
            return success(result);
        }

        // 返回验证结果
        result.put("success", true);
        result.put("visitorId", visitor.getId());
        result.put("visitorName", visitor.getName());
        result.put("message", "验证通过");

        return success(result);
    }

    @GetMapping("/area/list")
    @Operation(summary = "获取区域列表 - 设备调用")
    public CommonResult<List<VisitorAreaRespVO>> getAreaList() {
        List<VisitorAreaDO> list = visitorAreaService.getVisitorAreaList();
        return success(BeanUtils.toBean(list, VisitorAreaRespVO.class));
    }

    @PostMapping("/card/info")
    @Operation(summary = "获取访客卡信息")
    @Parameter(name = "visitorId", description = "访客ID", required = true)
    public CommonResult<Map<String, Object>> getCardInfo(@RequestParam("visitorId") Long visitorId) {
        Map<String, Object> result = new HashMap<>();

        // 获取卡信息
        VisitorCardDO card = visitorCardService.getCardByVisitorId(visitorId);
        result.put("visitorCard", card != null ? BeanUtils.toBean(card, VisitorCardRespVO.class) : null);

        // 获取区域列表
        List<VisitorAreaDO> areas = visitorAreaService.getVisitorAreaList();
        result.put("visitorAreas", BeanUtils.toBean(areas, VisitorAreaRespVO.class));

        return success(result);
    }

    @PostMapping("/card/bind")
    @Operation(summary = "绑定访客卡")
    public CommonResult<Long> bindCard(@Valid @RequestBody VisitorCardBindReqVO bindReqVO) {
        return success(visitorCardService.bindCard(bindReqVO));
    }

    @DeleteMapping("/card/unbind")
    @Operation(summary = "解绑访客卡")
    @Parameter(name = "visitorId", description = "访客ID", required = true)
    public CommonResult<Boolean> unbindCard(@RequestParam("visitorId") Long visitorId) {
        visitorCardService.unbindCard(visitorId);
        return success(true);
    }

    @PostMapping("/face/save")
    @Operation(summary = "保存人脸图片")
    public CommonResult<Boolean> saveFace(@Valid @RequestBody VisitorFaceSaveReqVO reqVO) {
        // 将人脸图片保存到访客记录
        // 实际应用中需要：1. 保存图片到文件系统/OSS  2. 更新访客的imgInfo字段
        VisitorDO visitor = visitorService.getVisitor(reqVO.getVisitorId());
        if (visitor == null) {
            return success(false);
        }

        // 简化实现：将图片列表转为逗号分隔的字符串存储
        if (reqVO.getFaceImages() != null && !reqVO.getFaceImages().isEmpty()) {
            // TODO: 实际应用中应该保存图片到文件系统并存储路径
            String imgPaths = String.join(",", reqVO.getFaceImages().subList(0,
                    Math.min(reqVO.getFaceImages().size(), 5)));
            visitor.setImgInfo(imgPaths);
            visitorService.updateVisitorImgInfo(reqVO.getVisitorId(), imgPaths);
        }

        return success(true);
    }

}
