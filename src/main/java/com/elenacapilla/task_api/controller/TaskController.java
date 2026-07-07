package com.elenacapilla.task_api.controller;

import com.elenacapilla.task_api.model.Task;
import com.elenacapilla.task_api.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// Todas las rutas de tareas incluyen el userId — así sabemos de quién son
// Ejemplo: /api/users/1/tasks → tareas del usuario con ID 1
@RequestMapping("/api/users/{userId}/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // POST /api/users/1/tasks — crear una tarea para el usuario 1
    // @PathVariable extrae el {userId} de la URL y lo pasa como parámetro
    @PostMapping
    public ResponseEntity<Task> create(
            @PathVariable Long userId,
            @Valid @RequestBody Task task) {
        return ResponseEntity.ok(taskService.create(userId, task));
    }

    // GET /api/users/1/tasks — listar todas las tareas del usuario 1
    @GetMapping
    public ResponseEntity<List<Task>> findByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.findByUser(userId));
    }

    // PATCH /api/users/1/tasks/3/toggle — marcar/desmarcar la tarea 3 como completada
    // PATCH se usa para actualizaciones parciales (solo cambiamos el campo "completed")
    @PatchMapping("/{taskId}/toggle")
    public ResponseEntity<Task> toggle(@PathVariable Long taskId) {
        return ResponseEntity.ok(taskService.toggleCompleted(taskId));
    }

    // DELETE /api/users/1/tasks/3 — eliminar la tarea 3
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> delete(@PathVariable Long taskId) {
        taskService.delete(taskId);
        // 204 No Content — la operación fue bien pero no hay nada que devolver
        return ResponseEntity.noContent().build();
    }
}