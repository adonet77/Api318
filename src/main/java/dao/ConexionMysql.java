package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;

/**
 *
 * @author ADONET
 */
public class ConexionMysql {

    //Carga las variables del archivo .env
    private static final Dotenv dotenv = Dotenv.load();

    // Definir constantes
    private static final String USUARIO = dotenv.get("API_USER");
    private static final String CONTRASENA = dotenv.get("API_PASSWORD");
    private static final String BD = dotenv.get("API_NAME");
    private static final String IP = dotenv.get("API_HOST");
    private static final String PUERTO = dotenv.get("API_PORT");

    //URL para conectara Mysql
    private static final String URL = "jdbc:mysql://" + IP + ":" + PUERTO + "/" + BD + "?useSSL=false&serverTimezone=UTC";

    private Connection conexion;

    // Metodo establecerConexion()
    public Connection establecerConexion() {

        try {

            // Cargar manualmente el driver JDBC de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer la conexión con MySQL
            conexion = DriverManager.getConnection(
                    URL,
                    USUARIO,
                    CONTRASENA
            );

            // Mensaje de confirmación
            System.out.println("Conexion exitosa a MySQL");

        } catch (ClassNotFoundException e) {

            // El driver de MySQL no fue encontrado
            System.out.println(
                    "ERROR: No se encontró el driver de MySQL: "
                    + e.getMessage()
            );

        } catch (SQLException e) {

            // Error relacionado con la conexión
            System.out.println(
                    "ERROR al conectar: "
                    + e.getMessage()
            );
        }

        // Retornar la conexión
        return conexion;
    }

    // Metodo cerrarConexion()
    public void cerrarConexion() {

        try {
            if (conexion != null && !conexion.isClosed()) {
                //Cierra la conexion
                conexion.close();
                System.out.println("Conexion cerrada correctamente");
            }
        } catch (SQLException e) {
            System.out.println("ERROR al cerrar la conexion" + e.getMessage());
            //  e.printStackTrace();

        }

    }

}
