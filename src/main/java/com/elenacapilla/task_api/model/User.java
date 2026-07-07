package com.elenacapilla.task_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;


// @Entity indica a Hibernate que esta clase es una tabla en la base de datos
@Entity
// @Table define el nombre exacto de la tabla en MySQL
@Table(name = "users")
// @Data de Lombok genera automáticamente getters, setters, toString y equals sin escribirlos
@Data
public class User {

    // Clave primaria — se autoincrementa (1, 2, 3...) con cada nuevo usuario
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @NotBlank valida que el campo no llegue vacío en la petición
    @NotBlank
    private String name;

    // @Email valida que tenga formato correcto (contiene @ y dominio)
    // @Column(unique = true) impide que dos usuarios tengan el mismo email
    @Email
    @NotBlank
    @Column(unique = true)
    private String email;

    // La contraseña se guardará encriptada, nunca en texto plano
    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}