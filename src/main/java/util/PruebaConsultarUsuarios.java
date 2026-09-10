package util;

import dao.UsuarioDAO;
import model.Usuario;
import java.util.List;

public class PruebaConsultarUsuarios {

    public static void main(String[] args) {

        // Crear una instancia de UsuarioDAO
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        // Consultar todos los usuarios almacenados en MySQL
        List<Usuario> listaUsuarios = usuarioDAO.consultarUsuarios();

        // Verificar si la lista contiene usuarios
        if (listaUsuarios.isEmpty()) {

            // Mostrar mensaje si no existen usuarios
            System.out.println(
                    "No hay usuarios registrados."
            );

        } else {

            // Mostrar la cantidad de usuarios encontrados
            System.out.println(
                    "Usuarios encontrados: "
                    + listaUsuarios.size()
            );

            // Recorrer la lista de usuarios
            for (Usuario usuario : listaUsuarios) {

                // Mostrar los datos de cada usuario
                System.out.println(
                        "ID: " + usuario.getId()
                        + " | Nombres: " + usuario.getNombres()
                        + " | Apellidos: " + usuario.getApellidos()
                        + " | Usuario: " + usuario.getUsuario()
                       
                );
            }
        }
    }
}