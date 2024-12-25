package com.rookie.stack.im.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.ExternalDocumentation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Name：OpenAPIConfig
 * Author：eumenides
 * Created on: 2024/12/19
 * Description:
 */
@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("菜鸟 IM 即时通讯产品 API 文档")
                        .description("菜鸟 IM 即时通讯产品 API 文档")
                        .version("v1"))
                .externalDocs(new ExternalDocumentation()
                        .description("菜鸟IM 产品API文档")
                        .url("/"));
    }
}