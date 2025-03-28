package com.spidey.openmusicapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.spidey.openmusicapi.enums.ElType;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.hibernate.validator.constraints.Length;

@Data
@FieldNameConstants
@TableName("role")
public class RoleDO {

    @TableId(type = IdType.AUTO)
    private Long id;

    @Length(min = 4, max = 16, message = "角色名长度在4-16位之间")
    private String name;

    @Length(min = 0, max = 16, message = "角色描述内容长度在4-16位之间")
    private String description;

    private ElType type;

    @TableLogic
    private Boolean deleted;
}
