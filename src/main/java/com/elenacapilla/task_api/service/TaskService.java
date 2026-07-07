package com.elenacapilla.task_api.service;

import com.elenacapilla.task_api.model.Task;
import com.elenacapilla.task_api.model.User;
import com.elenacapilla.task_api.repository.TaskRepository;
import com.elenacapilla.task_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    // Crear una nueva tarea asignada a un usuario
    public Task create(Long userId, Task task) {
        // Buscamos el usuario — si no existe lanzamos excepción
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        // Asignamos el usuario a la tarea antes de guardarla
        task.setUser(user);
        return taskRepository.save(task);
    }

    // Obtener todas las tareas de un usuario concreto
    public List<Task> findByUser(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    // Marcar una tarea como completada (o desmarcarla)
    public Task toggleCompleted(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
        // Cambia el estado: si estaba false pasa a true, y viceversa
        task.setCompleted(!task.isCompleted());
        return taskRepository.save(task);
    }

    // Eliminar una tarea por su ID
    public void delete(Long taskId) {
        taskRepository.deleteById(taskId);
    }
}