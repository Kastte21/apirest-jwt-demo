# Tu API REST segura con Spring Boot, JWT y PostgreSQL

## Descripción
API REST con autenticación JWT, Refresh tokens, PostgreSQL y buenas prácticas inspiradas en [nodebestpractices](https://github.com/goldbergyoni/nodebestpractices).

## Requisitos

- Java 21
- Maven
- PostgreSQL

## Instalación y ejecución

1. **Configura tus variables de entorno**  
   Exporta en tu sistema:
   ```bash
   export DB_USER=user
   export DB_PASS=pass
   export JWT_SECRET=secretkey
   ```

2. **Crea la base de datos**

3. **Compila y ejecuta**

## Estructura del proyecto

- `controller/`: Controladores REST
- `service/`: Lógica de negocio
- `repository/`: Acceso a datos (JPA)
- `model/`: Entidades (JPA)
- `dto/`: Objetos de transferencia de datos
- `security/`: JWT, filtros y configuración de seguridad
- `exception/`: Manejo de errores global

## Buenas prácticas aplicadas

- Variables sensibles fuera del código fuente
- Estructura modular y limpia
- Documentación desde el inicio

---