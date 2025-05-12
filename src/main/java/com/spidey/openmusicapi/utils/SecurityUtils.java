package com.spidey.openmusicapi.utils;

import com.spidey.openmusicapi.entity.LoginUserDetails;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.function.Supplier;

@UtilityClass
public class SecurityUtils {

    @Getter
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 获取当前授权
     * @return 当前授权
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取当前登录用户
     * @return 当前登录用户
     */
    @Nullable
    public static LoginUserDetails getLoginUser() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        return (LoginUserDetails) authentication.getPrincipal();
    }

    /**
     * 获取当前登录用户，如果未登录则抛出异常
     * @param exceptionSupplier 异常构造器
     * @return 当前登录用户
     * @param <T> 抛出的异常类型
     * @throws T 抛出的异常
     */
    public static <T extends Throwable> LoginUserDetails getLoginUserOrThrow(Supplier<T> exceptionSupplier) throws T {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            throw exceptionSupplier.get();
        }
        return (LoginUserDetails) authentication.getPrincipal();
    }

    /**
     * 加密密码
     * @param password 密码
     * @return 加密后的密码
     */
    public static String encryptPassword(String password)
    {
        return passwordEncoder.encode(password);
    }

}
