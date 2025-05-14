package com.spidey.openmusicapi.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.github.yulichang.annotation.EntityMapping;
import com.spidey.openmusicapi.enums.RepliedType;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Data
@FieldNameConstants
@TableName("comment")
public class CommentDO {

    @TableId(type = IdType.AUTO)
    private Long id;

    @Length(min = 1, max = 512, message = "评论内容长度在1-512位之间")
    private String content;

    private Long repliedId;

    private RepliedType repliedType;

    @TableField(exist = false)
    private CommentDO repliedComment;

    @TableField(exist = false)
    private PostDO repliedPost;

    private Long authorId;

    @TableField(exist = false)
    @EntityMapping(thisField = Fields.authorId, joinField = UserDO.Fields.id)
    private UserDO author;

    private Date publishedAt;

    @TableLogic
    private Boolean deleted;
}