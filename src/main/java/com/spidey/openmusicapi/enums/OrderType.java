package com.spidey.openmusicapi.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public record OrderType(@EnumValue @JsonValue String type, String desc) {

    public final static OrderType ASC = new OrderType("ascending", "升序");
    public final static OrderType DESC = new OrderType("descending", "降序");

    /**
     * 根据字符串获取枚举 - 模拟枚举类反序列化
     *
     * @param type 类型
     * @return 枚举对象
     */
    public static OrderType valueOf(String type) {
        if (DESC.type().equalsIgnoreCase(type)
                || DESC.type().substring(0, 4).equalsIgnoreCase(type)) {
            return DESC;
        } else {
            return ASC;
        }
    }

}