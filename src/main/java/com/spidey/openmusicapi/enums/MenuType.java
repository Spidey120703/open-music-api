package com.spidey.openmusicapi.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MenuType {
    MENU("menu", "菜单"),
    OPERATION("operation", "操作");

    @EnumValue
    @JsonValue
    private final String type;
    private final String desc;

}
