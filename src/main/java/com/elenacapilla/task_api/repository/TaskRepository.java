package com.elenacapilla.task_api.repository;

import com.elenacapilla.task_api.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// Spring genera automáticamente todas las queries a partir del nombre del método
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Genera: SELECT * FROM tasks WHERE user_id = ?
    // Devuelve solo las tareas del usuario con ese ID — no mezcla tareas de otros
    List<Task> findByUserId(Long userId);
}