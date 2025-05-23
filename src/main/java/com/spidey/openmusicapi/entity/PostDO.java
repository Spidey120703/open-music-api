package com.spidey.openmusicapi.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.github.yulichang.annotation.EntityMapping;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Data
@FieldNameConstants
@TableName("post")
public class PostDO {

    @TableId(type = IdType.AUTO)
    private Long id;

    @Length(min = 1, max = 64, message = "文章标题长度在1-64位之间")
    private String title;

    @Length(min = 1, max = 512, message = "评论内容长度在1-512位之间")
    private String content;

    private Long authorId;

    @TableField(exist = false)
    @EntityMapping(thisField = Fields.authorId, joinField = UserDO.Fields.id)
    private UserDO author;

    private Date publishedAt;

    @TableLogic
    private Boolean deleted;
}
