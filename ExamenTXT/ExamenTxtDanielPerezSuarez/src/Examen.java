import java.io.*;
import java.util.ArrayList;

public class Examen {
    public static void main(String[] args) {
        int contadorDeEventos = 0;
        int contadorAdvertencias = 0;
        int contadorConfirmados = 0;
        int contadorMatematicas = 0;
        int contadorIndices = 0;
        ArrayList<String> datos = new ArrayList<>();
        String linea = "";
        String fecha = "";

        try(BufferedReader reader = new BufferedReader(new FileReader(new File("datosMixtos.txt")));
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("SolDaniel.txt"), true))) {

            //Leemos línea a línea
            while ((linea = reader.readLine()) != null){

                if (linea.matches("\\d{1,2}-\\d{1,2}-\\d{4}")) {
                    // Si ya teníamos una fecha previa, escribimos sus contadores antes de reiniciar
                    if (!fecha.equals("")) {
                        writer.write(fecha + " --> " + contadorDeEventos);
                        writer.newLine();
                        writer.write(contadorAdvertencias + " eventos");
                        writer.newLine();
                        writer.write(fecha + " --> " + contadorConfirmados);
                        writer.newLine();
                        writer.write(fecha + " --> " + contadorMatematicas);
                        writer.newLine();
                        writer.newLine();
                    }

                    fecha = linea;
                    contadorDeEventos = 0;
                    contadorConfirmados = 0;
                    contadorAdvertencias = 0;
                    contadorMatematicas = 0;
                    continue; // pasamos a la siguiente línea
                }

                if (!linea.matches("\\d{1,2}-\\d{1,2}-\\d{4}")) {
                    if (!linea.trim().isEmpty()) {
                        contadorDeEventos++;
                    }
                }

                if (linea.toLowerCase().endsWith("matematicas")) {
                    contadorMatematicas++;
                }

                String[] partes = linea.split("\\s+", 2);

                for (int i = 0; i < partes.length; i++) {
                    if (partes[i].equalsIgnoreCase("Advertencia")) {
                        contadorAdvertencias++;
                    }
                    // Acepta "Confirmación" con y sin tilde
                    if (partes[i].equalsIgnoreCase("Confirmación") || partes[i].equalsIgnoreCase("Confirmacion")) {
                        contadorConfirmados++;
                    }
                }
            }

            // Al acabar, escribimos la última fecha si existe
            if (!fecha.equals("")) {
                writer.write(fecha + " --> " + contadorDeEventos);
                writer.newLine();
                writer.write(contadorAdvertencias + " eventos");
                writer.newLine();
                writer.write(fecha + " --> " + contadorConfirmados);
                writer.newLine();
                writer.write(fecha + " --> " + contadorMatematicas);
                writer.newLine();
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
