package com.spidey.openmusicapi.service.impl;

import com.github.yulichang.base.MPJBaseServiceImpl;
import com.spidey.openmusicapi.entity.ArtistSongDO;
import com.spidey.openmusicapi.mapper.ArtistSongMapper;
import com.spidey.openmusicapi.service.IArtistSongService;
import org.springframework.stereotype.Service;

@Service
public class ArtistSongServiceImpl extends MPJBaseServiceImpl<ArtistSongMapper, ArtistSongDO> implements IArtistSongService {

}