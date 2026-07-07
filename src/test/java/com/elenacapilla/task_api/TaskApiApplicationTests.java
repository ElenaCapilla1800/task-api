package com.elenacapilla.task_api;

import com.elenacapilla.task_api.model.User;
import com.elenacapilla.task_api.repository.UserRepository;
import com.elenacapilla.task_api.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// @ExtendWith activa Mockito para esta clase de tests
@ExtendWith(MockitoExtension.class)
class TaskApiApplicationTests {

	// @Mock crea un doble falso de UserRepository — no toca la base de datos real
	@Mock
	private UserRepository userRepository;

	// @Mock crea un doble falso del encoder
	@Mock
	private BCryptPasswordEncoder passwordEncoder;

	// @InjectMocks crea una instancia real de UserService e inyecta los mocks anteriores
	@InjectMocks
	private UserService userService;

	@Test
	void registrar_usuario_nuevo_ok() {
		// GIVEN — preparamos los datos y definimos el comportamiento de los mocks
		User user = new User();
		user.setEmail("test@test.com");
		user.setPassword("password123");
		user.setName("Test User");

		// Cuando pregunten si el email existe, decimos que NO
		when(userRepository.existsByEmail("test@test.com")).thenReturn(false);
		// Cuando encripten la contraseña, devolvemos un hash simulado
		when(passwordEncoder.encode("password123")).thenReturn("hash_simulado");
		// Cuando guarden el usuario, devolvemos el mismo usuario
		when(userRepository.save(any(User.class))).thenReturn(user);

		// WHEN — ejecutamos el método que queremos probar
		User resultado = userService.register(user);

		// THEN — verificamos que el resultado es el esperado
		assertNotNull(resultado);
		assertEquals("hash_simulado", resultado.getPassword());
		// Verificamos que save() se llamó exactamente una vez
		verify(userRepository, times(1)).save(any(User.class));
	}

	@Test
	void registrar_usuario_email_duplicado_lanza_excepcion() {
		// GIVEN — el email ya existe en la base de datos
		User user = new User();
		user.setEmail("duplicado@test.com");
		user.setPassword("password123");
		user.setName("Test User");

		when(userRepository.existsByEmail("duplicado@test.com")).thenReturn(true);

		// WHEN + THEN — verificamos que lanza la excepción correcta
		RuntimeException ex = assertThrows(RuntimeException.class,
				() -> userService.register(user));

		assertEquals("El email ya está registrado", ex.getMessage());
		// Verificamos que save() nunca se llamó — no se guardó nada
		verify(userRepository, never()).save(any(User.class));
	}
}