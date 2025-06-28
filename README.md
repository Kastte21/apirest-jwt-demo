# apirest-jwt-demo

API REST segura construida con **Spring Boot**, **Java 21**, autenticación mediante **JWT** y **Refresh Tokens**, y **PostgreSQL** como base de datos. El proyecto sigue buenas prácticas inspiradas en [nodebestpractices](https://github.com/goldbergyoni/nodebestpractices).

---

## Tabla de contenidos

- [Características](#características)
- [Requisitos previos](#requisitos-previos)
- [Instalación](#instalación)
- [Variables de entorno y configuración](#variables-de-entorno-y-configuración)
- [Estructura del proyecto](#estructura-del-proyecto)
- [Endpoints principales](#endpoints-principales)
- [Buenas prácticas aplicadas](#buenas-prácticas-aplicadas)
- [Próximos pasos y mejoras](#próximos-pasos-y-mejoras)

---

## Características

- Registro y login de usuarios con validación de credenciales.
- Autenticación basada en JWT (access token) y Refresh Tokens.
- Logout seguro con invalidación de refresh tokens.
- Rotación de refresh tokens en cada uso.
- Acceso protegido a endpoints mediante JWT.
- Manejo global de errores.
- Variables sensibles y de configuración fuera del código fuente.
- Estructura modular y clara para escalar el proyecto fácilmente.

---

## Requisitos previos

- Java 21
- Maven
- PostgreSQL

---

## Instalación

1. **Clona el repositorio:**
   ```bash
   git clone https://github.com/tu-usuario/apirest-jwt-demo.git
   cd apirest-jwt-demo
   ```

2. **Configura tus variables de entorno:**
   ```bash
   export DB_USER=usuario
   export DB_PASS=contraseña
   export JWT_SECRET=claveSuperSecretaBase64
   ```

3. **Crea la base de datos PostgreSQL:**
   ```sql
   CREATE DATABASE testdb;
   ```

4. **Compila y ejecuta la aplicación:**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

---

## Variables de entorno y configuración

Configura en tu sistema o en el archivo `application.properties`:

- `DB_USER` y `DB_PASS`: Usuario y contraseña de la base de datos.
- `JWT_SECRET`: Clave secreta (mínimo 256 bits, codificada en base64).
- `jwt.access.expiration`: Tiempo de expiración del access token en milisegundos.
- `jwt.refresh.expiration`: Tiempo de expiración del refresh token en milisegundos.

Ejemplo de `application.properties`:

```properties
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASS}
jwt.secret=${JWT_SECRET}
jwt.access.expiration=3600000
jwt.refresh.expiration=604800000
```

---

## Estructura del proyecto

```
apirest-jwt-demo/
├── pom.xml
├── README.md
├── src/
│   ├── main/
│   │   ├── java/com/example/apirest_jwt_demo/
│   │   │   ├── ApirestJwtDemoApplication.java
│   │   │   ├── config/
│   │   │   │   └── SecurityConfig.java
│   │   │   ├── controller/
│   │   │   │   └── AuthController.java
│   │   │   ├── dto/
│   │   │   │   ├── AuthResponse.java
│   │   │   │   ├── LoginRequest.java
│   │   │   │   ├── UserRegisterRequest.java
│   │   │   │   ├── UserResponse.java
│   │   │   │   ├── RefreshTokenRequest.java
│   │   │   │   └── LogoutRequest.java
│   │   │   ├── exception/
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   ├── model/
│   │   │   │   ├── User.java
│   │   │   │   └── RefreshToken.java
│   │   │   ├── repository/
│   │   │   │   ├── UserRepository.java
│   │   │   │   └── RefreshTokenRepository.java
│   │   │   ├── security/
│   │   │   │   ├── CustomAuthenticationEntryPoint.java
│   │   │   │   ├── CustomUserDetailsService.java
│   │   │   │   ├── JwtFilter.java
│   │   │   │   └── JwtUtil.java
│   │   │   └── service/
│   │   │       ├── AuthService.java
│   │   │       ├── UserService.java
│   │   │       ├── RefreshTokenService.java
│   │   │       └── impl/
│   │   │           ├── AuthServiceImpl.java
│   │   │           ├── UserServiceImpl.java
│   │   │           └── RefreshTokenServiceImpl.java
│   └── test/
│       └── java/com/example/apirest_jwt_demo/
│           └── ApirestJwtDemoApplicationTests.java
```

---

## Endpoints principales

### Autenticación

- **POST /api/auth/register**
   - Registra un nuevo usuario.
- **POST /api/auth/login**
   - Autentica usuario y entrega access/refresh token.
- **POST /api/auth/refresh-token**
   - Entrega nuevo access y refresh token a partir de un refresh token válido (rotación).
- **POST /api/auth/logout**
   - Cierra sesión e invalida el refresh token.

### Otros

- Endpoints protegidos requieren el header: `Authorization: Bearer <access_token>`.

---

## Buenas prácticas aplicadas

- **Seguridad**: Los tokens y contraseñas nunca se exponen ni se almacenan en texto plano.
- **Manejo de errores**: Centralizado, mensajes claros y sin fugas de información sensible.
- **Estructura limpia y escalable**: Separación clara por capas y responsabilidades.
- **Tokens JWT y Refresh**: Acceso seguro, rotación e invalidación de tokens.
- **Variables sensibles**: Nunca se incluyen en el código fuente.
- **Pruebas**: Base de pruebas con JUnit/Spring Boot.

---

## Próximos pasos y mejoras

- Implementar roles y autorización fina por endpoint.
- Documentación con Swagger/OpenAPI.
- Tests de integración y unitarios para todos los flujos.
- Rate limiting en endpoints de autenticación.
- Limpieza automática de refresh tokens expirados (tarea programada).
- Configuración de CORS y otras cabeceras de seguridad.

---

## Créditos

Inspirado en [nodebestpractices](https://github.com/goldbergyoni/nodebestpractices) y adaptado a Java/Spring Boot.

---