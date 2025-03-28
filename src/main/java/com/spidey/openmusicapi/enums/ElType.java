package com.spidey.openmusicapi.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ElementPlus Color Variant Type
 */
@Getter
@AllArgsConstructor
public enum ElType {
    PRIMARY("primary"),
    SUCCESS("success"),
    INFO("info"),
    WARNING("warning"),
    DANGER("danger"),
    DEFAULT("");

    @EnumValue
    @JsonValue
    private final String type;

}
