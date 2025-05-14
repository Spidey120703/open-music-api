package com.spidey.openmusicapi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.extension.mapping.base.MPJDeepService;
import com.spidey.openmusicapi.common.SFModel;
import com.spidey.openmusicapi.entity.ArtistDO;
import lombok.NonNull;

public interface IArtistService extends MPJDeepService<ArtistDO> {

    IPage<ArtistDO> getArtistsByPage(@NonNull SFModel model, String... columns);

    ArtistDO getArtistById(Long id);

}