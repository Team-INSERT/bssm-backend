package com.insert.ogbsm.global.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insert.ogbsm.global.error.CustomAuthenticationEntryPoint;
import com.insert.ogbsm.global.jwt.auth.JwtAuth;
import com.insert.ogbsm.global.jwt.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
//@RequiredArgsConstructor
@AllArgsConstructor
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
                        .requestMatchers("/bamboo/admin/**").hasAuthority("ADMIN")
                        .anyRequest().permitAll()
                )
                .exceptionHandling(exceptionHandler -> exceptionHandler.authenticationEntryPoint(new CustomAuthenticationEntryPoint(objectMapper)))
                .apply(new FilterConfig(jwtUtil, jwtAuth));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
