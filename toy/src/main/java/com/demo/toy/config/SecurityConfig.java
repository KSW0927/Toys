package com.demo.toy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.demo.toy.common.jwt.JwtAuthenticationFilter;
import com.demo.toy.common.jwt.JwtTokenProvider;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtTokenProvider jwtTokenProvider) throws Exception {
	    http
	        // CSRF 비활성화 (REST API용)
	        .csrf(csrf -> csrf.disable())

	        // 요청 권한 설정
	        .authorizeHttpRequests(auth -> auth
	            // 로그인, swagger, API 문서 등은 인증 없이 허용
//	            .requestMatchers("/api/users/login", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
	            .requestMatchers("/api/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
	            // 나머지 요청은 인증 필요
	            .anyRequest().authenticated()
	        )

	        // JWT 필터 추가
	        .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), 
	                         UsernamePasswordAuthenticationFilter.class);

	    return http.build();
	}
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
