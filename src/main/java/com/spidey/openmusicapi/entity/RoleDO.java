package com.spidey.openmusicapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.spidey.openmusicapi.enums.ElType;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Data
@FieldNameConstants
@TableName("role")
public class RoleDO {

    @TableId(type = IdType.AUTO)
    private Long id;

    @Length(min = 3, max = 16, message = "角色名长度在3-16位之间")
    private String name;

    @Length(min = 3, max = 16, message = "角色标签内容长度在3-16位之间")
    private String label;

    private ElType type;

    private Date createdAt;

    @TableLogic
    private Boolean deleted;
}
