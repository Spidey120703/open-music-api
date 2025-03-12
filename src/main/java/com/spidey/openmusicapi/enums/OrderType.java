package com.spidey.openmusicapi.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderType {

    public final static OrderType ASC = new OrderType("asc", "升序");
    public final static OrderType DESC = new OrderType("desc", "降序");

    @EnumValue
    @JsonValue
    private final String type;
    private final String desc;

    public static OrderType valueOf(String type) {
        if (DESC.getType().equalsIgnoreCase(type)) {
            return DESC;
        } else {
            return ASC;
        }
    }

}
