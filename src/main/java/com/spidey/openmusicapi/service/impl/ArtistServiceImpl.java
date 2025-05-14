package com.spidey.openmusicapi.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.spidey.openmusicapi.common.SFModel;
import com.spidey.openmusicapi.entity.AlbumDO;
import com.spidey.openmusicapi.entity.ArtistAlbumDO;
import com.spidey.openmusicapi.entity.ArtistDO;
import com.spidey.openmusicapi.mapper.ArtistMapper;
import com.spidey.openmusicapi.service.IArtistService;
import com.spidey.openmusicapi.utils.SFPageUtils;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class ArtistServiceImpl extends MPJBaseServiceImpl<ArtistMapper, ArtistDO> implements IArtistService {

    @NonNull
    private MPJLambdaWrapper<ArtistDO> getJoinWrapper() {
        MPJLambdaWrapper<ArtistDO> joinWrapper = new MPJLambdaWrapper<>("artist");
        joinWrapper.selectAll(ArtistDO.class, joinWrapper.getAlias())
                // 专辑关联的专辑，多对多
                .selectCollection("album", AlbumDO.class, ArtistDO::getAlbums)
                .leftJoin(ArtistAlbumDO.class, ArtistAlbumDO::getArtistId, ArtistDO::getId)
                .leftJoin(AlbumDO.class, "album", AlbumDO::getId, ArtistAlbumDO::getAlbumId);
        return joinWrapper;
    }

    @Override
    public IPage<ArtistDO> getArtistsByPage(@NonNull SFModel model, String... columns) {
        return this.selectJoinListPage(
                model.toPage(),
                ArtistDO.class,
                SFPageUtils.prepareForJoinListPaging(getJoinWrapper(), model, columns));
    }

    @Override
    public ArtistDO getArtistById(Long id) {
        return this.selectJoinOne(ArtistDO.class, getJoinWrapper().eq(ArtistDO::getId, id));
    }


}