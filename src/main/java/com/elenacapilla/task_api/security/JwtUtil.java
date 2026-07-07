package com.elenacapilla.task_api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

// @Component hace que Spring gestione esta clase y podamos inyectarla con @Autowired
@Component
public class JwtUtil {

    // Clave secreta para firmar los tokens — en producción iría en application.properties
    // Debe tener mínimo 32 caracteres para el algoritmo HS256
    private final String SECRET = "clave-secreta-task-api-elena-2026-segura";

    // Tiempo de expiración del token: 24 horas en milisegundos
    private final long EXPIRATION = 1000 * 60 * 60 * 24;

    // Genera la clave criptográfica a partir del string secreto
    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // Genera un token JWT para un email concreto
    // El email será el "subject" del token — lo que identifica al usuario
    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())                          // fecha de creación
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION)) // fecha de expiración
                .signWith(getKey())                            // firma con nuestra clave secreta
                .compact();                                    // genera el string final
    }

    // Extrae el email del token (si es válido)
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    // Valida que el token no ha expirado y que el email coincide
    public boolean isValid(String token, String email) {
        return extractEmail(token).equals(email)
                && !getClaims(token).getExpiration().before(new Date());
    }

    // Extrae toda la información (claims) del token verificando la firma
    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}