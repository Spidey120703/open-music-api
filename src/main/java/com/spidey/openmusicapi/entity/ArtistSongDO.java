package com.spidey.openmusicapi.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.yulichang.annotation.EntityMapping;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.util.Date;

@Data
@FieldNameConstants
@TableName("artist_song")
public class ArtistSongDO {

    private Long artistId;

    private Long songId;

    private Date associatedAt;

}
