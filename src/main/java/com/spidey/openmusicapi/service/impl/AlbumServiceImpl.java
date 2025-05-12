package com.spidey.openmusicapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.spidey.openmusicapi.entity.AlbumDO;
import com.spidey.openmusicapi.mapper.AlbumMapper;
import com.spidey.openmusicapi.service.IAlbumService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumServiceImpl extends MPJBaseServiceImpl<AlbumMapper, AlbumDO> implements IAlbumService {

}