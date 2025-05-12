package com.spidey.openmusicapi.service;

import com.spidey.openmusicapi.entity.LoginUserDetails;
import com.spidey.openmusicapi.entity.UserDO;
import com.spidey.openmusicapi.exception.GlobalException;
import com.spidey.openmusicapi.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Contract;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TokenService {

    private final IUserService userService;
    private final IPermissionService permissionService;

    @Value("${token.header}")
    private String header;

    @Value("${token.secret}")
    private String secret;

    @Value("${token.expiration}")
    private Long expiration;

    /**
     * 生成用户 Token
     * @param userDetails 用户信息
     * @return token
     */
    @NonNull
    public String generateUserToken(@NonNull LoginUserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(Claims.SUBJECT, userDetails.getUsername());

        long currentTimeMillis = System.currentTimeMillis();
        claims.put(Claims.ISSUED_AT, new Date(currentTimeMillis));
        claims.put(Claims.EXPIRATION, new Date(currentTimeMillis + expiration));

        return createToken(claims);
    }

    /**
     * 获取加密密钥
     * @return 加密后的密钥
     */
    @Contract("-> new")
    @NonNull
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 生成 JWT Token
     * @param claims 携带的信息
     * @return token
     */
    public String createToken(Map<String, Object> claims) {
        return Jwts.builder()
                .claims(claims)
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * 解析 JWT Token
     * @param token JWT Token
     * @return 携带的信息
     */
    public Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (IllegalArgumentException e) {
            throw new GlobalException(e.getMessage());
        }
    }

    /**
     * 从请求中获取 Token
     * @param request 请求
     * @return token
     */
    public String getToken(@NonNull HttpServletRequest request) {
        String token = request.getHeader(header);
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return token;
    }

    /**
     * 验证登录用户的 Token 是否有效
     * @param userDetails 登录的用户信息
     * @return 是否有效
     */
    public boolean verifyToken(LoginUserDetails userDetails) {
        Claims claims = parseToken(userDetails.getToken());
        String username = userDetails.getUsername();

        if (!username.equals(claims.getSubject())) {
            throw new UnauthorizedException("无效 Token，请重新登录");
        }

        userService.lambdaQuery()
                .eq(UserDO::getUsername, username)
                .oneOpt()
                .orElseThrow(() -> new UnauthorizedException("用户无效，请重新登录"));

        return claims.getExpiration().after(new Date());
    }

    /**
     * 获取登录用户信息
     * @param request 请求
     * @return 解析的用户信息
     */
    public LoginUserDetails getLoginUser(@NonNull HttpServletRequest request) {
        String token = getToken(request);

        if (StringUtils.isBlank(token)) {
            throw new UnauthorizedException("用户未登录");
        }

        Claims claims = parseToken(token);
        String username = claims.getSubject();
        UserDO user = userService.lambdaQuery()
                .eq(UserDO::getUsername, username)
                .oneOpt()
                .orElseThrow(() -> new UnauthorizedException("用户无效，请重新登录"));

        LoginUserDetails userDetails = LoginUserDetails.of(user);

        userDetails.setToken(token);
        userDetails.setPermissions(permissionService.getPermsByRoleId(user.getRoleId()));

        return userDetails;
    }

}
