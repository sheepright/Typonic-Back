package com.example.typingtestserver.Config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("TYPONIC API 문서")
                        .version("v1.0.0")
                        .description("TYPONIC WebSite OpenAPI 문서입니다.")
                );
    }
}
