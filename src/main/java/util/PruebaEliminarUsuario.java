package util;

import dao.UsuarioDAO;

public class PruebaEliminarUsuario {

    public static void main(String[] args) {

        // Crear una instancia de UsuarioDAO
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        // ID del usuario que queremos eliminar
        int id = 3;

        // Intentar eliminar el usuario de la base de datos
        boolean resultado = usuarioDAO.eliminarUsuario(id);

        // Verificar si el usuario fue eliminado correctamente
        if (resultado) {

            System.out.println(
                    "El usuario con ID " + id
                    + " fue eliminado correctamente."
            );

        } else {

            System.out.println(
                    "No fue posible eliminar el usuario con ID "
                    + id
            );
        }
    }
}