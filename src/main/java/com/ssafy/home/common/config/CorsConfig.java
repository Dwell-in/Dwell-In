package com.ssafy.home.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 백엔드, 프론트엔드 분리 시 (react나 view 쓸 때 서버 다르게 사용할 시) CORS 에러 해결을 위한 config
@Configuration
public class CorsConfig implements WebMvcConfigurer {
	private static final String DEVELOP_FRONT_ADDRESS1 = "http://localhost:8080";
	private static final String DEVELOP_FRONT_ADDRESS2 = "https://dwell-in.github.io";
	
	@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins(DEVELOP_FRONT_ADDRESS1, DEVELOP_FRONT_ADDRESS2)
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .exposedHeaders("location")
            .allowedHeaders("*")
            .allowCredentials(true);
    }
}
