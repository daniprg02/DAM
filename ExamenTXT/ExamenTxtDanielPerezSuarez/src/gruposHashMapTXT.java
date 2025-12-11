package ExamenAD;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class gruposHashMapTXT {
    public static void main(String[] args) throws IOException {

        // Clave: nombre del grupo
        // Valor: lista de líneas "nombre;valor"
        HashMap<String, ArrayList<String>> grupos = new HashMap<>();
        HashMap<String, Integer> totalPorGrupo = new HashMap<>();
        HashMap<String, Integer> contadorPersonas = new HashMap<>();
        int mayor = 0;
        String mayorGrupo = "";
        int contGrupos = 0;
        int contPersonas = 0;

        String grupoActual = null; // Para saber en qué grupo estoy

        try (BufferedReader br = new BufferedReader(new FileReader("registroGrupos.txt"))) {

            String linea;

            while ((linea = br.readLine()) != null) {

                // Saltar líneas vacías
                if (linea.isEmpty()) continue;

                // Si empieza un grupo
                if (linea.startsWith("GRUPO:")) {

                    grupoActual = linea.substring(6).trim(); // "A", "B",…
                    grupos.put(grupoActual, new ArrayList<>()); // Crear espacio para el grupo
                    continue;
                }

                // Si llegamos al cierre del grupo
                if (linea.equals("0")) {
                    contadorPersonas.put(grupoActual, contPersonas);
                    totalPorGrupo.put(grupoActual, contGrupos);
                    grupoActual = null;
                    contGrupos = 0;
                    contPersonas = 0;
                    continue;
                }

                // Si estamos dentro de un grupo, añadimos los datos
                if (grupoActual != null) {
                    grupos.get(grupoActual).add(linea);
                    String [] split = linea.split(";");
                    contGrupos+= Integer.parseInt(split[1]);
                    contPersonas++;

                    if(Integer.parseInt(split[1]) > mayor){
                        mayor = Integer.parseInt(split[1]);
                        mayorGrupo = linea;
                    }

                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // === Mostrar lo leído (para que veas que está perfecto) ===
        for (String g : grupos.keySet()) {
            System.out.println("GRUPO " + g);
            for (String persona : grupos.get(g)) {
                System.out.println("  " + persona);
            }
            System.out.println();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("NuevosAlumnos.txt"))) {

            for(String g : grupos.keySet()) {

                bw.write("GRUPO " + g);
                bw.newLine();

                bw.write("- Total acumulado: " + totalPorGrupo.get(g));
                bw.newLine();

                bw.write("- Personas: " + contadorPersonas.get(g));
                bw.newLine();

                bw.newLine(); // línea en blanco entre grupos
            }

            bw.write("mayor: " + mayorGrupo);
        }
    }
}
