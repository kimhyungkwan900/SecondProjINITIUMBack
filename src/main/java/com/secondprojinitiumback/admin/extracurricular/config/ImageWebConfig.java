package com.secondprojinitiumback.admin.extracurricular.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class ImageWebConfig implements WebMvcConfigurer {
    @Value("${extracurricularImage}")
    private String extracurricularImage;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String uploadPath = Paths.get(extracurricularImage).toUri().toString();

        registry.addResourceHandler("/images/**").addResourceLocations("file:" + uploadPath);
    }
}
