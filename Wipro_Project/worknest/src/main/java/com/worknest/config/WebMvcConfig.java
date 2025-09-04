package com.worknest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * WebMvcConfig - configures view resolver and static resource handling.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.worknest")
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver vr = new InternalResourceViewResolver();
        vr.setPrefix("/WEB-INF/views/");
        vr.setSuffix(".jsp");
        return vr;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // static resources like /resources/css/** should be under src/main/webapp/resources/
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
}
