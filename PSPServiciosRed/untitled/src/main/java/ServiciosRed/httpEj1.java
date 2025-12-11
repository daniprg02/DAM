package ServiciosRed;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class httpEj1 {

    public int almacenarPagina(String palabra, String destino) throws Exception {

        // WordReference solo funciona en esta ruta
        String recurso = URLEncoder.encode(palabra, StandardCharsets.UTF_8);
        String direccion = "https://www.wordreference.com/definicion/" + recurso;

        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(direccion))
                .header("User-Agent", "Mozilla/5.0")
                .header("Accept", "text/html")
                .build();

        HttpResponse<Path> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofFile(Path.of(destino))
        );

        return response.statusCode();
    }

    public static void main(String[] args) {

        httpEj1 gestor = new httpEj1();

        try {
            String palabra = "casa";
            String destino = "wordreference.html";

            int codigo = gestor.almacenarPagina(palabra, destino);

            if (codigo == HttpURLConnection.HTTP_OK) {
                System.out.println("Descarga finalizada");
            } else {
                System.err.println("Error HTTP " + codigo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


