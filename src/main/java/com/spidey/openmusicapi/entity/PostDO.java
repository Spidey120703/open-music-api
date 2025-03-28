package com.spidey.openmusicapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
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

    @Length(min = 1, max = 512, message = "文章长度在1-512位之间")
    private String content;
    private UserDO user;
    private Date publishedAt;

    @TableLogic
    private Boolean deleted;
}
