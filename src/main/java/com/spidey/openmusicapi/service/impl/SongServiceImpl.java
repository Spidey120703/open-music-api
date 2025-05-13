package com.spidey.openmusicapi.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.spidey.openmusicapi.common.SFModel;
import com.spidey.openmusicapi.entity.AlbumDO;
import com.spidey.openmusicapi.entity.ArtistDO;
import com.spidey.openmusicapi.entity.ArtistSongDO;
import com.spidey.openmusicapi.entity.SongDO;
import com.spidey.openmusicapi.mapper.SongMapper;
import com.spidey.openmusicapi.service.ISongService;
import com.spidey.openmusicapi.utils.SFPageUtils;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class SongServiceImpl extends MPJBaseServiceImpl<SongMapper, SongDO> implements ISongService {

    @NonNull
    private MPJLambdaWrapper<SongDO> getJoinWrapper() {
        MPJLambdaWrapper<SongDO> joinWrapper = new MPJLambdaWrapper<>("song");
        joinWrapper.selectAll(SongDO.class, joinWrapper.getAlias())
                // 专辑关联的歌手，多对多
                .selectCollection("artist", ArtistDO.class, SongDO::getArtists)
                .leftJoin(ArtistSongDO.class, ArtistSongDO::getSongId, SongDO::getId)
                .leftJoin(ArtistDO.class, "artist", ArtistDO::getId, ArtistSongDO::getArtistId)
                // 专辑关联的曲目，一对多
                .selectAssociation("album", AlbumDO.class, SongDO::getAlbum)
                .leftJoin(AlbumDO.class, "album", AlbumDO::getId, SongDO::getAlbumId);
        return joinWrapper;
    }

    @Override
    public IPage<SongDO> getSongsByPage(@NonNull SFModel model, String... columns) {
        return this.selectJoinListPage(
                model.toPage(),
                SongDO.class,
                SFPageUtils.prepareForJoinListPaging(getJoinWrapper(), model, columns));
    }

    @Override
    public SongDO getSongById(Long id) {
        return this.selectJoinOne(SongDO.class, getJoinWrapper().eq(SongDO::getId, id));
    }


}