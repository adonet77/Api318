package dao;

import model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.PasswordUtil;

public class UsuarioDAO {

    public boolean registrarUsuario(Usuario usuario) {

        // Crear una instancia de la clase que administra la conexión con MySQL
        ConexionMysql conexionBD = new ConexionMysql();

        // Variable que almacenará la conexión activa con la base de datos
        Connection conexion = null;

        // Sentencia SQL que permitirá insertar un nuevo usuario
        String sql = "INSERT INTO usuarios "
                + "(nombres, apellidos, usuario, password) "
                + "VALUES (?, ?, ?, ?)";

        try {

            // Establecer la conexión con la base de datos
            conexion = conexionBD.establecerConexion();

            // Verificar que la conexión se haya establecido correctamente
            if (conexion == null) {
                System.out.println("No se pudo establecer la conexión con la base de datos.");
                return false;
            }

            // Preparar la sentencia SQL para ejecutarla posteriormente
            PreparedStatement sentencia = conexion.prepareStatement(sql);

            // Obtener los nombres del objeto Usuario y colocarlos en el primer ?
            sentencia.setString(1, usuario.getNombres());

            // Obtener los apellidos del objeto Usuario y colocarlos en el segundo ?
            sentencia.setString(2, usuario.getApellidos());

            // Obtener el nombre de usuario y colocarlo en el tercer ?
            sentencia.setString(3, usuario.getUsuario());

            // Generar el hash de la contraseña antes de almacenarla
            String hash = PasswordUtil.generarHash(usuario.getPassword());

            // Colocar el hash generado en el cuarto ?
            sentencia.setString(4, hash);

            // Ejecutar el INSERT y almacenar la cantidad de registros afectados
            int filasAfectadas = sentencia.executeUpdate();

            // Cerrar la sentencia preparada
            sentencia.close();

            // Cerrar la conexión con MySQL
            conexionBD.cerrarConexion();

            // Verificar si se insertó exactamente un registro
            if (filasAfectadas > 0) {
                System.out.println("Usuario registrado correctamente.");
                return true;
            }

        } catch (SQLException e) {

            // Obtener el código de error generado por MySQL
            int codigoError = e.getErrorCode();

            // Verificar si el error corresponde a un registro duplicado
            if (codigoError == 1062) {

                // Mostrar un mensaje específico para usuario duplicado
                System.out.println(
                        "El nombre de usuario '"
                        + usuario.getUsuario()
                        + "' ya está registrado."
                );

            } else {

                // Mostrar otros errores relacionados con SQL
                System.out.println("Error al registrar el usuario: " + e.getMessage());
            }
        }
        // Si el INSERT no fue exitoso, devolver false
        return false;
    }//Fin registrarUsuario

    // Método encargado de validar las credenciales de un usuario
    public Usuario validarUsuario(String usuario, String password) {

        // Crear una instancia de la clase que administra la conexión con MySQL
        ConexionMysql conexionBD = new ConexionMysql();

        // Variable que almacenará la conexión activa con la base de datos
        Connection conexion = null;

        // Sentencia SQL para buscar un usuario por su nombre de usuario
        String sql = "SELECT id, nombres, apellidos, usuario, password, fecha_creacion "
                + "FROM usuarios "
                + "WHERE usuario = ?";

        try {

            // Establecer la conexión con la base de datos
            conexion = conexionBD.establecerConexion();

            // Verificar que la conexión se haya establecido correctamente
            if (conexion == null) {

                System.out.println(
                        "No se pudo establecer la conexión con la base de datos."
                );

                // Si no hay conexión, no se puede validar el usuario
                return null;
            }

            // Preparar la sentencia SQL para ejecutarla posteriormente
            PreparedStatement sentencia = conexion.prepareStatement(sql);

            // Colocar el nombre de usuario recibido en el primer signo ?
            sentencia.setString(1, usuario);

            // Ejecutar la consulta SELECT
            // El resultado se almacena en un objeto ResultSet
            java.sql.ResultSet resultado = sentencia.executeQuery();

            // Verificar si la consulta encontró un usuario
            if (resultado.next()) {

                // Obtener el hash de la contraseña almacenado en MySQL
                String hashAlmacenado = resultado.getString("password");

                // Comprobar si la contraseña ingresada corresponde al hash almacenado
                boolean passwordCorrecta
                        = PasswordUtil.verificarPassword(password, hashAlmacenado);

                // Verificar si la contraseña es correcta
                if (passwordCorrecta) {

                    // Crear un objeto Usuario para almacenar los datos encontrados
                    Usuario usuarioEncontrado = new Usuario();

                    // Obtener el ID desde la base de datos
                    usuarioEncontrado.setId(
                            resultado.getInt("id")
                    );

                    // Obtener los nombres desde la base de datos
                    usuarioEncontrado.setNombres(
                            resultado.getString("nombres")
                    );

                    // Obtener los apellidos desde la base de datos
                    usuarioEncontrado.setApellidos(
                            resultado.getString("apellidos")
                    );

                    // Obtener el nombre de usuario desde la base de datos
                    usuarioEncontrado.setUsuario(
                            resultado.getString("usuario")
                    );

                    // Obtener la fecha de creación desde la base de datos
                    usuarioEncontrado.setFechaCreacion(
                            resultado.getTimestamp("fecha_creacion")
                                    .toLocalDateTime()
                    );

                    // Cerrar el ResultSet
                    resultado.close();

                    // Cerrar la sentencia preparada
                    sentencia.close();

                    // Cerrar la conexión con MySQL
                    conexionBD.cerrarConexion();

                    // Devolver el objeto Usuario con los datos encontrados
                    return usuarioEncontrado;
                }
            }

            // Cerrar el ResultSet
            resultado.close();

            // Cerrar la sentencia preparada
            sentencia.close();

            // Cerrar la conexión con MySQL
            conexionBD.cerrarConexion();

        } catch (SQLException e) {

            // Mostrar el mensaje cuando ocurre un error relacionado con SQL
            System.out.println(
                    "Error al validar el usuario: " + e.getMessage()
            );
        }

        // Si el usuario no existe, la contraseña es incorrecta
        // o se produjo algún problema, devolver null
        return null;
    } // Fin validar usuario

