package com.elenacapilla.task_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

// Cada tarea pertenece a un usuario — esta clase representa la tabla "tasks" en MySQL
@Entity
@Table(name = "tasks")
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Título obligatorio de la tarea
    @NotBlank
    private String title;

    // Descripción opcional — puede ser null
    private String description;

    // Estado de la tarea: false = pendiente, true = completada
    // Por defecto las tareas nuevas empiezan como pendientes
    private boolean completed = false;

    // Relación Many-to-One: muchas tareas pertenecen a un solo usuario
    // @JoinColumn indica que en la tabla "tasks" habrá una columna "user_id"
    // que apunta al id del usuario propietario
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}