package com.elenacapilla.task_api.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

// @RestControllerAdvice intercepta todas las excepciones lanzadas en cualquier controller
// En lugar de que Spring devuelva un error genérico, nosotros controlamos la respuesta
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Captura RuntimeException — la usamos en UserService y TaskService
    // Ejemplo: "El email ya está registrado", "Usuario no encontrado"
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntime(RuntimeException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    // Captura errores de validación — cuando @NotBlank o @Email fallan
    // Ejemplo: enviar una petición sin el campo "title" en una tarea
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        // Recorremos todos los campos que fallaron y los añadimos al mapa
        ex.getBindingResult().getFieldErrors().forEach(fieldError ->
                errors.put(fieldError.getField(), fieldError.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }

    // Captura cualquier otra excepción no controlada
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneral(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Error interno del servidor");
        return ResponseEntity.internalServerError().body(error);
    }
}