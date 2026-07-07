package com.elenacapilla.task_api.controller;

import com.elenacapilla.task_api.model.User;
import com.elenacapilla.task_api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RestController = @Controller + @ResponseBody
//Todo lo que devuelvan los métodos se convierte automáticamente a JSON
@RestController
// @RequestMapping define la URL base para todos los endpoints de esta clase
// Todos empezarán por /api/users
@RequestMapping("/api/users")
public class UserController {

    // Inyectamos el service — el controller nunca habla directamente con la base de datos
    @Autowired
    private UserService userService;

    // POST /api/users/register — registrar un nuevo usuario
    // @RequestBody convierte el JSON que llega en la petición a un objeto User
    // @Valid activa las validaciones que pusimos en la entidad (@NotBlank, @Email...)
    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody User user) {
        User saved = userService.register(user);
        // ResponseEntity.ok() devuelve HTTP 200 con el usuario creado en el cuerpo
        return ResponseEntity.ok(saved);
    }

    // GET /api/users — obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }
}