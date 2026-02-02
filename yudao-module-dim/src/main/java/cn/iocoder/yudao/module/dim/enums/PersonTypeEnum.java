package cn.iocoder.yudao.module.dim.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 人员类型枚举
 */
@Getter
@AllArgsConstructor
public enum PersonTypeEnum {

    OUTSOURCING(0, "外协"),
    EXPERIMENT_TEAM(1, "实验队"),
    CONSTRUCTION_TEAM(2, "施工队"),
    PROPERTY(3, "物业"),
    LOCAL_INSTITUTE(4, "本所"),
    HEADQUARTERS(5, "总部");

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
    public static PersonTypeEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (PersonTypeEnum type : values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }

}
