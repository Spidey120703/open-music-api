package com.spidey.openmusicapi.service.impl;

import com.github.yulichang.base.MPJBaseServiceImpl;
import com.spidey.openmusicapi.entity.ArtistDO;
import com.spidey.openmusicapi.mapper.ArtistMapper;
import com.spidey.openmusicapi.service.IArtistService;
import org.springframework.stereotype.Service;

@Service
public class ArtistServiceImpl extends MPJBaseServiceImpl<ArtistMapper, ArtistDO> implements IArtistService {

}