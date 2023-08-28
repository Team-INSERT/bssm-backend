package com.insert.ogbsm.global.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insert.ogbsm.domain.user.authority.Authority;
import com.insert.ogbsm.domain.user.role.Role;
import com.insert.ogbsm.global.error.CustomAuthenticationEntryPoint;
import com.insert.ogbsm.global.jwt.auth.JwtAuth;
import com.insert.ogbsm.global.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsUtils;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private ObjectMapper objectMapper;
    private final JwtUtil jwtUtil;
    private final JwtAuth jwtAuth;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/comment/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/comment/**").authenticated()
                        .anyRequest().permitAll()
                )
                .exceptionHandling(exceptionHandler -> exceptionHandler.authenticationEntryPoint(new CustomAuthenticationEntryPoint(objectMapper)))
                .apply(new FilterConfig(jwtUtil, jwtAuth));

        return http.build();
    }
}
