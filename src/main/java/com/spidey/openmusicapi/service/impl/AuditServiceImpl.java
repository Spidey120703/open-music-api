package com.spidey.openmusicapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.spidey.openmusicapi.common.SFModel;
import com.spidey.openmusicapi.entity.AuditDO;
import com.spidey.openmusicapi.entity.UserDO;
import com.spidey.openmusicapi.mapper.AuditMapper;
import com.spidey.openmusicapi.service.IAuditService;
import com.spidey.openmusicapi.service.ICommentService;
import com.spidey.openmusicapi.service.IPostService;
import com.spidey.openmusicapi.utils.SFPageUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl extends MPJBaseServiceImpl<AuditMapper, AuditDO> implements IAuditService {

    private final IPostService postService;
    private final ICommentService commentService;

    @NonNull
    private MPJLambdaWrapper<AuditDO> getJoinWrapper() {
        MPJLambdaWrapper<AuditDO> joinWrapper = new MPJLambdaWrapper<>("artist");
        joinWrapper.selectAll(AuditDO.class, joinWrapper.getAlias())
                .selectAssociation("submitter", UserDO.class, AuditDO::getSubmitter)
                .leftJoin(UserDO.class, "submitter", UserDO::getId, AuditDO::getSubmitterId)
                .selectAssociation("auditor", UserDO.class, AuditDO::getAuditor)
                .leftJoin(UserDO.class, "auditor", UserDO::getId, AuditDO::getAuditorId);
        return joinWrapper;
    }

    @Override
    public IPage<AuditDO> getAuditsByPage(@NonNull SFModel model, String... columns) {
            return this.selectJoinListPage(
                    model.toPage(),
                    AuditDO.class,
                    SFPageUtils.prepareForJoinListPaging(getJoinWrapper(), model, columns));
    }

    @Override
    public IPage<AuditDO> getAuditsByPage(
            @NonNull Function<MPJLambdaWrapper<AuditDO>, MPJLambdaWrapper<AuditDO>> config,
            SFModel model, String... columns) {
        return this.selectJoinListPage(
                model.toPage(),
                AuditDO.class,
                SFPageUtils.prepareForJoinListPaging(config.apply(getJoinWrapper()), model, columns));
    }

    @Override
    public AuditDO getAuditById(Long id) {
        AuditDO audit = this.selectJoinOne(
                AuditDO.class,
                getJoinWrapper()
                        .eq(AuditDO::getId, id)
        );

        switch (audit.getType()) {
            case POST -> audit.setTarget(postService.getByIdDeep(audit.getTargetId()));
            case COMMENT -> audit.setTarget(commentService.getByIdDeep(audit.getTargetId()));
            case SHARED -> {}
        }

        return audit;
    }

}