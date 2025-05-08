package com.spidey.openmusicapi.controller;

import com.spidey.openmusicapi.common.ApiResponse;
import com.spidey.openmusicapi.entity.ResetPasswordDTO;
import com.spidey.openmusicapi.entity.SignInDTO;
import com.spidey.openmusicapi.entity.UserDO;
import com.spidey.openmusicapi.enums.UserStatus;
import com.spidey.openmusicapi.exception.ForbiddenException;
import com.spidey.openmusicapi.exception.NotFoundException;
import com.spidey.openmusicapi.exception.UnauthorizedException;
import com.spidey.openmusicapi.service.IUserService;
import com.spidey.openmusicapi.utils.JwtUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.spidey.openmusicapi.utils.ControllerUtils.verifyUpdateResult;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthorizationController {

    private final IUserService userService;

    @PostMapping("sign-in")
    public ApiResponse<String> login(@RequestBody @Valid SignInDTO dto) {
        UserDO user = userService.lambdaQuery()
                .eq(UserDO::getUsername, dto.getUsername())
                .eq(UserDO::getPassword, dto.getPassword())
                .oneOpt()
                .orElseThrow(() -> new UnauthorizedException("用户名或密码错误"));

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new ForbiddenException("用户状态异常");
        }

        return ApiResponse.success("登录成功", JwtUtils.generateToken(user));
    }

    @PatchMapping("reset-password")
    public ApiResponse<Boolean> resetPassword(@RequestBody @Valid ResetPasswordDTO dto) {
        UserDO user = userService.lambdaQuery()
                .eq(UserDO::getUsername, dto.getUsername())
                .oneOpt()
                .orElseThrow(() -> new NotFoundException("用户不存在"));

        user.setPassword(dto.getPassword());
        return verifyUpdateResult(userService.updateById(user), "密码重置成功", "密码重置失败");
    }

}
