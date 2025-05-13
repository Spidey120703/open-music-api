package com.spidey.openmusicapi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.extension.mapping.base.MPJDeepService;
import com.spidey.openmusicapi.common.SFModel;
import com.spidey.openmusicapi.entity.SongDO;
import lombok.NonNull;

public interface ISongService extends MPJDeepService<SongDO> {
    IPage<SongDO> getSongsByPage(@NonNull SFModel model, String... columns);

    SongDO getSongById(Long id);
}