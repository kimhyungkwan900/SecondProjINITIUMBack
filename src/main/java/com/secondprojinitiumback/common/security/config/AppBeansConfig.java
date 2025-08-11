package com.secondprojinitiumback.common.security.config;

import com.secondprojinitiumback.common.converter.LocalDateToChar8Converter;
import org.modelmapper.ModelMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppBeansConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public LocalDateToChar8Converter localDateToChar8Converter() {
        return new LocalDateToChar8Converter();
    }
}