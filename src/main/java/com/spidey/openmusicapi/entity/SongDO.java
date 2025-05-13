package com.spidey.openmusicapi.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.yulichang.annotation.EntityMapping;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.util.Date;
import java.util.List;

@Data
@FieldNameConstants
@TableName("song")
public class SongDO {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Integer discNumber;

    private Integer trackNumber;

    @NotBlank(message = "歌曲标题不能为空")
    private String title;

    private String cover;

    private String genre;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date releaseDate;

    private String lyric;

    private Integer duration;

    private String bio;

    private Long albumId;

    @TableField(exist = false)
    @EntityMapping(thisField = Fields.albumId, joinField = AlbumDO.Fields.id)
    private AlbumDO album;

    private String albumName;

    private String artistNames;

    @TableField(exist = false)
    private List<ArtistDO> artists;

    private Date createdAt;

    @TableLogic
    private Boolean deleted;
}
