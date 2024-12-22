package com.rookie.stack.push;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Name：MessagePushProperties
 * Author：eumenides
 * Created on: 2024/12/22
 * Description:
 */
@Configuration
@ConfigurationProperties(prefix = "message.push")
@Data
public class MessagePushProperties {
    private EmailProperties email;
    private SmsProperties sms;
    private String templatePath; // 自定义模板路径
    @Data
    public static class EmailProperties {
        private boolean enabled;
        private String host;
        private int port;
        private String username;
        private String password;
        private String from;
        private boolean ssl;
    }
    @Data
    public static class SmsProperties {
        private String provider; // 短信服务商（例如 aliyun、twilio）
        private String apiKey;
        private String apiSecret;
        private String region;
    }
}
