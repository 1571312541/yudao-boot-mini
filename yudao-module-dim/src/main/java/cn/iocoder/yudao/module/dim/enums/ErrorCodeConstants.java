package cn.iocoder.yudao.module.dim.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

/**
 * DIM 模块错误码枚举
 * dim 系统，使用 1-100-000-000 段
 */
public interface ErrorCodeConstants {

    // ========== 访客模块 1-100-001-000 ==========
    ErrorCode VISITOR_NOT_EXISTS = new ErrorCode(1_100_001_000, "访客不存在");
    ErrorCode VISITOR_ID_NUM_DUPLICATE = new ErrorCode(1_100_001_001, "证件号码已存在");
    ErrorCode VISIT_LOG_NOT_EXISTS = new ErrorCode(1_100_001_002, "来访日志不存在");

    // ========== 预约访客模块 1-100-001-100 ==========
    ErrorCode RESERVATION_VISITOR_NOT_EXISTS = new ErrorCode(1_100_001_100, "预约访客不存在");
    ErrorCode RESERVATION_VISITOR_ALREADY_CHECKED_IN = new ErrorCode(1_100_001_101, "预约已来访，无法重复操作");
    ErrorCode RESERVATION_VISITOR_ALREADY_CANCELLED = new ErrorCode(1_100_001_102, "预约已取消，无法操作");
    ErrorCode RESERVATION_VISITOR_EXPIRED = new ErrorCode(1_100_001_103, "预约已过期");

    // ========== 访客区域模块 1-100-001-200 ==========
    ErrorCode VISITOR_AREA_NOT_EXISTS = new ErrorCode(1_100_001_200, "访客区域不存在");

    // ========== 住宿模块 1-100-002-000 ==========
    ErrorCode BUILDING_NOT_EXISTS = new ErrorCode(1_100_002_000, "楼栋不存在");
    ErrorCode BUILDING_HAS_FLOORS = new ErrorCode(1_100_002_001, "楼栋下存在楼层，无法删除");
    ErrorCode FLOOR_NOT_EXISTS = new ErrorCode(1_100_002_002, "楼层不存在");
    ErrorCode FLOOR_HAS_ROOMS = new ErrorCode(1_100_002_003, "楼层下存在房间，无法删除");
    ErrorCode ROOM_NOT_EXISTS = new ErrorCode(1_100_002_004, "房间不存在");
    ErrorCode ROOM_NUMBER_DUPLICATE = new ErrorCode(1_100_002_005, "房间编号已存在");
    ErrorCode ROOM_GUEST_NOT_EXISTS = new ErrorCode(1_100_002_006, "住宿记录不存在");
    ErrorCode ROOM_ALREADY_OCCUPIED = new ErrorCode(1_100_002_007, "房间已被占用");
    ErrorCode ROOM_GUEST_ALREADY_CHECKED_OUT = new ErrorCode(1_100_002_008, "已退房，无法重复操作");
    ErrorCode ROOM_GUEST_NOT_ACTIVE = new ErrorCode(1_100_002_009, "住客不在在住状态");
    ErrorCode ROOM_GUEST_NOT_RESERVED = new ErrorCode(1_100_002_010, "住客不在预约状态");
    ErrorCode ROOM_GUEST_EXTEND_DATE_INVALID = new ErrorCode(1_100_002_011, "新预离日期必须晚于当前预离日期");
    ErrorCode ROOM_NOT_VACANT = new ErrorCode(1_100_002_012, "目标房间不是空闲状态");
    ErrorCode STAY_SETTLEMENT_NOT_EXISTS = new ErrorCode(1_100_002_013, "住宿结算记录不存在");
    ErrorCode STAY_SETTLEMENT_ALREADY_SETTLED = new ErrorCode(1_100_002_014, "住宿结算已确认，无法重复操作");
    ErrorCode STAY_SETTLEMENT_ALREADY_INVOICED = new ErrorCode(1_100_002_015, "住宿结算已开票，无法重复操作");
    ErrorCode ROOM_GUEST_IS_CO_GUEST = new ErrorCode(1_100_002_016, "同住人不能再添加同住人");
    ErrorCode ROOM_GUEST_PRIMARY_NOT_EXISTS = new ErrorCode(1_100_002_017, "主住客不存在");

    // ========== 餐饮模块 1-100-003-000 ==========
    ErrorCode DINING_NOT_EXISTS = new ErrorCode(1_100_003_000, "餐饮设置不存在");
    ErrorCode DINING_RECORD_NOT_EXISTS = new ErrorCode(1_100_003_001, "就餐记录不存在");
    ErrorCode DINING_REGISTRATION_NOT_EXISTS = new ErrorCode(1_100_003_001, "报餐登记不存在");
    ErrorCode DINING_SETTLEMENT_NOT_EXISTS = new ErrorCode(1_100_003_002, "餐饮结算不存在");
    ErrorCode DINING_REGISTRATION_STATUS_ERROR = new ErrorCode(1_100_003_003, "报餐状态不正确，无法操作");
    ErrorCode DINING_REGISTRATION_ALREADY_CANCELLED = new ErrorCode(1_100_003_004, "报餐已取消，无法重复操作");
    ErrorCode DINING_SETTLEMENT_ALREADY_SETTLED = new ErrorCode(1_100_003_005, "结算已确认，无法重复操作");
    ErrorCode DINING_PRICE_NOT_EXISTS = new ErrorCode(1_100_003_006, "餐饮价格配置不存在");
    ErrorCode DINING_CARD_NOT_VALID = new ErrorCode(1_100_003_007, "该卡不可用");
    ErrorCode DINING_ALREADY_SWIPED = new ErrorCode(1_100_003_008, "已经刷过卡");
    ErrorCode DINING_NOT_REGISTERED = new ErrorCode(1_100_003_009, "未报餐");

