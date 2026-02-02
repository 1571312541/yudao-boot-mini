package cn.iocoder.yudao.module.dim.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 资产采购状态枚举
 */
@Getter
@AllArgsConstructor
public enum AssetPurchaseStatusEnum {

    DRAFT(0, "待提交"),
    PENDING(1, "待审核"),
    APPROVED(2, "已通过"),
    REJECTED(3, "已拒绝"),
    WAREHOUSED(4, "已入库");

    /**
     * 状态值
     */
    private final Integer value;

    /**
     * 状态名称
     */
    private final String name;

    /**
     * 根据值获取枚举
     */
    public static AssetPurchaseStatusEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (AssetPurchaseStatusEnum status : values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        return null;
    }

}
