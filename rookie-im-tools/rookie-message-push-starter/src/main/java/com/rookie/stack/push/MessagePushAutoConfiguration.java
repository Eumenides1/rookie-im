package com.rookie.stack.push;

import com.rookie.stack.push.implementation.EmailPushServiceImpl;
import com.rookie.stack.push.template.FreemarkerTemplateRenderer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Name：MessagePushAutoConfiguration
 * Author：eumenides
 * Created on: 2024/12/22
 * Description:
 */
@Configuration
@EnableConfigurationProperties(MessagePushProperties.class) // 启用配置类
public class MessagePushAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "message.push.email", name = "enabled", havingValue = "true", matchIfMissing = true)
    public MessagePushService emailPushService(MessagePushProperties properties) {
        // 获取调用方自定义的模板路径
        String customTemplatePath = properties.getTemplatePath();
        FreemarkerTemplateRenderer templateRenderer = new FreemarkerTemplateRenderer(customTemplatePath);
        return new EmailPushServiceImpl(properties.getEmail(), templateRenderer);
    }
}
