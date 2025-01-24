package com.rookie.stack.platform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.SecureRandom;

/**
 * Name：SecureRandomConfig
 * Author：eumenides
 * Created on: 2024/12/28
 * Description:
 */
@Configuration
public class SecureRandomConfig {

    @Bean
    public SecureRandom secureRandom() {
        return new SecureRandom();
    }
}
