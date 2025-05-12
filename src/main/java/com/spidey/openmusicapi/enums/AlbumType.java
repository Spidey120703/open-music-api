package com.spidey.openmusicapi.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AlbumType {
    ALBUM("album", "专辑"),
    SINGLE("single", "单曲"),
    EP("ep", "EP"),
    COMPILATION("compilation", "合辑"),
    LIVE("live", "现场专辑"),
    OTHER("other", "其他");

    @EnumValue
    @JsonValue
    private final String name;
    private final String title;

}
