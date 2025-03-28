package com.spidey.openmusicapi.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus {
    ACTIVE("active", "正常", ElType.SUCCESS),
    FROZEN("frozen", "冻结", ElType.WARNING),
    DEACTIVATED("deactivated", "注销", ElType.DANGER),
    OTHER("other", "其他", ElType.INFO);

    @EnumValue
    @JsonValue
    private final String status;
    private final String desc;
    private final ElType type;

}
