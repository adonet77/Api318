
package dao;

import java.sql.Connection;

/**
 *
 * @author ADONET
 */
public class ProbarConexion {
    public static void main(String[] args) {
        ConexionMysql  conexionBD = new ConexionMysql();
        Connection conexion;
        conexion = conexionBD.establecerConexion();
        
        //Validamos conexion es diferente a null
        
        if (conexion !=null) {
            System.out.println("La conexion fue establecida correctamente");
        } else {
            System.out.println("No fue posible conectar con la BD");
        }
        //Cerra conexion
        conexionBD.cerrarConexion();
        System.out.println("Programa finalizado");
        
        
    }
}
