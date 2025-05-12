package com.spidey.openmusicapi.controller;

import com.spidey.openmusicapi.common.ApiResponse;
import com.spidey.openmusicapi.entity.LoginUserDetails;
import com.spidey.openmusicapi.entity.ResetPasswordDTO;
import com.spidey.openmusicapi.entity.SignInDTO;
import com.spidey.openmusicapi.entity.UserDO;
import com.spidey.openmusicapi.exception.NotFoundException;
import com.spidey.openmusicapi.exception.UnauthorizedException;
import com.spidey.openmusicapi.service.IUserService;
import com.spidey.openmusicapi.service.TokenService;
import com.spidey.openmusicapi.utils.SecurityUtils;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.spidey.openmusicapi.utils.ControllerUtils.verifyUpdateResult;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final IUserService userService;
    private final TokenService tokenService;

    @Resource
    private final AuthenticationManager authenticationManager;


    @PostMapping("sign-in")
    public ApiResponse<String> login(@RequestBody @Valid SignInDTO dto) {

        Authentication authentication = null;

        try {

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    dto.getUsername(), dto.getPassword());

            authentication = authenticationManager.authenticate(authenticationToken);

        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                throw new UnauthorizedException("用户名或密码错误");
            } else {
                throw new UnauthorizedException(e.getMessage());
            }
        }

        LoginUserDetails userDetails = (LoginUserDetails) authentication.getPrincipal();

        return ApiResponse.success("登录成功", tokenService.generateUserToken(userDetails));
    }

    @PatchMapping("reset-password")
    public ApiResponse<Boolean> resetPassword(@RequestBody @Valid ResetPasswordDTO dto) {
        UserDO user = userService.lambdaQuery()
                .eq(UserDO::getUsername, dto.getUsername())
                .oneOpt()
                .orElseThrow(() -> new NotFoundException("用户不存在"));

        user.setPassword(SecurityUtils.encryptPassword(dto.getPassword()));
        return verifyUpdateResult(userService.updateById(user), "密码重置成功", "密码重置失败");
    }

}
