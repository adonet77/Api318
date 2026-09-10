package controlador;

import dao.UsuarioDAO;
import model.Usuario;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ADONET
 */
@WebServlet(
        name = "UsuarioControlador",
        urlPatterns = {"/UsuarioControlador"}
)
public class UsuarioControlador extends HttpServlet {

    // =========================================================
    // MÉTODO GET - CONSULTAR USUARIOS
    // =========================================================
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Mostrar que se recibió GET
        System.out.println("=================================");
        System.out.println("ENTRÓ AL doGet()");
        System.out.println("=================================");

        // Crear UsuarioDAO
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        // Consultar usuarios
        List<Usuario> listaUsuarios
                = usuarioDAO.consultarUsuarios();

        // Mostrar cantidad encontrada
        System.out.println(
                "Cantidad de usuarios encontrados: "
                + listaUsuarios.size()
        );

        // Configurar Gson para LocalDateTime
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(
                        LocalDateTime.class,
                        (JsonSerializer<LocalDateTime>) (fecha, tipo, contexto)
                        -> contexto.serialize(
                                fecha.toString()
                        )
                )
                .create();

        // Convertir lista a JSON
        String json = gson.toJson(listaUsuarios);

        // Mostrar JSON
        System.out.println("JSON generado:");
        System.out.println(json);

        // Configurar respuesta
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Enviar JSON
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();

        // Confirmar respuesta
        System.out.println(
                "Respuesta enviada correctamente."
        );
    }

    // =========================================================
    // MÉTODO DELETE - ELIMINAR USUARIO
    // =========================================================
    @Override
    protected void doDelete(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Mostrar que se recibió DELETE
        System.out.println("=================================");
        System.out.println("ENTRÓ AL doDelete()");
        System.out.println("=================================");

        // Obtener ID de la URL
        String parametroId
                = request.getParameter("id");

        // Verificar que exista el ID
        if (parametroId == null
                || parametroId.isEmpty()) {

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // Código 400
            response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST
            );

            response.getWriter().print(
                    "{\"mensaje\":\"Debe proporcionar el ID del usuario.\"}"
            );

            return;
        }

        try {

            // Convertir ID a entero
            int id = Integer.parseInt(parametroId);

            // Crear UsuarioDAO
            UsuarioDAO usuarioDAO = new UsuarioDAO();

            // Eliminar usuario
            boolean resultado
                    = usuarioDAO.eliminarUsuario(id);

            // Configurar respuesta
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // Verificar resultado
            if (resultado) {

                // Código 200
                response.setStatus(
                        HttpServletResponse.SC_OK
                );

                response.getWriter().print(
                        "{\"mensaje\":\"Usuario eliminado correctamente.\"}"
                );

            } else {

                // Código 404
                response.setStatus(
                        HttpServletResponse.SC_NOT_FOUND
                );

                response.getWriter().print(
                        "{\"mensaje\":\"No se encontró un usuario con ese ID.\"}"
                );
            }

        } catch (NumberFormatException e) {

            // Configurar respuesta
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // Código 400
            response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST
            );

            response.getWriter().print(
                    "{\"mensaje\":\"El ID debe ser un número entero.\"}"
            );
        }
    }

    // =========================================================
    // MÉTODO POST - INICIAR SESIÓN
    // =========================================================
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Configurar respuesta JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Mostrar que se recibió POST
        System.out.println("=================================");
        System.out.println("ENTRÓ AL doPost()");
        System.out.println("=================================");

        // Obtener la acción de la URL
        String accion
                = request.getParameter("accion");

        // Mostrar acción
        System.out.println(
                "Acción recibida: " + accion
        );

        // Verificar si la acción es login
        if ("login".equalsIgnoreCase(accion)) {

            // Crear Gson
            Gson gson = new Gson();

            // Leer JSON del body
            String cuerpo = request.getReader()
                    .lines()
                    .reduce(
                            "",
                            (acumulado, linea)
                            -> acumulado + linea
                    );

            // Mostrar JSON recibido
            System.out.println("JSON recibido:");
            System.out.println(cuerpo);

            // Convertir JSON a JsonObject
            JsonObject jsonObjeto
                    = gson.fromJson(
                            cuerpo,
                            JsonObject.class
                    );

            // Crear objeto Usuario
            Usuario usuario = new Usuario();

            // Obtener usuario del JSON
            usuario.setUsuario(
                    jsonObjeto
                            .get("usuario")
                            .getAsString()
            );

            // Obtener contraseña del JSON
            usuario.setPassword(
                    jsonObjeto
                            .get("password")
                            .getAsString()
            );

            // Mostrar usuario recibido
            System.out.println(
                    "Usuario recibido: "
                    + usuario.getUsuario()
            );

            // Crear UsuarioDAO
            UsuarioDAO usuarioDAO
                    = new UsuarioDAO();

            // Validar credenciales
            Usuario usuarioEncontrado
                    = usuarioDAO.validarUsuario(
                            usuario.getUsuario(),
                            usuario.getPassword()
                    );

            // Verificar resultado
            if (usuarioEncontrado != null) {

                // Crear Gson para LocalDateTime
                Gson gsonRespuesta
                        = new GsonBuilder()
                                .registerTypeAdapter(
                                        LocalDateTime.class,
                                        (JsonSerializer<LocalDateTime>) (fecha, tipo, contexto)
                                        -> contexto.serialize(
                                                fecha.toString()
                                        )
                                )
                                .create();

                // Convertir usuario a JSON
                String json
                        = gsonRespuesta.toJson(
                                usuarioEncontrado
                        );

                // Código 200
                response.setStatus(
                        HttpServletResponse.SC_OK
                );

                // Enviar respuesta
                response.getWriter().print(
                        "{\"mensaje\":\"Inicio de sesión correcto\","
                        + "\"usuario\":"
                        + json
                        + "}"
                );

                // Mostrar resultado
                System.out.println(
                        "Inicio de sesión correcto."
                );

            } else {

                // Código 401
                response.setStatus(
                        HttpServletResponse.SC_UNAUTHORIZED
                );

                // Enviar mensaje
                response.getWriter().print(
                        "{\"mensaje\":\"Usuario o contraseña incorrectos\"}"
                );

                // Mostrar resultado
                System.out.println(
                        "Usuario o contraseña incorrectos."
                );
            }

        } else {

            // Código 400
            response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST
            );

            // Acción no válida
            response.getWriter().print(
                    "{\"mensaje\":\"Acción no válida\"}"
            );

            // Mostrar resultado
            System.out.println(
                    "Acción no válida."
            );
        }
    }

    // =========================================================
    // MÉTODO PUT - ACTUALIZAR USUARIO
    // =========================================================
    @Override
    protected void doPut(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Configurar respuesta JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Crear salida
        PrintWriter out
                = response.getWriter();

        // Mensaje temporal
        out.print(
                "{\"mensaje\":\"PUT todavía no implementado\"}"
        );

        // Vaciar salida
        out.flush();
    }

    // =========================================================
    // INFORMACIÓN DEL SERVLET
    // =========================================================
    @Override
    public String getServletInfo() {
        return "Controlador de usuarios";
    }
}
