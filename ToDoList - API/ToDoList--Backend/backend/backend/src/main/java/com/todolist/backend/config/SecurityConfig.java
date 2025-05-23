package com.todolist.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // desativa proteção CSRF para testes
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll() // libera acesso às rotas /auth
                .anyRequest().permitAll()
            );
        return http.build();
    }
}
