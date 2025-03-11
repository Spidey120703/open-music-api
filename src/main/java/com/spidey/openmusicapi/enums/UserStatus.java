package com.spidey.openmusicapi.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus {
    ACTIVE("active", "正常", TagType.SUCCESS),
    FROZEN("frozen", "冻结", TagType.WARNING),
    DEACTIVATED("deactivated", "注销", TagType.DANGER),
    OTHER("other", "其他", TagType.INFO);

    @EnumValue
    @JsonValue
    private final String status;
    private final String desc;
    private final TagType type;

}
