package com.spidey.openmusicapi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.spidey.openmusicapi.common.ApiResponse;
import com.spidey.openmusicapi.common.SFModel;
import com.spidey.openmusicapi.common.SFPage;
import com.spidey.openmusicapi.entity.*;
import com.spidey.openmusicapi.enums.TargetType;
import com.spidey.openmusicapi.exception.BadRequestException;
import com.spidey.openmusicapi.exception.ForbiddenException;
import com.spidey.openmusicapi.exception.NotFoundException;
import com.spidey.openmusicapi.service.IAuditService;
import com.spidey.openmusicapi.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.spidey.openmusicapi.utils.ControllerUtils.*;

@RequiredArgsConstructor
@RequestMapping("/audit")
@RestController
public class AuditController {

    private final IAuditService auditService;

    private void checkPerm(String type) {
        LoginUserDetails userDetails = SecurityUtils.getLoginUserOrThrow();
        if (! userDetails.getPermissions().contains("content:%s:process".formatted(type))) {
            throw new ForbiddenException("您没有权限处理");
        }
    }

    @PreAuthorize("@perm.hasPerm('content:post:list') && @perm.hasPerm('content:comment:list') && @perm.hasPerm('content:shared:list')")
    @GetMapping
    public ApiResponse<SFPage<AuditDO>> getAuditsByPage(@ModelAttribute SFModel model) {
        return getSuccess(
                SFPage.of(
                        auditService.getAuditsByPage(model,
                                ArtistDO.Fields.name,
                                ArtistDO.Fields.nickname,
                                ArtistDO.Fields.bio,
                                ArtistDO.Fields.genre)
                ).extractFrom(model));
    }

    @PreAuthorize("@perm.hasPerm('content:post:list') || @perm.hasPerm('content:comment:list') || @perm.hasPerm('content:shared:list')")
    @GetMapping("type/-/{targetType}")
    public ApiResponse<SFPage<AuditDO>> getAuditsByPage(
            @PathVariable String targetType, @ModelAttribute SFModel model) {
        checkPerm(targetType);
        return getSuccess(
                SFPage.of(
                        auditService.getAuditsByPage(
                                wrapper -> wrapper.eq(AuditDO::getType, targetType),
                                model,
                                ArtistDO.Fields.name,
                                ArtistDO.Fields.nickname,
                                ArtistDO.Fields.bio,
                                ArtistDO.Fields.genre)
                ).extractFrom(model));
    }

    @PreAuthorize("@perm.hasPerm('content:post:get') || @perm.hasPerm('content:comment:get') || @perm.hasPerm('content:shared:get')")
    @GetMapping("{auditId}")
    public ApiResponse<AuditDO> getAuditById(@PathVariable Long auditId) {
        AuditDO audit = checkNull(auditService.getAuditById(auditId), "反馈不存在");
        checkPerm(audit.getType().getValue());
        return getSuccess(audit);
    }

    /**
     * 新增审核记录
     */
    @PostMapping("{targetType}/{auditId}")
    public ApiResponse<Boolean> addFeedback(@PathVariable String targetType, @PathVariable Long auditId, @RequestBody @Validated FeedbackDTO feedback) {
        LoginUserDetails userDetails = SecurityUtils.getLoginUserOrThrow();

        feedback.setId(auditId);
        AuditDO audit;
        try {
            audit = feedback.toAudit(targetType);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("不支持 %s 类型的反馈".formatted(targetType));
        }
        audit.setSubmitterId(userDetails.getUser().getId());

        return verifyCreateResult(auditService.save(audit));
    }

    /**
     * 删除审核记录
     */
    @PreAuthorize("@perm.hasPerm('content:post:delete') || @perm.hasPerm('content:comment:delete') || @perm.hasPerm('content:shared:delete')")
    @DeleteMapping("{auditId}")
    public ApiResponse<Boolean> deleteAudit(@PathVariable Long auditId) {
        AuditDO audit = auditService.getById(auditId);
        if (audit == null) {
            return verifyDeleteResult(false);
        }
        checkPerm(audit.getType().getValue());
        return verifyDeleteResult(auditService.removeById(auditId));
    }

    /**
     * 轮转审核状态
     */
    @PreAuthorize("@perm.hasPerm('content:post:process') || @perm.hasPerm('content:comment:process') || @perm.hasPerm('content:shared:process')")
    @PatchMapping("{auditId}/process")
    public ApiResponse<Boolean> processAudit(@PathVariable Long auditId, @RequestBody @Validated AuditProcessDTO dto) {
        LoginUserDetails userDetails = SecurityUtils.getLoginUserOrThrow();

        AuditDO audit = auditService.getById(auditId);
        if (audit == null) {
            return verifyDeleteResult(false);
        }
        checkPerm(audit.getType().getValue());

        audit.setStatus(dto.getNewStatus());

        audit.setAuditorId(userDetails.getUser().getId());

        return verifyUpdateResult(auditService.updateById(audit));
    }
}