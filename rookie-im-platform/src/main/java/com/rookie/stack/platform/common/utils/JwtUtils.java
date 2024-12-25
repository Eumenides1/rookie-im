package com.rookie.stack.platform.common.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Name：JwtUtils
 * Author：eumenides
 * Created on: 2024/12/24
 * Description:
 */
@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    private final long ACCESS_TOKEN_EXPIRATION = 3600000; // 1 小时

    // 生成 Access Token
    public String generateAccessToken(Long userId, String username) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("username", username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // 生成 Refresh Token
    public String generateRefreshToken(Long userId) {
        // 7 天
        long REFRESH_TOKEN_EXPIRATION = 604800000;
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // 获取 Access Token 的过期时间
    public long getAccessTokenExpiration() {
        return ACCESS_TOKEN_EXPIRATION / 1000; // 转换为秒
    }
}
