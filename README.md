# Task API

API REST de gestión de tareas con autenticación de usuarios, construida con Spring Boot y MySQL.

## Tecnologías

- Java 23 + Spring Boot 4.1
- Spring Security + BCrypt
- Spring Data JPA + Hibernate
- MySQL 8 (Docker)
- Maven

## Endpoints

### Autenticación
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/auth/login` | Login — recibe `{ "email", "password" }`, devuelve `{ "token" }` (JWT) |

### Usuarios
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/users/register` | Registrar nuevo usuario |
| GET | `/api/users` | Listar todos los usuarios |

### Tareas
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/users/{userId}/tasks` | Crear tarea |
| GET | `/api/users/{userId}/tasks` | Listar tareas del usuario |
| PATCH | `/api/users/{userId}/tasks/{taskId}/toggle` | Marcar como completada |
| DELETE | `/api/users/{userId}/tasks/{taskId}` | Eliminar tarea |

Todas las rutas excepto `POST /api/auth/login` y `POST /api/users/register` requieren el header `Authorization: Bearer {token}` obtenido en el login.

## Cómo ejecutar el proyecto

### Requisitos
- Java 23
- Docker Desktop

### Pasos

1. Clona el repositorio
```bash
   git clone https://github.com/ElenaCapilla1800/task-api.git
   cd task-api
```

2. Inicia MySQL con Docker
```bash
   docker run --name mysql-taskapi -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=taskapi -p 3306:3306 -d mysql:8
```

3. Ejecuta la aplicación
```bash
   ./mvnw spring-boot:run
```

La API estará disponible en `http://localhost:8082`

> El secreto usado para firmar los JWT tiene un valor por defecto para desarrollo local (`jwt.secret` en `application.properties`). Para producción, define la variable de entorno `JWT_SECRET` con tu propio valor — nunca subas el valor real al repositorio.

## Estructura del proyecto

```
src/main/java/com/elenacapilla/task_api/
├── config/         # Configuración de Spring Security
├── controller/     # Endpoints REST
├── model/          # Entidades JPA (User, Task)
├── repository/     # Acceso a base de datos
└── service/        # Lógica de negocio
```