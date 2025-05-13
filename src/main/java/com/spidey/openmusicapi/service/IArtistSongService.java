package com.spidey.openmusicapi.service;

import com.github.yulichang.extension.mapping.base.MPJDeepService;
import com.spidey.openmusicapi.entity.ArtistSongDO;
import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface IArtistSongService extends MPJDeepService<ArtistSongDO> {
    @Transactional(rollbackFor = Exception.class)
    boolean saveArtistIdsBySongId(
            @NonNull Long songId,
            @NonNull Collection<Long> artistIds);
}