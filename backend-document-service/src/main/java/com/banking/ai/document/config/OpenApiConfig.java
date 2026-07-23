package com.banking.ai.document.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI documentServiceOpenApi() {
        return new OpenAPI().info(new Info()
                .title("BankingAI Document Service API")
                .description("Document metadata CRUD and search APIs")
                .version("1.0.0")
                .contact(new Contact().name("BankingAI Assistant Team")));
    }
}
