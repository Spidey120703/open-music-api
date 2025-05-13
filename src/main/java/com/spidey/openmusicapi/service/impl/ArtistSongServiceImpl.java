package com.spidey.openmusicapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.spidey.openmusicapi.entity.ArtistSongDO;
import com.spidey.openmusicapi.mapper.ArtistSongMapper;
import com.spidey.openmusicapi.service.IArtistSongService;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class ArtistSongServiceImpl extends MPJBaseServiceImpl<ArtistSongMapper, ArtistSongDO> implements IArtistSongService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveArtistIdsBySongId(
            @NonNull Long songId,
            @NonNull Collection<Long> artistIds) {
        // 删除原有的多对多关系
        this.remove(
                new LambdaQueryWrapper<ArtistSongDO>()
                        .eq(ArtistSongDO::getSongId, songId));
        // 保存新的关系
        return this.saveBatch(artistIds
                .stream()
                .map(artistId -> new ArtistSongDO(artistId, songId))
                .toList());
    }

}