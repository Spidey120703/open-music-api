package com.spidey.openmusicapi.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.util.Date;

@Data
@NoArgsConstructor
@FieldNameConstants
@TableName("artist_song")
public class ArtistSongDO {

    private Long artistId;

    private Long songId;

    private Date associatedAt;

    public ArtistSongDO(Long artistId, Long songId) {
        this.artistId = artistId;
        this.songId = songId;
    }

}
