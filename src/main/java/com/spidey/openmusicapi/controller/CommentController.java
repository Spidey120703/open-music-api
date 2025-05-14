package com.spidey.openmusicapi.controller;

import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.spidey.openmusicapi.common.ApiResponse;
import com.spidey.openmusicapi.common.SFModel;
import com.spidey.openmusicapi.common.SFPage;
import com.spidey.openmusicapi.entity.CommentDO;
import com.spidey.openmusicapi.entity.LoginUserDetails;
import com.spidey.openmusicapi.entity.PostDO;
import com.spidey.openmusicapi.enums.RepliedType;
import com.spidey.openmusicapi.exception.BadRequestException;
import com.spidey.openmusicapi.exception.ForbiddenException;
import com.spidey.openmusicapi.service.ICommentService;
import com.spidey.openmusicapi.utils.SFPageUtils;
import com.spidey.openmusicapi.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static com.spidey.openmusicapi.utils.ControllerUtils.*;
import static com.spidey.openmusicapi.utils.IdentifierUtils.kebab2Snake;

@RequiredArgsConstructor
@RequestMapping("/comment")
@RestController
public class CommentController {

    private final ICommentService commentService;

    @GetMapping
    public ApiResponse<SFPage<CommentDO>> getCommentsByPage(@ModelAttribute SFModel model) {
        return getSuccess(
                SFPageUtils.doPageDeep(
                        commentService,
                        model,
                        CommentDO.Fields.authorId,
                        CommentDO.Fields.repliedId,
                        CommentDO.Fields.content,
                        CommentDO.Fields.publishedAt));
    }

    @GetMapping("from/{repliedType}/{repliedId}")
    public ApiResponse<SFPage<CommentDO>> getCommentsByCommentId(
            @PathVariable String repliedType, @PathVariable Long repliedId, @ModelAttribute SFModel model) {
        RepliedType type;
        try {
            type = RepliedType.valueOf(kebab2Snake(repliedType));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("不支持评论 %s 类型".formatted(repliedType));
        }

        MPJLambdaWrapper<CommentDO> wrapper = new MPJLambdaWrapper<>();
        wrapper
                .eq(CommentDO::getRepliedType, type)
                .eq(CommentDO::getRepliedId, repliedId);

        switch (type) {
            case POST -> wrapper
                    .selectAssociation("post", PostDO.class, CommentDO::getRepliedPost)
                    .leftJoin(PostDO.class, "post", PostDO::getId, CommentDO::getRepliedId);
            case COMMENT -> wrapper
                    .selectAssociation("comment", CommentDO.class, CommentDO::getRepliedComment)
                    .leftJoin(CommentDO.class, "comment", CommentDO::getId, CommentDO::getRepliedId);
        }

        return getSuccess(
                SFPage.of(
                        commentService.getCommentsByPage(
                                wrapper,
                                model,
                                CommentDO.Fields.authorId,
                                CommentDO.Fields.repliedId,
                                CommentDO.Fields.content,
                                CommentDO.Fields.publishedAt)
                ).extractFrom(model));
    }

    @PostMapping("on/{repliedType}/{repliedId}")
    public ApiResponse<Boolean> addComment(
            @PathVariable String repliedType, @PathVariable Long repliedId, @RequestBody @Validated CommentDO comment) {

        RepliedType type;
        try {
            type = RepliedType.valueOf(kebab2Snake(repliedType));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("不支持评论 %s 类型".formatted(repliedType));
        }

        LoginUserDetails userDetails = SecurityUtils.getLoginUserOrThrow();

        comment.setAuthorId(userDetails.getUser().getId());
        comment.setRepliedType(type);
        comment.setRepliedId(repliedId);

        return verifyCreateResult(commentService.save(comment));
    }

    @DeleteMapping("{commentId}")
    public ApiResponse<Boolean> deleteComment(@PathVariable Long commentId) {
        LoginUserDetails userDetails = SecurityUtils.getLoginUserOrThrow();

        if (! Objects.equals(commentService.getById(commentId).getAuthorId(), userDetails.getUser().getId())) {
            throw new ForbiddenException("用户无权限删除此评论");
        }

        return verifyDeleteResult(commentService.removeById(commentId));
    }
}