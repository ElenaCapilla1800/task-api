package com.elenacapilla.task_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// @SpringBootApplication = punto de entrada de la aplicación
// Activa el escaneo automático de componentes (@Service, @Repository, @Controller...)
@SpringBootApplication
public class TaskApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskApiApplication.class, args);
	}

	// @Bean registra BCryptPasswordEncoder como componente de Spring
	// Así puede ser inyectado con @Autowired en cualquier clase
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}