package com.elenacapilla.task_api.controller;

import com.elenacapilla.task_api.dto.LoginRequest;
import com.elenacapilla.task_api.dto.LoginResponse;
import com.elenacapilla.task_api.model.User;
import com.elenacapilla.task_api.repository.UserRepository;
import com.elenacapilla.task_api.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // POST /api/auth/login — recibe email y contraseña, devuelve un token JWT
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        // Buscamos el usuario por email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Comparamos la contraseña recibida con el hash guardado en base de datos
        // BCrypt nunca desencripta — vuelve a hashear y compara
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            // 401 Unauthorized — contraseña incorrecta
            return ResponseEntity.status(401).body("Contraseña incorrecta");
        }

        // Todo correcto — generamos el token JWT con el email del usuario
        String token = jwtUtil.generateToken(user.getEmail());

        // Devolvemos el token — el cliente lo guardará y lo enviará en futuras peticiones
        return ResponseEntity.ok(new LoginResponse(token));
    }
}