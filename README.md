# Task API

![CI](https://github.com/ElenaCapilla1800/task-api/actions/workflows/ci.yml/badge.svg)

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

2. Crea tus variables de entorno (no se suben al repositorio)

   Copia `.env.example` a `.env` y pon tus propios valores. El secreto de JWT debe tener al menos 32 caracteres.

   Para ejecutar la app fuera de Docker, define las mismas variables en la terminal donde la vayas a lanzar. En PowerShell:
```powershell
   $env:DB_PASSWORD="tu_contrasena"
   $env:JWT_SECRET="un_secreto_largo_de_minimo_32_caracteres"
```

3. Inicia MySQL con Docker (lee la contraseña de tu archivo `.env`)
```bash
   docker compose up -d mysql
```

4. Ejecuta la aplicación (o levanta todo con `docker compose up --build`)
```bash
   ./mvnw spring-boot:run
```

La API estará disponible en `http://localhost:8082`

> La aplicación no arranca si falta `JWT_SECRET` o `DB_PASSWORD`: no hay valores por defecto en el repositorio, para no usar nunca una clave pública por error.

## Tests

```bash
   ./mvnw test
```

Los tests también se ejecutan automáticamente en cada push con GitHub Actions (`.github/workflows/ci.yml`).

## Estructura del proyecto

```
src/main/java/com/elenacapilla/task_api/
├── config/         # Configuración de Spring Security
├── controller/     # Endpoints REST
├── model/          # Entidades JPA (User, Task)
├── repository/     # Acceso a base de datos
└── service/        # Lógica de negocio
```