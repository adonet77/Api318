# API318

API REST desarrollada en Java para la gestion de usuario

## Tecnologías

- Java
- Java EE8
- Maven
- MySQL
- JDBC
- Git
- GitHub

## Base de datos

El proyecto utiliza MySQL.

Las credenciales de conexión se almacenan en un archivo `.env`.

## Ejecución

1. Clonar el repositorio.
2. Crear el archivo `.env`.
3. Configurar las credenciales de MySQL.
4. Ejecutar el proyecto en Apache Tomcat.

## Seguridad

Las credenciales de la base de datos no se almacenan directamente
en el código fuente y el archivo `.env` está excluido mediante `.gitignore`.

## Endpoints

### Consultar usuarios

**Método:** GET

**Endpoint:**


/Api318/UsuarioControlador
Permite consultar todos los usuarios registrados en la base de datos.

La información se devuelve en formato JSON.
Validar usuario / Iniciar sesión

### Método: POST

Endpoint:

/Api318/UsuarioControlador?accion=login

Body:

{
    "usuario": "Zprueba",
    "password": "123456"
}

Permite validar las credenciales del usuario.

La contraseña se verifica utilizando BCrypt.

Si las credenciales son correctas, el API devuelve la información del usuario.

### Eliminar usuario

Método: DELETE

Endpoint:

/Api318/UsuarioControlador?id=21

Permite eliminar un usuario utilizando su ID.

Si el usuario existe, el API devuelve un mensaje de confirmación.

Si el usuario no existe, devuelve un mensaje indicando que no fue encontrado.


y no como texto plano.


### Resumen de los endpoints

| Método | Endpoint | Función |
|---|---|---|
| **GET** | `/Api318/UsuarioControlador` | Consultar usuarios |
| **POST** | `/Api318/UsuarioControlador?accion=login` | Validar usuario / iniciar sesión |
| **DELETE** | `/Api318/UsuarioControlador?id=21` | Eliminar usuario |

Este README refleja el estado actual del **API318**, sin documentar todavía funcionalidades que aún no hemos terminado, como `PUT` o el registro de usuarios mediante el controlador.

