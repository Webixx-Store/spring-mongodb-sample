package com.example.bot_binnance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Allow all endpoints
                        .allowedOrigins("http://localhost:4200" , "http://192.168.40.45:4200" , "http://localhost:4300" , "https://auth-webixx.web.app" , "https://dattot-vn.web.app" , "https://cryptovuive.web.app" , "https://minhminh-kalivisa.web.app") // Allow specific origin
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow specific HTTP methods
                        .allowedHeaders("*") // Allow all headers
                        .allowCredentials(true); // Allow credentials (cookies, etc.)
            }
        };
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Cấu hình để truy cập thư mục upload
    	registry.addResourceHandler("/upload/product/**").addResourceLocations("file:upload/product/");
    }
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
