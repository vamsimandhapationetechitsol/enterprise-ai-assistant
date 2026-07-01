package com.banking.ai.account.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI accountServiceOpenApi() {
        return new OpenAPI().info(new Info()
                .title("BankingAI Account Service API")
                .description("User registration, login, and profile APIs")
                .version("1.0.0")
                .contact(new Contact().name("BankingAI Assistant Team")));
    }
}
