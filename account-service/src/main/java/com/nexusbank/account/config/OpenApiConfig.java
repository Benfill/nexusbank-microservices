package com.nexusbank.account.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
	return new OpenAPI().info(new Info().title("NexuxBank Account Service API Documentation")
		.description("Manage nexusbank accounts").version("1.0.0")
		.contact(new Contact().name("Benfill").url("https://benfill.vercel.app/").email("benfianass@gmail.com"))
		.license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}
