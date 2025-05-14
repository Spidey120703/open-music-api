package com.spidey.openmusicapi.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FeedbackStatus {

    PENDING("pending", "待审核"),
    PROCESSING("processing", "处理中"),
    RESOLVED("resolved", "已处理"),
    REJECTED("rejected", "被拒绝"),
    CLOSED("closed", "已关闭");

    @JsonValue
    @EnumValue
    private final String value;
    private final String label;
}
