package com.elenacapilla.task_api.dto;

// DTO — solo transporta los datos del login, no es una entidad de base de datos
public class LoginRequest {
    private String email;
    private String password;

    // Getters y setters manuales — no usamos Lombok aquí para que veas cómo es sin él
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}