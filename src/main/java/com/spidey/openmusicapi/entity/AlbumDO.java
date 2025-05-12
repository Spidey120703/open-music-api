package com.spidey.openmusicapi.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.yulichang.annotation.EntityMapping;
import com.spidey.openmusicapi.enums.AlbumType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@FieldNameConstants
@TableName("album")
public class AlbumDO {

    @TableId(type = IdType.AUTO)
    private Long id;

    @NotBlank(message = "专辑标题不能为空")
    private String title;

    private String cover;

    private String genre;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date releaseDate;

    private Integer totalDiscs;

    private Integer totalTracks;

    private Integer duration;

    private String bio;

    private AlbumType type;

    @TableField(exist = false)
    private List<ArtistDO> artists;

    @TableField(exist = false)
    @EntityMapping(thisField = Fields.id, joinField = SongDO.Fields.albumId)
    private List<SongDO> songs;

    private Date createdAt;

    @TableLogic
    private Boolean deleted;

}
