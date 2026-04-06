# 🍽️ La Maison — Módulo de Autenticación Reactivo

Proyecto desarrollado para la asignatura de **Programación Reactiva** — Fundación Universitaria Católica Lumen Gentium (Unicatólica).

---

## 📌 Descripción del Proyecto

Sistema de autenticación reactivo para el restaurante **La Maison**. Permite el acceso seguro al sistema mediante registro, inicio de sesión y control de roles.

El sistema distingue dos tipos de usuarios:
- **Cliente** — puede registrarse, iniciar sesión, ver su perfil y recuperar su contraseña.
- **Administrador** — gestiona usuarios, modifica roles y controla el sistema.

---

## ⚙️ Stack Tecnológico

| Tecnología | Uso |
|---|---|
| Java 17 | Lenguaje principal |
| Spring Boot 3.5.13 | Framework base |
| Spring WebFlux | Programación reactiva (Mono / Flux) |
| Spring Data MongoDB Reactive | Repositorios reactivos |
| Spring Security | Seguridad y control de acceso |
| JWT (jjwt 0.11.5) | Autenticación stateless |
| MongoDB | Base de datos NoSQL |
| Maven | Gestión de dependencias |

---

## 🗂️ Estructura del Proyecto

```
lamaison-auth/
├── pom.xml
└── src/main/java/com/lamaison/auth/
    ├── LamaisonAuthApplication.java
    ├── model/
    │   ├── User.java
    │   ├── Role.java (enum: CLIENTE, ADMINISTRADOR)
    │   └── PasswordResetToken.java
    ├── repository/
    │   ├── UserRepository.java
    │   └── PasswordResetTokenRepository.java
    ├── service/
    │   ├── AuthService.java (interfaz)
    │   ├── UserService.java (interfaz)
    │   └── impl/
    │       ├── AuthServiceImpl.java
    │       └── UserServiceImpl.java
    ├── controller/
    │   ├── AuthController.java
    │   └── AdminController.java
    ├── config/
    │   ├── SecurityConfig.java
    │   └── JwtUtil.java
    ├── dto/
    │   ├── request/
    │   │   ├── RegisterRequest.java
    │   │   ├── LoginRequest.java
    │   │   └── PasswordResetRequest.java
    │   └── response/
    │       ├── AuthResponse.java
    │       └── UserResponse.java
    └── exception/
        └── GlobalExceptionHandler.java
```

---

## 🗃️ Schema de Base de Datos (MongoDB)

### Colección `users`

| Campo | Tipo | Descripción |
|---|---|---|
| id | String (PK) | Identificador único |
| nombre | String | Nombre del usuario |
| correo | String (UK) | Correo electrónico único |
| password | String | Contraseña encriptada (BCrypt) |
| rol | String | CLIENTE o ADMINISTRADOR |
| activo | Boolean | Estado de la cuenta |
| creadoEn | DateTime | Fecha de registro |
| ultimoLogin | DateTime | Último acceso |

### Colección `password_reset_tokens`

| Campo | Tipo | Descripción |
|---|---|---|
| id | String (PK) | Identificador único |
| userId | String (FK) | Referencia al usuario |
| token | String (UK) | Token UUID generado |
| expiracion | DateTime | Fecha de expiración (1 hora) |
| usado | Boolean | Si ya fue utilizado |

### Relación

```
users ||--o{ password_reset_tokens : "solicita"
```

Un usuario puede tener múltiples tokens de recuperación de contraseña.

---

## 🔗 Endpoints de la API

### Autenticación (`/api/auth`)

| Método | Endpoint | Descripción |
|---|---|---|
| POST | `/api/auth/register` | Registro de cliente |
| POST | `/api/auth/login` | Inicio de sesión |
| POST | `/api/auth/password-reset/request` | Solicitar recuperación |
| POST | `/api/auth/password-reset/confirm` | Confirmar nueva contraseña |

### Administrador (`/api/admin`) — requiere rol ADMINISTRADOR

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/admin/users` | Listar todos los usuarios |
| GET | `/api/admin/users/{id}` | Obtener usuario por ID |
| PATCH | `/api/admin/users/{id}/role` | Cambiar rol de usuario |
| DELETE | `/api/admin/users/{id}` | Eliminar usuario |

---

## 🔄 Componente Reactivo

Todo el flujo usa programación reactiva con **Project Reactor**:

- `Mono<T>` — para operaciones que retornan un solo elemento (login, registro, buscar usuario)
- `Flux<T>` — para operaciones que retornan múltiples elementos (listar usuarios)
- Los repositorios extienden `ReactiveMongoRepository`
- La seguridad usa `@EnableWebFluxSecurity`

---

## ▶️ Configuración

En `src/main/resources/application.properties`:

```properties
spring.data.mongodb.uri=mongodb+srv://<usuario>:<password>@<cluster>.mongodb.net/lamaison_db
server.port=8080
jwt.secret=lamaison-secret-key-muy-segura-2024
jwt.expiration=86400000
```

---

## 👥 Integrantes

- Jhon (Unicatólica — Tec. Soft.)
- Danilo (Unicatólica — Tec. Soft.)
