package com.spidey.openmusicapi.service.impl;

import com.github.yulichang.base.MPJBaseServiceImpl;
import com.spidey.openmusicapi.entity.RoleDO;
import com.spidey.openmusicapi.mapper.RoleMapper;
import com.spidey.openmusicapi.service.IRoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends MPJBaseServiceImpl<RoleMapper, RoleDO> implements IRoleService {
}
