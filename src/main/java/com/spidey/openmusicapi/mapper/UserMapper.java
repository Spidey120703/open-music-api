package com.spidey.openmusicapi.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.spidey.openmusicapi.entity.UserDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends MPJBaseMapper<UserDO> {
}
