package com.nexusbank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class EnvConfig {

    @Bean
    public void loadEnv() {
	Dotenv dotenv = Dotenv.load();
	System.setProperty("GIT_USERNAME", dotenv.get("GIT_USERNAME"));
	System.setProperty("GIT_PASSWORD", dotenv.get("GIT_PASSWORD"));
    }
}
