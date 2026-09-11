
package util;

/**
 *
 * @author ADONET
 */

import dao.UsuarioDAO;
import model.Usuario;

public class PruebaUsuario {

    public static void main(String[] args) {

        // Crear un objeto de tipo Usuario
        Usuario usuario = new Usuario();

        // Asignar los nombres
        usuario.setNombres("Lucas");

        // Asignar los apellidos
        usuario.setApellidos("Vega");

        // Asignar el nombre de usuario
        usuario.setUsuario("Luvega");

        // Asignar la contraseña
        usuario.setPassword("2026");

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