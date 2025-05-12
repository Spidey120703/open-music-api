package com.spidey.openmusicapi.service.impl;

import com.spidey.openmusicapi.entity.LoginUserDetails;
import com.spidey.openmusicapi.entity.UserDO;
import com.spidey.openmusicapi.exception.BadRequestException;
import com.spidey.openmusicapi.exception.UnauthorizedException;
import com.spidey.openmusicapi.service.IAccountService;
import com.spidey.openmusicapi.service.IUserService;
import com.spidey.openmusicapi.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private final IUserService userService;

    @Override
    public UserDO getCurrentUser() {
        LoginUserDetails userDetails = SecurityUtils
                .getLoginUserOrThrow(() -> new UnauthorizedException("用户未登录"));
        return userService.getByIdDeep(userDetails.getUser().getId());
    }

    @Override
    public boolean changePassword(String oldPassword, String newPassword) {
        LoginUserDetails userDetails = SecurityUtils
                .getLoginUserOrThrow(() -> new UnauthorizedException("用户未登录"));

        UserDO user = userService.lambdaQuery()
                .eq(UserDO::getId, userDetails.getUser().getId())
                .oneOpt()
                .orElseThrow(() -> new UnauthorizedException("当前用户不存在"));

        if (! SecurityUtils.getPasswordEncoder()
                .matches(oldPassword, user.getPassword())) {
            throw new BadRequestException("旧密码错误");
        }

        user.setPassword(SecurityUtils.encryptPassword(newPassword));
        return userService.updateById(user);
    }
}
