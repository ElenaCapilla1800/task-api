package com.elenacapilla.task_api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

// OncePerRequestFilter garantiza que este filtro se ejecuta exactamente una vez por petición
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Leemos el header "Authorization" de la petición
        // Debe tener el formato: "Bearer eyJhbGciOiJ..."
        String authHeader = request.getHeader("Authorization");

        String token = null;
        String email = null;

        // Verificamos que el header existe y empieza por "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Extraemos el token eliminando el prefijo "Bearer "
            token = authHeader.substring(7);
            try {
                email = jwtUtil.extractEmail(token);
            } catch (Exception e) {
                // Token malformado o expirado — dejamos email como null
                // El filtro continuará y Spring Security bloqueará la petición
            }
        }

        // Si hay email y aún no hay autenticación en el contexto de seguridad
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtUtil.isValid(token, email)) {
                // Creamos el objeto de autenticación con el email como principal
                // Lista vacía de authorities — más adelante aquí irían los roles
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(email, null, List.of());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Registramos la autenticación en el contexto de Spring Security
                // A partir de aquí Spring sabe quién es el usuario en esta petición
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Pasamos la petición al siguiente filtro (o al controller si no hay más filtros)
        filterChain.doFilter(request, response);
    }
}