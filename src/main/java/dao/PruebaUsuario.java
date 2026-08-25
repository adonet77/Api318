
package dao;

/**
 *
 * @author ADONET
 */

import model.Usuario;

public class PruebaUsuario {

    public static void main(String[] args) {

        // Crear un objeto de tipo Usuario
        Usuario usuario = new Usuario();

        // Asignar los nombres
        usuario.setNombres("Adonay");

        // Asignar los apellidos
        usuario.setApellidos("Sanchez");

        // Asignar el nombre de usuario
        usuario.setUsuario("asanchez");

        // Asignar la contraseña
        usuario.setPassword("adonet77");

        // Crear un objeto UsuarioDAO
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        // Intentar registrar el usuario en MySQL
        boolean resultado = usuarioDAO.registrarUsuario(usuario);

        // Mostrar el resultado del registro
        if (resultado) {

            System.out.println("Registro realizado correctamente.");

        } else {

            System.out.println("No fue posible registrar el usuario.");
        }
    }
}