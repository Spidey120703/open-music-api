package com.spidey.openmusicapi.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.spidey.openmusicapi.enums.FeedbackStatus;
import com.spidey.openmusicapi.enums.TargetType;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.hibernate.validator.constraints.Length;

import static com.spidey.openmusicapi.utils.IdentifierUtils.kebab2Snake;

@Data
@FieldNameConstants
@TableName("audit")
public class FeedbackDTO {

    private Long id;

    @Length(min = 8, max = 255, message = "理由长度必须在 8-255 个字符之间")
    @NotEmpty(message = "理由不能为空")
    private String reason;

    private String images;

    public AuditDO toAudit(String type) {
        AuditDO audit = new AuditDO();
        audit.setType(TargetType.valueOf(kebab2Snake(type)));
        audit.setTargetId(id);
        audit.setReason(reason);
        audit.setImages(images);
        audit.setStatus(FeedbackStatus.PENDING);
        return audit;
    }
}
