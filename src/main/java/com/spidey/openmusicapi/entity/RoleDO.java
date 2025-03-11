package com.spidey.openmusicapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.spidey.openmusicapi.enums.TagType;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

@Data
@FieldNameConstants
@TableName("role")
public class RoleDO {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;
    private TagType type;

}
