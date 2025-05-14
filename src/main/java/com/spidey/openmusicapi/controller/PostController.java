package com.spidey.openmusicapi.controller;

import com.spidey.openmusicapi.common.ApiResponse;
import com.spidey.openmusicapi.common.SFModel;
import com.spidey.openmusicapi.common.SFPage;
import com.spidey.openmusicapi.entity.LoginUserDetails;
import com.spidey.openmusicapi.entity.PostDO;
import com.spidey.openmusicapi.exception.ForbiddenException;
import com.spidey.openmusicapi.service.IPostService;
import com.spidey.openmusicapi.utils.SFPageUtils;
import com.spidey.openmusicapi.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static com.spidey.openmusicapi.utils.ControllerUtils.*;

@RequiredArgsConstructor
@RequestMapping("/post")
@RestController
public class PostController {

    private final IPostService postService;

    @GetMapping
    public ApiResponse<SFPage<PostDO>> getPostsByPage(@ModelAttribute SFModel model) {
        return getSuccess(
                SFPageUtils.doPageDeep(
                        postService,
                        model,
                        PostDO.Fields.authorId,
                        PostDO.Fields.content,
                        PostDO.Fields.publishedAt));
    }

    @PostMapping
    public ApiResponse<Boolean> addPost(@RequestBody @Validated PostDO post) {
        LoginUserDetails userDetails = SecurityUtils.getLoginUserOrThrow();

        post.setAuthorId(userDetails.getUser().getId());

        return verifyCreateResult(postService.save(post));
    }

    @DeleteMapping("{postId}")
    public ApiResponse<Boolean> deletePost(@PathVariable Long postId) {
        LoginUserDetails userDetails = SecurityUtils.getLoginUserOrThrow();

        if (! Objects.equals(postService.getById(postId).getAuthorId(), userDetails.getUser().getId())) {
            throw new ForbiddenException("用户无权限删除此动态");
        }

        return verifyDeleteResult(postService.removeById(postId));
    }
}