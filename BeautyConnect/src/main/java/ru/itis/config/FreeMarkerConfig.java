package ru.itis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class FreeMarkerConfig {

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setTemplateLoaderPath("classpath:/templates/");

        Map<String, Object> variables = new HashMap<>();
        variables.put("dateFormatter", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        configurer.setFreemarkerVariables(variables);

        return configurer;
    }
}

