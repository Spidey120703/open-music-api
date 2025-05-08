package com.spidey.openmusicapi.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.yulichang.annotation.EntityMapping;
import com.spidey.openmusicapi.enums.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Data
@FieldNameConstants
@TableName("user")
public class UserDO {

    @TableId(type = IdType.AUTO)
    private Long id;

    @Length(min = 4, max = 16, message = "用户名长度在4-16位之间")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @TableField(select = false)
    private String password;

    private String avatar;
    private String nickname;

    @Email(message = "邮箱格式不正确")
    private String email;

    @Length(min = 11, max = 11, message = "手机号长度为11位")
    private String phone;

    @JsonIgnore
    private Long roleId;

    @TableField(exist = false)
    @EntityMapping(thisField = Fields.roleId, joinField = RoleDO.Fields.id)
    private RoleDO role;

    private UserStatus status;

    private Date registeredAt;

    @TableLogic
    private Boolean deleted;
}
