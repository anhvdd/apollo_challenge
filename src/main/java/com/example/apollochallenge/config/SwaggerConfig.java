package com.example.apollochallenge.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI myOpenAPI() {
        Info info = new Info()
            .title("API docs")
            .version("1.0")
            .description("API docs");

        return new OpenAPI().info(info);
    }
}
