package com.spidey.openmusicapi.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.spidey.openmusicapi.common.SFModel;
import com.spidey.openmusicapi.entity.CommentDO;
import com.spidey.openmusicapi.mapper.CommentMapper;
import com.spidey.openmusicapi.service.ICommentService;
import com.spidey.openmusicapi.utils.SFPageUtils;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends MPJBaseServiceImpl<CommentMapper, CommentDO> implements ICommentService {

    @Override
    public IPage<CommentDO> getCommentsByPage(
            @NonNull MPJLambdaWrapper<CommentDO> wrapper, @NonNull SFModel model, String... columns) {

        return this.selectJoinListPage(
                model.toPage(),
                CommentDO.class,
                SFPageUtils.prepareForJoinListPaging(wrapper, model, columns));
    }

}