package com.spidey.openmusicapi.service.impl;

import com.github.yulichang.base.MPJBaseServiceImpl;
import com.spidey.openmusicapi.entity.UserDO;
import com.spidey.openmusicapi.mapper.UserMapper;
import com.spidey.openmusicapi.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends MPJBaseServiceImpl<UserMapper, UserDO> implements IUserService {

}
