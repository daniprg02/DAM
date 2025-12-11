package ServiciosRed;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Ej2URL {
    public static void main(String[] args) {
        // Leer contenido de una página we
        try {
            URL url = new URL("https://www.example.com");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream())
            );

            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }
            reader.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}




