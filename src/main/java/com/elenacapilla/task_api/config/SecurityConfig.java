package com.elenacapilla.task_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

// @Configuration indica que esta clase contiene beans de configuración de Spring
@Configuration
// @EnableWebSecurity activa la configuración personalizada de Spring Security
@EnableWebSecurity
public class SecurityConfig {

    // Este bean reemplaza la configuración por defecto de Spring Security
    // Por ahora abrimos todo para poder probar los endpoints con Postman
    // Más adelante aquí añadiremos JWT y restringiremos los accesos
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Desactivamos CSRF — necesario para APIs REST (no hay sesión de navegador)
                .csrf(csrf -> csrf.disable())
                // Permitimos todas las peticiones sin autenticación (temporal)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                );
        return http.build();
    }
}