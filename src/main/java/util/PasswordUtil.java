package util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // Método encargado de generar un hash para una contraseña
    public static String generarHash(String password) {

        // Generar y devolver el hash utilizando BCrypt
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Método encargado de comprobar una contraseña
    // contra un hash almacenado
    public static boolean verificarPassword(
            String password,
            String hash
    ) {

        // Comparar la contraseña ingresada con el hash
        // utilizando BCrypt
        return BCrypt.checkpw(password, hash);
    }
}
