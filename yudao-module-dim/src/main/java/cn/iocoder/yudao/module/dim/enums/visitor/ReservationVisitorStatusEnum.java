package cn.iocoder.yudao.module.dim.enums.visitor;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 预约访客状态枚举
 */
@Getter
@AllArgsConstructor
public enum ReservationVisitorStatusEnum {

    PENDING(0, "待来访"),
    CHECKED_IN(1, "已来访"),
    EXPIRED(2, "已过期"),
    CANCELLED(3, "已取消");

    /**
     * 状态值
     */
    private final Integer status;
    /**
     * 状态名
     */
    private final String name;

    /**
     * 根据状态值获取枚举
     */
    public static ReservationVisitorStatusEnum valueOf(Integer status) {
        if (status == null) {
            return null;
        }
        for (ReservationVisitorStatusEnum value : values()) {
            if (value.getStatus().equals(status)) {
                return value;
            }
        }
        return null;
    }

}