    // Método encargado de eliminar un usuario de la base de datos
    public boolean eliminarUsuario(int id) {

        // Crear una instancia de la clase que administra la conexión con MySQL
        ConexionMysql conexionBD = new ConexionMysql();

        // Variable que almacenará la conexión activa con la base de datos
        Connection conexion = null;

        // Sentencia SQL para eliminar un usuario utilizando su ID
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try {

            // Establecer la conexión con la base de datos
            conexion = conexionBD.establecerConexion();

            // Verificar que la conexión se haya establecido correctamente
            if (conexion == null) {

                System.out.println(
                        "No se pudo establecer la conexión con la base de datos."
                );

                // Si no existe conexión, no se puede eliminar el usuario
                return false;
            }

            // Preparar la sentencia SQL para ejecutarla posteriormente
            PreparedStatement sentencia = conexion.prepareStatement(sql);

            // Colocar el ID recibido en el signo ?
            sentencia.setInt(1, id);

            // Ejecutar la sentencia DELETE
            // El resultado indica cuántos registros fueron afectados
            int filasAfectadas = sentencia.executeUpdate();

            // Cerrar la sentencia preparada
            sentencia.close();

            // Cerrar la conexión con MySQL
            conexionBD.cerrarConexion();

            // Verificar si se eliminó algún registro
            if (filasAfectadas > 0) {

                // Mostrar mensaje de confirmación
                System.out.println(
                        "Usuario eliminado correctamente."
                );

                // Indicar que la operación fue exitosa
                return true;
            }

        } catch (SQLException e) {

            // Mostrar el mensaje cuando ocurre un error relacionado con SQL
            System.out.println(
                    "Error al eliminar el usuario: "
                    + e.getMessage()
            );
        }

        // Si no se eliminó ningún usuario, devolver false
        return false;
    } // Fin eliminar usuario 

    // Método encargado de consultar todos los usuarios
    public List<Usuario> consultarUsuarios() {

        // Crear una lista donde se almacenarán los usuarios encontrados
        List<Usuario> listaUsuarios = new ArrayList<>();

        // Crear una instancia de la clase que administra la conexión con MySQL
        ConexionMysql conexionBD = new ConexionMysql();

        // Variable que almacenará la conexión activa con la base de datos
        Connection conexion = null;

        // Sentencia SQL para consultar todos los usuarios
        String sql = "SELECT id, nombres, apellidos, usuario, fecha_creacion "
                + "FROM usuarios";

        try {

            // Establecer la conexión con la base de datos
            conexion = conexionBD.establecerConexion();

            // Verificar que la conexión se haya establecido correctamente
            if (conexion == null) {

                System.out.println(
                        "No se pudo establecer la conexión con la base de datos."
                );

                // Devolver la lista vacía
                return listaUsuarios;
            }

            // Preparar la sentencia SQL
            PreparedStatement sentencia = conexion.prepareStatement(sql);

            // Ejecutar la consulta SELECT
            ResultSet resultado = sentencia.executeQuery();

            // Recorrer todos los registros encontrados
            while (resultado.next()) {

                // Crear un objeto Usuario para almacenar los datos del registro
                Usuario usuario = new Usuario();

                // Obtener el ID desde la base de datos
                usuario.setId(
                        resultado.getInt("id")
                );

                // Obtener los nombres
                usuario.setNombres(
                        resultado.getString("nombres")
                );

                // Obtener los apellidos
                usuario.setApellidos(
                        resultado.getString("apellidos")
                );

                // Obtener el nombre de usuario
                usuario.setUsuario(
                        resultado.getString("usuario")
                );

                // Obtener la fecha de creación
                usuario.setFechaCreacion(
                        resultado.getTimestamp("fecha_creacion")
                                .toLocalDateTime()
                );

                // Agregar el usuario a la lista
                listaUsuarios.add(usuario);
            }

            // Cerrar el ResultSet
            resultado.close();

            // Cerrar la sentencia preparada
            sentencia.close();

            // Cerrar la conexión con MySQL
            conexionBD.cerrarConexion();

        } catch (SQLException e) {

            // Mostrar el mensaje cuando ocurre un error relacionado con SQL
            System.out.println(
                    "Error al consultar los usuarios: "
                    + e.getMessage()
            );
        }

        // Devolver la lista de usuarios
        return listaUsuarios;

    } // Fin consultarUsuarios
}
