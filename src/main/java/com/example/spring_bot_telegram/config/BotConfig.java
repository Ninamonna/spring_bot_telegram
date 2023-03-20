package com.example.spring_bot_telegram.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@PropertySource("application.properties")
@Data
public class BotConfig {

    @Value("${bot.name}")
    String name;

    @Value("${bot.token}")
    String token;

    @Value("${bot.owner}")
    long owner;
}
