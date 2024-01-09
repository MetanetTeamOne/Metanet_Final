package com.metanet.finalproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableScheduling
@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
	    registry.addMapping("/**")
	        .allowedOriginPatterns("*")
	        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE")
	        .allowedHeaders("Authorization", "Content-Type")
	        .exposedHeaders("Custom-Header")
	        .allowCredentials(true)
	        .maxAge(3600);
	}
}
