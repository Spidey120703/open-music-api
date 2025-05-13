package com.spidey.openmusicapi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.extension.mapping.base.MPJDeepService;
import com.spidey.openmusicapi.common.SFModel;
import com.spidey.openmusicapi.entity.AlbumDO;

public interface IAlbumService extends MPJDeepService<AlbumDO> {

    IPage<AlbumDO> getAlbumsByPage(SFModel model, String... columns);

    AlbumDO getAlbumById(Long id);

}