package dao;

import model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

            // Mostrar el mensaje cuando ocurre un error relacionado con SQL
            System.out.println("Error al registrar el usuario: " + e.getMessage());

        }
        // Si el INSERT no fue exitoso, devolver false
        return false;
    }
}