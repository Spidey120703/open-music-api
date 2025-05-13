package com.spidey.openmusicapi.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.spidey.openmusicapi.common.SFModel;
import com.spidey.openmusicapi.entity.AlbumDO;
import com.spidey.openmusicapi.entity.ArtistAlbumDO;
import com.spidey.openmusicapi.entity.ArtistDO;
import com.spidey.openmusicapi.entity.SongDO;
import com.spidey.openmusicapi.mapper.AlbumMapper;
import com.spidey.openmusicapi.service.IAlbumService;
import com.spidey.openmusicapi.utils.SFPageUtils;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class AlbumServiceImpl extends MPJBaseServiceImpl<AlbumMapper, AlbumDO> implements IAlbumService {

    @NonNull
    private MPJLambdaWrapper<AlbumDO> getJoinWrapper() {
        MPJLambdaWrapper<AlbumDO> joinWrapper = new MPJLambdaWrapper<>("album");
        joinWrapper.selectAll(AlbumDO.class, joinWrapper.getAlias())
                // 专辑关联的歌手，多对多
                .selectCollection("artist", ArtistDO.class, AlbumDO::getArtists)
                .leftJoin(ArtistAlbumDO.class, ArtistAlbumDO::getAlbumId, AlbumDO::getId)
                .leftJoin(ArtistDO.class, "artist", ArtistDO::getId, ArtistAlbumDO::getArtistId)
                // 专辑关联的曲目，一对多
                .selectCollection("song", SongDO.class, AlbumDO::getSongs)
                .leftJoin(SongDO.class, "song", SongDO::getAlbumId, AlbumDO::getId);
        return joinWrapper;
    }

    @Override
    public IPage<AlbumDO> getAlbumsByPage(@NonNull SFModel model, String... columns) {
        return this.selectJoinListPage(
                model.toPage(),
                AlbumDO.class,
                SFPageUtils.prepareForJoinListPaging(getJoinWrapper(), model, columns));
    }

    @Override
    public AlbumDO getAlbumById(Long id) {
        return this.selectJoinOne(AlbumDO.class, getJoinWrapper().eq(AlbumDO::getId, id));
    }

}