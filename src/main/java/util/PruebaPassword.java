package util;

public class PruebaPassword {

    public static void main(String[] args) {

        String password = "adonet";

        String hash = PasswordUtil.generarHash(password);

        System.out.println("Contraseña original: " + password);
        System.out.println("Hash generado: " + hash);
        
        //Comprobar la contraseña correcta
        boolean resultado = PasswordUtil.verificarPassword(password, hash);
        System.out.println("La contraseña es correcta? " + resultado);
        System.out.println("========================");
        
        
        System.out.println("========================");
        
        //Comprobar la contraseña incorrecta
        boolean resultadoIncorrecto = PasswordUtil.verificarPassword("123456", hash);
        System.out.println("La contraseña 123456  es correcta? " + resultadoIncorrecto);
    }
}