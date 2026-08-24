package util;

public class PruebaPassword {

    public static void main(String[] args) {

        String password = "123456";

        String hash = PasswordUtil.generarHash(password);

        System.out.println("Contraseña original: " + password);
        System.out.println("Hash generado: " + hash);
    }
}