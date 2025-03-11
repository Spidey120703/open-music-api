package com.spidey.openmusicapi.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderType {

    ASC("asc", "升序"),
    DESC("desc", "降序");

    @EnumValue
    @JsonValue
    private final String type;
    private final String desc;

}
