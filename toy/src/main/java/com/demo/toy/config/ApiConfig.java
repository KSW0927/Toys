package com.demo.toy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApiConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") 										// /api 이하 모든 요청에 CORS 허용
                .allowedOrigins("http://localhost:3000")					// React 개발 서버 주소
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") 	// 허용할 메소드
                .allowedHeaders("*") 										// 모든 헤더 허용
                .allowCredentials(true); 									// 쿠키 등 인증정보 허용 (필요 없으면 빼도 됨)
    }
}
