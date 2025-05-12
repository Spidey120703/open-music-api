package com.spidey.openmusicapi.controller;

import com.spidey.openmusicapi.common.ApiResponse;
import com.spidey.openmusicapi.entity.LoginUserDetails;
import com.spidey.openmusicapi.exception.UnauthorizedException;
import com.spidey.openmusicapi.service.IPermissionService;
import com.spidey.openmusicapi.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final IPermissionService permissionService;

    @PreAuthorize("@perm.hasPerm('perm:user:add')")
    @GetMapping("perm")
    public ApiResponse<List<String>> testPerm() {
        LoginUserDetails userDetails = SecurityUtils.getLoginUser();
        if (Objects.isNull(userDetails)) {
            throw new UnauthorizedException("用户未登录");
        }

        return ApiResponse.success(permissionService.getPermsByRoleId(userDetails.getUser().getRoleId()));
    }

}
