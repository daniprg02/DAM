package ServiciosRed;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class PokemonJAR {

    public static String obtenerJSON(String url) throws Exception {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .header("User-Agent", "Mozilla/5.0")   // evita bloqueos
                .header("Accept", "application/json")
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8)
        );

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new Exception("Error HTTP " + response.statusCode() + ". ¿El Pokémon existe?");
        }
    }

    public static void guardarJSON(String ruta, String contenido) throws Exception {
        Files.writeString(Path.of(ruta), contenido, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Elige un pokemon y te daré su información del JSON");
        String pokemon = sc.nextLine();

        try {
            String url = "https://pokeapi.co/api/v2/pokemon/" + pokemon;
            String json = obtenerJSON(url);
            System.out.println("=== JSON recibido ===");

            String ruta = "pokemon.json";
            guardarJSON(ruta, json);
            System.out.println("\nArchivo guardado en: " + ruta);

            // Parsear el JSON con Gson
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

            // Extraer los campos deseados
            ArrayList<String> datosPokemon = new ArrayList<>();
            String name = jsonObject.get("name").getAsString();
            int id = jsonObject.get("id").getAsInt();
            int height = jsonObject.get("height").getAsInt();
            int weight = jsonObject.get("weight").getAsInt();

            // Tipos (puede haber múltiples)
            JsonArray typesArray = jsonObject.getAsJsonArray("types");
            StringBuilder types = new StringBuilder("Types: ");
            for (int i = 0; i < typesArray.size(); i++) {
                String typeName = typesArray.get(i).getAsJsonObject()
                        .get("type").getAsJsonObject()
                        .get("name").getAsString();
                types.append(typeName);
                if (i < typesArray.size() - 1) types.append(", ");
            }

            // Añadir al ArrayList (con check de duplicados, aunque no es necesario aquí)
            if (!datosPokemon.contains(name)) datosPokemon.add("Name: " + name);
            if (!datosPokemon.contains(String.valueOf(id))) datosPokemon.add("ID: " + id);
            if (!datosPokemon.contains(String.valueOf(height))) datosPokemon.add("Height: " + height);
            if (!datosPokemon.contains(String.valueOf(weight))) datosPokemon.add("Weight: " + weight);
            if (!datosPokemon.contains(types.toString())) datosPokemon.add(types.toString());

            // Imprimir los datos
            for (String dato : datosPokemon) {
                System.out.println(dato);
            }

        } catch (Exception e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
            e.printStackTrace();  // Para depuración
        }
    }
}
