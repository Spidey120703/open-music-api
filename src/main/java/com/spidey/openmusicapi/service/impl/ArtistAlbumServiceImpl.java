package com.spidey.openmusicapi.service.impl;

import com.github.yulichang.base.MPJBaseServiceImpl;
import com.spidey.openmusicapi.entity.ArtistAlbumDO;
import com.spidey.openmusicapi.mapper.ArtistAlbumMapper;
import com.spidey.openmusicapi.service.IArtistAlbumService;
import org.springframework.stereotype.Service;

@Service
public class ArtistAlbumServiceImpl extends MPJBaseServiceImpl<ArtistAlbumMapper, ArtistAlbumDO> implements IArtistAlbumService {

}