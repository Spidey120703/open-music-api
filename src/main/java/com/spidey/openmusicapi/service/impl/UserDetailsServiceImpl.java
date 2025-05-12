package com.spidey.openmusicapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.spidey.openmusicapi.entity.LoginUserDetails;
import com.spidey.openmusicapi.entity.UserDO;
import com.spidey.openmusicapi.enums.UserStatus;
import com.spidey.openmusicapi.exception.UnauthorizedException;
import com.spidey.openmusicapi.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        LambdaQueryWrapper<UserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserDO::getUsername, username);
        UserDO user = userService
                .getOneOpt(wrapper)
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在"));

        if (user.getStatus() == UserStatus.FROZEN) {
            throw new UnauthorizedException("用户被冻结");
        } else if (user.getStatus() == UserStatus.DEACTIVATED) {
            throw new UnauthorizedException("用户已注销");
        } else if (user.getStatus() == UserStatus.OTHER) {
            throw new UnauthorizedException("用户状态异常");
        }

        return LoginUserDetails.of(user);
    }

}
