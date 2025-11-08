package com.demo.toy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.demo.toy.common.jwt.JwtAuthenticationFilter;
import com.demo.toy.common.jwt.JwtTokenProvider;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

import org.springframework.web.client.RestTemplate;

@Configuration
public class SecurityConfig {

	 @Bean
	 public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtTokenProvider jwtTokenProvider) throws Exception {
	     http
	     	.cors(cors -> cors.configurationSource(corsConfigurationSource()))
	     	.csrf(csrf -> csrf.disable())
	        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) 
	        .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
	        .authorizeHttpRequests(auth -> auth
	        		
	        // 비로그인 허용
	        .requestMatchers("/api/users/**", "/swagger-ui/**").permitAll()
	        .requestMatchers(HttpMethod.GET, "/api/data/pictures").permitAll()
	        
	        // 정적 리소스 허용
	        .requestMatchers("/uploads/**").permitAll()
	        
	        // 비로그인 미허용
	        .requestMatchers("/api/data/pictures/**").authenticated()
	        .anyRequest().authenticated()
	         );
	     return http.build();
	 }
 
	 @Bean
	 public CorsConfigurationSource corsConfigurationSource() {
	     CorsConfiguration configuration = new CorsConfiguration();
	     
	     configuration.setAllowedOrigins(List.of("http://localhost:3000")); 
	     configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	     configuration.setAllowedHeaders(List.of("*"));
	     configuration.setAllowCredentials(true); 
	
	     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	     source.registerCorsConfiguration("/**", configuration);
	     return source;
	 }
 
	 @Bean
	 public PasswordEncoder passwordEncoder() {
	     return new BCryptPasswordEncoder();
	 }
 
	@Bean
	public RestTemplate restTemplate() {
	   return new RestTemplate();
	}
}
