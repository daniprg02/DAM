import java.io.*;
import java.util.ArrayList;

public class miExamen {
    public static void main(String[] args) {
        int contadorDeEventos = 0;
        int contadorAdvertencias = 0;
        int contadorConfirmados = 0;
        int contadorMatematicas = 0;
        int contadorIndices = 0;
        ArrayList<String> datos = new ArrayList<>();
        String linea = "";
        String fecha = "";

        try(BufferedReader reader = new BufferedReader(new FileReader(new File("datosMixtos.txt")))) {
            //Separamos la linea hasta que encuentre una fecha


            while ((linea = reader.readLine()) != null){

                if (linea.matches("\\d{1,2}-\\d{1,2}-\\d{4}")) {
                    fecha = linea;
                    contadorDeEventos = 0;
                    contadorConfirmados = 0;
                    contadorAdvertencias = 0;
                    contadorMatematicas = 0;
                }

                if (!linea.matches("\\d{1,2}-\\d{1,2}-\\d{4}")) {
                    contadorDeEventos++;
                }


                if (linea.toLowerCase().endsWith("matematicas")) {
                    contadorMatematicas++;
                }

                String[] partes = linea.split("\\s+", 2);

                for (int i = 0; i < partes.length; i++) {
                    if (partes[i].equalsIgnoreCase("Advertencia")) {
                        contadorAdvertencias++;
                    }
                    if (partes[i].equalsIgnoreCase("Confirmación")) {
                        contadorConfirmados++;
                    }


                }

                if (!linea.matches("\\d{1,2}-\\d{1,2}-\\d{4}")) {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("SolDaniel2.txt"), true))) {
//                        if(contadorIndices == 0) {
//                            writer.write("a) ");
//                            writer.newLine();
//                            contadorIndices++;
//                        }
                        writer.write(fecha + " --> " + contadorDeEventos);

//                        if(contadorIndices == 1) {
//                            writer.write("b) ");
//                            writer.newLine();
//                            contadorIndices++;
//                        }
                        writer.newLine();
                        writer.write(contadorAdvertencias + " eventos");

//                        if(contadorIndices == 2) {
//                            writer.write("c) ");
//                            writer.newLine();
//                            contadorIndices++;
//                        }
                        writer.newLine();
                        writer.write(fecha + " --> " + contadorConfirmados);

//                        if(contadorIndices == 3) {
//                            writer.write("d) ");
//                            writer.newLine();
//                            contadorIndices++;
//                        }
                        writer.newLine();
                        writer.write(fecha + " --> " + contadorMatematicas);
                        writer.newLine();
                    }
                }




            }


        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
