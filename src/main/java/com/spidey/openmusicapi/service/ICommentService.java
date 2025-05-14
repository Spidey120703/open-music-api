package com.spidey.openmusicapi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.extension.mapping.base.MPJDeepService;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.spidey.openmusicapi.common.SFModel;
import com.spidey.openmusicapi.entity.CommentDO;

public interface ICommentService extends MPJDeepService<CommentDO> {

    IPage<CommentDO> getCommentsByPage(
            MPJLambdaWrapper<CommentDO> wrapper, SFModel model, String... columns);

}