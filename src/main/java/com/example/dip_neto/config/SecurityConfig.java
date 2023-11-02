package com.example.dip_neto.config;

import com.example.dip_neto.jwtoken.AuthEntryPoint;
import com.example.dip_neto.jwtoken.JasonWebTokenConfig;
import com.example.dip_neto.jwtoken.TokenCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final TokenCreator tokenCreator;
    private final AuthEntryPoint jwtAuthEntryPoint;
    private static final String FRONT_ORIGIN = "http://localhost:5000";
    private static final String HEADER_TOKEN = "auth-token";
    private static final String LOGIN_ENDPOINT = "/login";

    private static final List<String> METHODS = List.of("GET", "PUT", "POST", "DELETE", "OPTIONS");

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthEntryPoint)
                .and()
                .authorizeHttpRequests()
                .requestMatchers(LOGIN_ENDPOINT).permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new JasonWebTokenConfig(tokenCreator))
                .and()
                .logout()
                .clearAuthentication(true)
                .deleteCookies(HEADER_TOKEN);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final var cors = new CorsConfiguration();
        cors.setAllowedOriginPatterns(List.of("*"));
        cors.setAllowedOrigins(List.of(FRONT_ORIGIN));
        cors.setAllowedMethods(METHODS);
        cors.setAllowedHeaders(List.of("*"));
        cors.setAllowCredentials(true);

        final var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cors);

        return source;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}