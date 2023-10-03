package com.insert.ogbsm.infra.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insert.ogbsm.infra.error.CustomAuthenticationEntryPoint;
import com.insert.ogbsm.infra.jwt.auth.JwtAuth;
import com.insert.ogbsm.infra.jwt.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                        .requestMatchers(HttpMethod.POST, "/comment/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/comment/**").authenticated()
                        .requestMatchers("/bamboo/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/bamboo/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/ber").permitAll()
                        .requestMatchers(HttpMethod.POST, "/ber/**").authenticated()
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
