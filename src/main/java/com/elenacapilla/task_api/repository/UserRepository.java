package com.elenacapilla.task_api.repository;

import com.elenacapilla.task_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// JpaRepository nos da gratis: save(), findById(), findAll(), delete()...
// sin escribir ninguna consulta SQL
// <User, Long> = entidad que maneja, tipo de su clave primaria
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring lee el nombre del metodo y genera automáticamente:
    // SELECT * FROM users WHERE email = ?
    // Optional evita NullPointerException si no encuentra el usuario
    Optional<User> findByEmail(String email);

    // Genera: SELECT COUNT(*) FROM users WHERE email = ?
    // Devuelve true si ya existe ese email, false si no
    boolean existsByEmail(String email);
}