package com.spidey.openmusicapi.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.spidey.openmusicapi.entity.SongDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SongMapper extends MPJBaseMapper<SongDO> {
}