package com.spidey.openmusicapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.yulichang.annotation.EntityMapping;
import com.spidey.openmusicapi.enums.UserStatus;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.util.Date;

@Data
@FieldNameConstants
@TableName("user")
public class UserDO {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;
    private String password;
    private String avatar;
    private String nickname;
    private String email;
    private String phone;

    @JsonIgnore
    private Long roleId;

    @TableField(exist = false)
    @EntityMapping(thisField = Fields.roleId, joinField = RoleDO.Fields.id)
    private RoleDO role;

    private UserStatus status;
    private Date registeredAt;
}
