package com.spidey.openmusicapi.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.spidey.openmusicapi.enums.FeedbackStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@TableName("audit")
public class AuditProcessDTO {

    @NotNull
    @TableField(value = "status")
    private FeedbackStatus newStatus;

}
