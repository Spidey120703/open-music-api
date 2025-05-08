package com.spidey.openmusicapi.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.yulichang.annotation.EntityMapping;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.util.Date;

@Data
@FieldNameConstants
@TableName("authority")
public class AuthorityDO {

    @JsonIgnore
    private Long roleId;

    @TableField(exist = false)
    @EntityMapping(thisField = Fields.roleId, joinField = RoleDO.Fields.id)
    private RoleDO role;

    @JsonIgnore
    private Long menuId;

    @TableField(exist = false)
    @EntityMapping(thisField = Fields.menuId, joinField = MenuDO.Fields.id)
    private MenuDO menu;

    private Date authorizedAt;

    public AuthorityDO(Long roleId, Long menuId) {
        this.roleId = roleId;
        this.menuId = menuId;
    }
}
