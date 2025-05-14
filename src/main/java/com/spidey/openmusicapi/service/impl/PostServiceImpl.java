package com.spidey.openmusicapi.service.impl;

import com.github.yulichang.base.MPJBaseServiceImpl;
import com.spidey.openmusicapi.entity.PostDO;
import com.spidey.openmusicapi.mapper.PostMapper;
import com.spidey.openmusicapi.service.IPostService;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl extends MPJBaseServiceImpl<PostMapper, PostDO> implements IPostService {
}