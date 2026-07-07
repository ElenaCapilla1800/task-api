package com.elenacapilla.task_api.service;

import com.elenacapilla.task_api.model.User;
import com.elenacapilla.task_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

// @Service marca esta clase como capa de lógica de negocio
// Spring la detecta automáticamente y la gestiona
@Service
public class UserService {

    // @Autowired inyecta la dependencia automáticamente
    // Spring crea el objeto y lo conecta aquí — no necesitamos hacer "new"
    @Autowired
    private UserRepository userRepository;

    // BCryptPasswordEncoder encripta contraseñas de forma segura e irreversible
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Método para registrar un nuevo usuario
    public User register(User user) {

        // Comprobamos si el email ya existe antes de guardar
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        // Encriptamos la contraseña antes de guardarla
        // "123456" se convierte en algo como "$2a$10$xyz..." que no se puede revertir
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Guardamos el usuario en la base de datos y lo devolvemos
        return userRepository.save(user);
    }

    // Devuelve la lista de todos los usuarios registrados
    public List<User> findAll() {
        return userRepository.findAll();
    }
}