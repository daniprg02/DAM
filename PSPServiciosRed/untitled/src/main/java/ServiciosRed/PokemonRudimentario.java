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

public class PokemonRudimentario {

        public static String obtenerJSON(String url) throws Exception {
            HttpClient client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(url))
                    .header("User-Agent", "Mozilla/5.0")
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

                // Parsing manual del JSON (asumimos formato minificado)
                ArrayList<String> datosPokemon = new ArrayList<>();

                // Extraer name
                String name = extractStringValue(json, "\"name\":\"");
                if (name != null) datosPokemon.add("Name: " + name);

                // Extraer id (número)
                Integer id = extractIntValue(json, "\"id\":");
                if (id != null) datosPokemon.add("ID: " + id);

                // Extraer height (número)
                Integer height = extractIntValue(json, "\"height\":");
                if (height != null) datosPokemon.add("Height: " + height);

                // Extraer weight (número)
                Integer weight = extractIntValue(json, "\"weight\":");
                if (weight != null) datosPokemon.add("Weight: " + weight);

                // Extraer types (array de strings)
                String types = extractTypes(json);
                if (types != null) datosPokemon.add("Types: " + types);

                // Imprimir los datos
                for (String dato : datosPokemon) {
                    System.out.println(dato);
                }

            } catch (Exception e) {
                System.out.println("Ocurrió un error: " + e.getMessage());
                e.printStackTrace();
            }
        }

        // Metodo auxiliar para extraer un valor string entre comillas después de una clave
        private static String extractStringValue(String json, String key) {
            int start = json.indexOf(key);
            if (start == -1) return null;
            start += key.length();  // Avanza después de la clave
            int end = json.indexOf("\"", start);  // Encuentra el cierre de comillas
            if (end == -1) return null;
            return json.substring(start, end);  // Extrae el valor
        }

        // Metodo auxiliar para extraer un valor int después de una clave
        private static Integer extractIntValue(String json, String key) {
            int start = json.indexOf(key);
            if (start == -1) return null;
            start += key.length();  // Avanza después de la clave
            int end = json.indexOf(",", start);  // Encuentra el siguiente delimitador
            if (end == -1) end = json.indexOf("}", start);  // O el cierre de objeto
            if (end == -1) return null;
            String valueStr = json.substring(start, end).trim();  // Extrae y limpia
            try {
                return Integer.parseInt(valueStr);  // Convierte a int
            } catch (NumberFormatException e) {
                return null;  // Si no es número, ignora
            }
        }

        // Metodo auxiliar para extraer los tipos (maneja múltiples)
        private static String extractTypes(String json) {
            int start = json.indexOf("\"types\":[");
            if (start == -1) return null;
            start += "\"types\":[".length();  // Avanza al inicio del array
            int end = json.indexOf("]", start);  // Encuentra el fin del array
            if (end == -1) return null;
            String typesArray = json.substring(start, end);  // Extrae el array como string

            // Divide por objetos {} y extrae cada "name"
            String[] typeObjects = typesArray.split("\\},\\{");  // Separa por },{
            StringBuilder types = new StringBuilder();
            for (int i = 0; i < typeObjects.length; i++) {
                String obj = typeObjects[i].replace("{", "").replace("}", "");  // Limpia
                int nameStart = obj.indexOf("\"name\":\"");
                if (nameStart != -1) {
                    nameStart += "\"name\":\"".length();
                    int nameEnd = obj.indexOf("\"", nameStart);
                    if (nameEnd != -1) {
                        String typeName = obj.substring(nameStart, nameEnd);
                        types.append(typeName);
                        if (i < typeObjects.length - 1) types.append(", ");
                    }
                }
            }
            return types.length() > 0 ? types.toString() : null;
        }
    }

