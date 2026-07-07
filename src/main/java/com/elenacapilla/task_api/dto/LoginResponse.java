package com.elenacapilla.task_api.dto;

// DTO de respuesta — lo que devolvemos cuando el login es correcto
public class LoginResponse {
    private String token;

    public LoginResponse(String token) {
        this.token = token;
    }

    public String getToken() { return token; }
}