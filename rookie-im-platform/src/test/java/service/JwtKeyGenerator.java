package com.rookie.stack.im.service;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Base64;

/**
 * Name：JwtKeyGenerator
 * Author：eumenides
 * Created on: 2024/12/24
 * Description:
 */

public class JwtKeyGenerator {

    public static void main(String[] args) {
        // 生成安全的密钥
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        // 将密钥转换为 Base64 编码字符串
        String base64EncodedKey = Base64.getEncoder().encodeToString(key.getEncoded());

        // 打印密钥
        System.out.println("Generated Key: " + base64EncodedKey);
    }
}
