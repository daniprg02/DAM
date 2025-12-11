package ServiciosRed;

import java.net.MalformedURLException;
import java.net.URL;

public class Ej1URL {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://aulavirtual-educacion.larioja.org/course/view.php?id=14831");

            System.out.println("Protocolo = " + url.getProtocol());
            System.out.println("Host = " + url.getHost());
            System.out.println("Port = " + url.getDefaultPort());
            System.out.println("Ruta = " + url.getPath());
            System.out.println("Query = " + url.getQuery());
            System.out.println("Referencia = " + url.getRef());
            System.out.println("Archivo = " + url.getFile());
            System.out.println("URL = " + url.toExternalForm());


        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
