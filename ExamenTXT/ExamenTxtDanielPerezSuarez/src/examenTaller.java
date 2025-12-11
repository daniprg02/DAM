package ExamenAD;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class examenTaller {
    public static void main(String[] args) {

        // HashMaps requeridos
        HashMap<String, Integer> operacionesPorTaller = new HashMap<>();
        HashMap<String, Integer> realizadasPorTaller = new HashMap<>();
        HashMap<String, Double> importePorTaller = new HashMap<>();
        HashMap<String, Integer> contadorOperaciones = new HashMap<>();

        // Variables globales
        int pendientesTotal = 0;
        String operacionMasCara_matricula = "";
        String operacionMasCara_nombre = "";
        double operacionMasCara_precio = 0.0;

        String tallerActual = null;

        try (BufferedReader br = new BufferedReader(new FileReader("operacionesTaller2025.txt"))) {

            String linea;

            while ((linea = br.readLine()) != null) {
                linea = linea.trim();

                // Ignorar líneas vacías o ruido
                if (linea.isEmpty() ||
                        linea.startsWith("#") ||
                        linea.startsWith("---") ||
                        linea.equals("FIN-SECCION") ||
                        linea.equals("Información no relevante") ||
                        linea.equals("Más texto que debes ignorar")) {
                    continue;
                }

                // Detectar cambios de taller
                if (linea.startsWith("=== TALLER:")) {
                    tallerActual = linea.substring(11, linea.length() - 4).trim();
                    continue;
                }
                if (linea.equals("CAMBIO-DE-TALLER") || linea.equals("*** CAMBIO ***")) {
                    // El taller se actualizará cuando aparezca el siguiente "=== TALLER: ..."
                    continue;
                }

                // Si la línea no tiene taller actual → ignorar operaciones iniciales
                if (tallerActual == null) {
                    continue;
                }

                // Ahora sí: procesar registros válidos de operaciones
                String[] datos = linea.split(";");
                if (datos.length < 4) continue;

                String matricula = datos[0];
                String operacion = datos[1];
                double precio = Double.parseDouble(datos[2]);
                String estado = datos[3];

                // a) Contar operaciones por taller
                operacionesPorTaller.put(tallerActual,
                        operacionesPorTaller.getOrDefault(tallerActual, 0) + 1);

                // b) Contar realizadas y sumar importe
                if (estado.equalsIgnoreCase("Realizado")) {
                    realizadasPorTaller.put(tallerActual,
                            realizadasPorTaller.getOrDefault(tallerActual, 0) + 1);

                    importePorTaller.put(tallerActual,
                            importePorTaller.getOrDefault(tallerActual, 0.0) + precio);
                }

                // c) Contar operación más frecuente
                contadorOperaciones.put(operacion,
                        contadorOperaciones.getOrDefault(operacion, 0) + 1);

                // d) Contar pendientes
                if (estado.equalsIgnoreCase("Pendiente")) {
                    pendientesTotal++;
                }

                // e) Operación más cara
                if (precio > operacionMasCara_precio) {
                    operacionMasCara_precio = precio;
                    operacionMasCara_matricula = matricula;
                    operacionMasCara_nombre = operacion;
                }
            }

        } catch (IOException e) {
            System.out.println("Error al leer archivo");
            return;
        }

        // Encontrar operación más frecuente
        String operacionMasFrecuente = null;
        int maxFrecuencia = 0;
        for (String op : contadorOperaciones.keySet()) {
            int cant = contadorOperaciones.get(op);
            if (cant > maxFrecuencia) {
                maxFrecuencia = cant;
                operacionMasFrecuente = op;
            }
        }

        // === MOSTRAR POR CONSOLA ===
        System.out.println("=== RESUMEN POR TALLER ===\n");
        for (String taller : operacionesPorTaller.keySet()) {
            System.out.println("Taller " + taller);
            System.out.println("- Operaciones registradas: " + operacionesPorTaller.get(taller));
            System.out.println("- Operaciones realizadas: " +
                    realizadasPorTaller.getOrDefault(taller, 0));

            double importe = importePorTaller.getOrDefault(taller, 0.0);
            System.out.printf("- Importe total realizado: %.2f €\n\n", importe);
        }

        System.out.println("Operación más realizada: " +
                operacionMasFrecuente + " (" + maxFrecuencia + " unidades)");
        System.out.println("\nOperación más cara:");
        System.out.println("- Matrícula: " + operacionMasCara_matricula);
        System.out.println("- Operación: " + operacionMasCara_nombre);
        System.out.printf("- Precio: %.2f €\n", operacionMasCara_precio);

        System.out.println("\nTotal de operaciones pendientes: " + pendientesTotal);

        // === GENERAR INFORME ===
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("informeTaller2025.txt"))) {

            bw.write("=== INFORME TALLER 2025 ===\n\n");

            for (String taller : operacionesPorTaller.keySet()) {
                bw.write("Taller " + taller + "\n");
                bw.write("- Operaciones registradas: " + operacionesPorTaller.get(taller) + "\n");
                bw.write("- Operaciones realizadas: " +
                        realizadasPorTaller.getOrDefault(taller, 0) + "\n");
                bw.write(String.format("- Importe total realizado: %.2f €\n\n",
                        importePorTaller.getOrDefault(taller, 0.0)));
            }

            bw.write("Operación más realizada: " + operacionMasFrecuente +
                    " (" + maxFrecuencia + " unidades)\n\n");

            bw.write("Operación más cara:\n");
            bw.write("- Matrícula: " + operacionMasCara_matricula + "\n");
            bw.write("- Operación: " + operacionMasCara_nombre + "\n");
            bw.write(String.format("- Precio: %.2f €\n\n", operacionMasCara_precio));

            bw.write("Total de operaciones pendientes: " + pendientesTotal + "\n");

        } catch (IOException e) {
            System.out.println("Error al generar informe");
        }

        System.out.println("\nInforme generado: informeTaller2025.txt");
    }
}
