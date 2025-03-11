package com.spidey.openmusicapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class PostDO {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String content;
    private UserDO user;
    private Date publishedAt;

}
