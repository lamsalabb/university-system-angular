package com.university.core.security;

import com.university.core.security.permissions.PermissionLoader;
import com.university.core.security.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtUtil jwtUtil, PermissionLoader permissionLoader) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> {})
                .authorizeHttpRequests(
                        configurer -> configurer

                                .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                                .requestMatchers("/api/auth/**").permitAll()

                                .anyRequest().authenticated()
                )
                .addFilterBefore(
                        new JwtAuthFilter(jwtUtil,permissionLoader),
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();

    }
}
