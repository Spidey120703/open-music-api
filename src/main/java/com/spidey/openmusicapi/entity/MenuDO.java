package com.spidey.openmusicapi.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.github.yulichang.annotation.EntityMapping;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.Date;
import java.util.List;

@Data
@FieldNameConstants
@TableName("menu")
public class MenuDO {

    @TableId(type = IdType.AUTO)
    private Long id;

    @NotBlank(message = "菜单名不能为空")
    @UniqueElements(message = "菜单名已存在")
    private String name;

    @NotBlank(message = "路由不能为空")
    private String route;
    private String icon;
    private String title;
    private Boolean hidden;
    private Long parentId;

    @TableField(exist = false)
    @EntityMapping(thisField = Fields.parentId, joinField = Fields.id)
    private MenuDO parent;

    @TableField(exist = false)
    @EntityMapping(thisField = Fields.id, joinField = Fields.parentId)
    private List<MenuDO> children;

    @TableField(exist = false)
    private Boolean hasChildren;

    @TableField(fill = FieldFill.INSERT)
    private Date createdAt;

    @TableLogic
    private Boolean deleted;
}
