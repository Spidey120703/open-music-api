package com.spidey.openmusicapi.service.impl;

import com.github.yulichang.base.MPJBaseServiceImpl;
import com.spidey.openmusicapi.entity.SongDO;
import com.spidey.openmusicapi.mapper.SongMapper;
import com.spidey.openmusicapi.service.ISongService;
import org.springframework.stereotype.Service;

@Service
public class SongServiceImpl extends MPJBaseServiceImpl<SongMapper, SongDO> implements ISongService {

}