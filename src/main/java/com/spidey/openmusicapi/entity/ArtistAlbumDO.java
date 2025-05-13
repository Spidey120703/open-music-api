package com.spidey.openmusicapi.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.util.Date;

@Data
@NoArgsConstructor
@FieldNameConstants
@TableName("artist_album")
public class ArtistAlbumDO {

    private Long artistId;

    private Long albumId;

    private Date associatedAt;

    public ArtistAlbumDO(Long artistId, Long albumId) {
        this.artistId = artistId;
        this.albumId = albumId;
    }

}
