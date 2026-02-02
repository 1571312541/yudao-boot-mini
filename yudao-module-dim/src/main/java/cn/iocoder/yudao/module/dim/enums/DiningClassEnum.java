package cn.iocoder.yudao.module.dim.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 餐类分类枚举
 */
@Getter
@AllArgsConstructor
public enum DiningClassEnum {

    GUEST_MEAL(0, "客餐"),
    TABLE_MEAL(1, "桌餐");

    /**
     * 类型值
     */
    private final Integer value;

    /**
     * 类型名称
     */
    private final String name;

    /**
     * 根据值获取枚举
     */
    public static DiningClassEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (DiningClassEnum type : values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }

}
