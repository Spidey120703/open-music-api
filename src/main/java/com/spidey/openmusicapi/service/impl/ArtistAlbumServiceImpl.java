package com.spidey.openmusicapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.spidey.openmusicapi.entity.ArtistAlbumDO;
import com.spidey.openmusicapi.mapper.ArtistAlbumMapper;
import com.spidey.openmusicapi.service.IArtistAlbumService;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class ArtistAlbumServiceImpl extends MPJBaseServiceImpl<ArtistAlbumMapper, ArtistAlbumDO> implements IArtistAlbumService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveArtistIdsByAlbumId(
            @NonNull Long albumId,
            @NonNull Collection<Long> artistIds) {
        // 删除原有的多对多关系
        this.remove(
                new LambdaQueryWrapper<ArtistAlbumDO>()
                        .eq(ArtistAlbumDO::getAlbumId, albumId));
        // 保存新的关系
        return this.saveBatch(artistIds
                .stream()
                .map(artistId -> new ArtistAlbumDO(artistId, albumId))
                .toList());
    }

}