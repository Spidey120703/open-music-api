package com.spidey.openmusicapi.entity;

import com.baomidou.mybatisplus.annotation.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.util.Date;
import java.util.List;

@Data
@FieldNameConstants
@TableName("artist")
public class ArtistDO {

    @TableId(type = IdType.AUTO)
    private Long id;

    @NotBlank(message = "歌手名不能为空")
    private String name;

    private String nickname;

    private String cover;

    private String genre;

    private String bio;

    @TableField(exist = false)
    private List<AlbumDO> albums;

    private Date createdAt;

    @TableLogic
    private Boolean deleted;

}
