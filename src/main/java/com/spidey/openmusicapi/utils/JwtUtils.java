package com.spidey.openmusicapi.utils;

import com.spidey.openmusicapi.entity.UserDO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.experimental.UtilityClass;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Getter
@UtilityClass
public class JwtUtils {

    private final String secret = "openmusic.spidey.io@120703:Marvel2025./";

    private final Long expiration = 1000L * 60 * 60 * 24 * 7;

    /**
     * 获取加密密钥
     * @return 加密后的密钥
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 生成token
     * @param user 用户信息
     * @return 用户的token
     */
    public String generateToken(UserDO user) {
        Map<String, ?> claims = new HashMap<>();
        return createToken(claims, user.getUsername());
    }

    /**
     * 构建并签名JWT令牌
     * @param claims JWT负载中的声明键值对，用于存放用户角色/权限等附加信息
     * @param subject JWT主体标识符，通常是用户的唯一标识符，如用户名、ID等
     * @return 使用HMAC-SHA算法签名后的完整JWT字符串
     * @see Keys#hmacShaKeyFor(byte[]) 签名算法的实现
     */
    private String createToken(Map<String, ?> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * 验证JWT令牌是否有效
     * @param token JWT令牌
     * @param user 用户信息
     * @return true表示令牌有效，false表示令牌无效
     */
    public Boolean validateToken(String token, UserDO user) {
        final String username = extractUsername(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

    /**
     * 从JWT令牌中提取用户名
     * @param token JWT令牌
     * @return 用户名
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * 从JWT令牌中提取过期时间
     * @param token
     * @return
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * 从JWT令牌中提取指定类型的声明
     * @param token JWT令牌
     * @param claimsResolver 声明解析器，用于从JWT令牌中提取声明
     * @return 指定类型的声明
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = parseToken(token);
        if (claims == null) {
            return null;
        }
        return claimsResolver.apply(claims);
    }

    /**
     * 从JWT令牌中提取所有声明
     * @param token JWT令牌
     * @return 所有声明的Claims对象
     */
    private Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (MalformedJwtException e) {
            return null;
        }
    }

    /**
     * 检查JWT令牌是否已过期
     * @param token JWT令牌
     * @return true表示令牌已过期，false表示令牌未过期
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}