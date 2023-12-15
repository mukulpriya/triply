package com.mukul.triply.config.security;

import com.mukul.triply.config.interceptors.AuthNInterceptor;
import com.mukul.triply.config.interceptors.AuthZInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SecurityConfig implements WebMvcConfigurer {

    final AuthNInterceptor authNInterceptor;

    final AuthZInterceptor authzInterceptor;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(authNInterceptor)
                .excludePathPatterns("/api/v1/users/login", "/api/v1/users/refresh", "/swagger-ui/**", "/v3/api-docs/**");
        registry.addInterceptor(authzInterceptor)
                .excludePathPatterns("/api/v1/users/login", "/api/v1/users/refresh", "/swagger-ui/**", "/v3/api-docs/**");

    }
}
