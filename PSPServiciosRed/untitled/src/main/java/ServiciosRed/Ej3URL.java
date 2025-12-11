package ServiciosRed;

import java.net.URL;

public class Ej3URL {
    // Crear URL relativas
    public static void main(String[] args) throws Exception {
        URL base = new URL("https://www.ejemplo.com/directorio/");
        URL completa = new URL(base, "pagina.html");

        System.out.println("URL completa: " + completa);

    }
}