    // ========== 物资模块 1-100-004-000 ==========
    ErrorCode MATERIAL_CATEGORY_NOT_EXISTS = new ErrorCode(1_100_004_000, "物资分类不存在");
    ErrorCode MATERIAL_NOT_EXISTS = new ErrorCode(1_100_004_001, "物资不存在");
    ErrorCode MATERIAL_LOG_NOT_EXISTS = new ErrorCode(1_100_004_002, "物资日志不存在");
    ErrorCode MATERIAL_STOCK_NOT_ENOUGH = new ErrorCode(1_100_004_003, "物资库存不足");
    ErrorCode MATERIAL_LOG_ALREADY_AUDITED = new ErrorCode(1_100_004_004, "物资日志已审核");

    // ========== 库存模块（统一物资+耗材）1-100-005-000 ==========
    ErrorCode INVENTORY_CATEGORY_NOT_EXISTS = new ErrorCode(1_100_009_000, "库存分类不存在");
    ErrorCode INVENTORY_CATEGORY_PARENT_NOT_EXISTS = new ErrorCode(1_100_009_001, "父级分类不存在");
    ErrorCode INVENTORY_CATEGORY_HAS_CHILDREN = new ErrorCode(1_100_009_002, "分类下存在子分类，无法删除");
    ErrorCode INVENTORY_CATEGORY_HAS_ITEMS = new ErrorCode(1_100_009_003, "分类下存在物品，无法删除");
    ErrorCode INVENTORY_ITEM_NOT_EXISTS = new ErrorCode(1_100_009_004, "库存物品不存在");
    ErrorCode INVENTORY_ITEM_CODE_DUPLICATE = new ErrorCode(1_100_009_005, "物品编码已存在");
    ErrorCode INVENTORY_ITEM_HAS_STOCK = new ErrorCode(1_100_009_006, "物品尚有库存，无法删除");
    ErrorCode INVENTORY_STOCK_INSUFFICIENT = new ErrorCode(1_100_009_007, "库存不足");
    ErrorCode INVENTORY_LOG_NOT_EXISTS = new ErrorCode(1_100_009_008, "库存日志不存在");
    ErrorCode INVENTORY_LOG_ALREADY_AUDITED = new ErrorCode(1_100_009_009, "库存日志已审核，无法重复操作");

    // ========== 资产模块 1-100-006-000 ==========
    ErrorCode ASSET_CATEGORY_NOT_EXISTS = new ErrorCode(1_100_006_000, "资产分类不存在");
    ErrorCode ASSET_CATEGORY_HAS_CHILDREN = new ErrorCode(1_100_006_001, "资产分类下存在子分类，无法删除");
    ErrorCode ASSET_NOT_EXISTS = new ErrorCode(1_100_006_002, "资产不存在");
    ErrorCode ASSET_INVENTORY_NOT_ENOUGH = new ErrorCode(1_100_006_003, "资产库存不足");
    ErrorCode ASSET_HOLDER_NOT_EXISTS = new ErrorCode(1_100_006_004, "资产持有记录不存在");
    ErrorCode ASSET_BORROW_NOT_EXISTS = new ErrorCode(1_100_006_005, "资产外借记录不存在");
    ErrorCode ASSET_BORROW_ALREADY_RETURNED = new ErrorCode(1_100_006_006, "资产已归还，无法重复操作");
    ErrorCode ASSET_PURCHASE_NOT_EXISTS = new ErrorCode(1_100_006_007, "资产采购单不存在");
    ErrorCode ASSET_PURCHASE_STATUS_ERROR = new ErrorCode(1_100_006_008, "采购单状态不正确，无法操作");
    ErrorCode ASSET_PURCHASE_ALREADY_SUBMITTED = new ErrorCode(1_100_006_009, "采购单已提交，无法修改");
    ErrorCode ASSET_PURCHASE_ALREADY_AUDITED = new ErrorCode(1_100_006_010, "采购单已审核，无法重复操作");
    ErrorCode ASSET_PURCHASE_ITEM_NOT_EXISTS = new ErrorCode(1_100_006_011, "采购明细不存在");

    // ========== 消防模块 1-100-007-000 ==========
    ErrorCode FIRE_EQUIPMENT_NOT_EXISTS = new ErrorCode(1_100_007_000, "消防设备不存在");

    // ========== 车辆模块 1-100-008-000 ==========
    ErrorCode VEHICLE_NOT_EXISTS = new ErrorCode(1_100_008_000, "车辆不存在");
    ErrorCode VEHICLE_PLATE_DUPLICATE = new ErrorCode(1_100_008_001, "车牌号已存在");
    ErrorCode VEHICLE_USAGE_NOT_EXISTS = new ErrorCode(1_100_008_002, "用车记录不存在");

}
