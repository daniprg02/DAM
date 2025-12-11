package ExamenAD;

import java.io.*;
import java.util.ArrayList;

public class ej05 {
    public static void main(String[] args) {
        // Listas paralelas para datos por equipo (mantienen el orden de lectura)
        ArrayList<String> equipos = new ArrayList<>();
        ArrayList<Integer> operacionesPorEquipo = new ArrayList<>();
        ArrayList<Integer> tiemposPorEquipo = new ArrayList<>();
        ArrayList<ArrayList<String>> categoriasPorEquipo = new ArrayList<>(); // Sub-listas de categorías por equipo

        // Lista global para todas las categorías (para calcular dominante global)
        ArrayList<String> todasCategorias = new ArrayList<>();

        // Contadores globales
        int totalOperaciones = 0;
        int totalTiempo = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("redes.txt"))) {
            String linea;
            String equipoActual = null;
            ArrayList<String> categoriasEquipoActual = null;
            int opsEquipo = 0;
            int tiempoEquipo = 0;

            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("=== ")) {
                    // Guardar el equipo anterior si existe
                    if (equipoActual != null) {
                        equipos.add(equipoActual);
                        operacionesPorEquipo.add(opsEquipo);
                        tiemposPorEquipo.add(tiempoEquipo);
                        categoriasPorEquipo.add(categoriasEquipoActual);

                        totalOperaciones += opsEquipo;
                        totalTiempo += tiempoEquipo;
                    }
                    // Iniciar nuevo equipo
                    equipoActual = linea.substring(4, linea.length() - 4).trim(); // Ej. "EQUIPO-REDES"
                    categoriasEquipoActual = new ArrayList<>();
                    opsEquipo = 0;
                    tiempoEquipo = 0;
                } else if (linea.equals("END")) {
                    // Fin de sección, continuamos
                } else if (!linea.isEmpty()) {
                    // Línea de operación
                    opsEquipo++;
                    String[] partes = linea.split(";");
                    if (partes.length >= 5) {
                        String categoria = partes[2].trim();
                        categoriasEquipoActual.add(categoria);
                        todasCategorias.add(categoria); // Para global

                        String estado = partes[4].trim();
                        if (estado.equalsIgnoreCase("Finalizado")) {
                            int tiempo = Integer.parseInt(partes[3].trim());
                            tiempoEquipo += tiempo;
                        }
                    }
                }
            }
            // Guardar el último equipo
            if (equipoActual != null) {
                equipos.add(equipoActual);
                operacionesPorEquipo.add(opsEquipo);
                tiemposPorEquipo.add(tiempoEquipo);
                categoriasPorEquipo.add(categoriasEquipoActual);

                totalOperaciones += opsEquipo;
                totalTiempo += tiempoEquipo;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Calcular categorías dominantes por equipo (usando bucles simples)
        ArrayList<String> dominantesPorEquipo = new ArrayList<>();
        for (ArrayList<String> catsEquipo : categoriasPorEquipo) {
            String dominante = "";
            int maxCount = 0;
            ArrayList<String> unicas = new ArrayList<>(); // Para evitar contar repetidas
            for (String cat : catsEquipo) {
                if (!unicas.contains(cat)) {
                    unicas.add(cat);
                }
            }
            for (String unica : unicas) {
                int count = 0;
                for (String cat : catsEquipo) {
                    if (cat.equals(unica)) {
                        count++;
                    }
                }
                if (count > maxCount) {
                    maxCount = count;
                    dominante = unica;
                }
            }
            dominantesPorEquipo.add(dominante);
        }

        // Calcular categoría dominante global (mismo enfoque con bucles)
        String categoriaGlobalDominante = "";
        int maxGlobal = 0;
        ArrayList<String> unicasGlobal = new ArrayList<>();
        for (String cat : todasCategorias) {
            if (!unicasGlobal.contains(cat)) {
                unicasGlobal.add(cat);
            }
        }
        for (String unica : unicasGlobal) {
            int count = 0;
            for (String cat : todasCategorias) {
                if (cat.equals(unica)) {
                    count++;
                }
            }
            if (count > maxGlobal) {
                maxGlobal = count;
                categoriaGlobalDominante = unica;
            }
        }

        // Escribir en el archivo de salida
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("redesNew.txt"))) {
            bw.write("=== RESUMEN POR EQUIPO ===");
            bw.newLine();

            for (int i = 0; i < equipos.size(); i++) {
                bw.write(equipos.get(i));
                bw.newLine();
                bw.write("- Operaciones: " + operacionesPorEquipo.get(i));
                bw.newLine();
                bw.write("- Tiempo finalizado: " + tiemposPorEquipo.get(i) + " min");
                bw.newLine();
                bw.write("- Categoría dominante: " + dominantesPorEquipo.get(i));
                bw.newLine();
                bw.newLine(); // Espacio entre equipos
            }

            bw.write("=== RESUMEN GLOBAL ===");
            bw.newLine();
            bw.write("- Total de operaciones: " + totalOperaciones);
            bw.newLine();
            bw.write("- Categoría más frecuente global: " + categoriaGlobalDominante);
            bw.newLine();
            bw.write("- Tiempo total finalizado global: " + totalTiempo + " min");
            bw.newLine();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
