package com.spidey.openmusicapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.yulichang.annotation.EntityMapping;
import com.spidey.openmusicapi.enums.FeedbackStatus;
import com.spidey.openmusicapi.enums.TargetType;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Data
@FieldNameConstants
@TableName("audit")
public class AuditDO {

    @TableId(type = IdType.AUTO)
    private Long id;

    @NotEmpty(message = "类型不能为空")
    private TargetType type;

    private Long targetId;

    @NotEmpty(message = "状态不能为空")
    private FeedbackStatus status;

    @Length(min = 8, max = 255, message = "理由长度必须在 8-255 个字符之间")
    @NotEmpty(message = "理由不能为空")
    private String reason;

    private String images;

    private Long submitterId;

    private Date submittedAt;

    private Long auditorId;

    private Date auditedAt;

    @TableField(exist = false)
    @EntityMapping(thisField = Fields.submitterId, joinField = UserDO.Fields.id)
    private UserDO submitter;

    @TableField(exist = false)
    @EntityMapping(thisField = Fields.auditorId, joinField = UserDO.Fields.id)
    private UserDO auditor;

    /**
     * 反馈的目标，拟定为 PostDO, CommentDO, SharedDO
     */
    @TableField(exist = false)
    private Object target;

}
