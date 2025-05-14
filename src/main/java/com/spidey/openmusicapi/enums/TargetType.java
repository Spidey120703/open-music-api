package com.spidey.openmusicapi.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TargetType {

    POST("post"),
    COMMENT("comment"),
    SHARED("shared");

    @JsonValue
    @EnumValue
    private final String value;
}
