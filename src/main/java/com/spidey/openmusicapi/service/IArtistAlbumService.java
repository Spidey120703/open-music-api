package com.spidey.openmusicapi.service;

import com.github.yulichang.extension.mapping.base.MPJDeepService;
import com.spidey.openmusicapi.entity.ArtistAlbumDO;
import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface IArtistAlbumService extends MPJDeepService<ArtistAlbumDO> {
    @Transactional(rollbackFor = Exception.class)
    boolean saveArtistIdsByAlbumId(
            @NonNull Long albumId,
            @NonNull Collection<Long> artistIds);
}