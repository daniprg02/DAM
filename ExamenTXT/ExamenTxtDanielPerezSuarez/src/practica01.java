package ExamenAD;

import java.io.*;
import java.util.ArrayList;

public class practica01 {
    public static void main(String[] args) {
        int contEventos = 0;
        int contAdvertencias = 0;
        int contConfirmados = 0;
        int contMatematicas = 0;
        ArrayList<String> a = new ArrayList<>();
        ArrayList<String> b = new ArrayList<>();
        ArrayList<String> c = new ArrayList<>();
        ArrayList<String> d = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader("datosMixtos.txt"))){

            String linea = "";

            while((linea = br.readLine())!=null) {

                if (linea.matches("\\d{1,2}-\\d{1,2}-\\d{4}")) {
                    String fecha = linea;
                    if(contEventos != 0) {
                        a.add("--> " + contEventos);
                        contEventos = 0;

                    }
                    if(contConfirmados!=0){
                        c.add(" --> " + contConfirmados);
                        contConfirmados = 0;
                    }
                    if(contMatematicas!=0){
                        d.add("--> " + contMatematicas);
                        contMatematicas = 0;
                    }
                    if(!a.contains(fecha)) {
                        a.add(fecha);
                    }
                    if(!c.contains(fecha)) {
                        c.add(fecha);
                    }
                    if(!d.contains(fecha)) {
                        d.add(fecha);
                    }

                } else {
                    linea = linea.replaceAll("\\s+", " ");
                    if (!linea.isEmpty()) {
                        contEventos++;
                    }
                    if(linea.contains("Advertencia")){
                        contAdvertencias++;
                    }
                    if(linea.contains("Confirmación")){
                        contConfirmados++;
                    }
                    if(linea.toLowerCase().endsWith("matematicas")){
                        contMatematicas++;
                    }
                }
            }

            if(contEventos != 0) {
                a.add("--> " + contEventos);
            }
            if(contConfirmados!=0){
                c.add(" --> " + contConfirmados);
            }
            if(contMatematicas!=0){
                d.add("--> " + contMatematicas);
            }

            b.add(contAdvertencias + " eventos");

            try(BufferedWriter bw = new BufferedWriter(new FileWriter("SolDaniel2.txt", true))) {
                bw.write("a) ");
                bw.newLine();
                for (int i = 0; i < a.size(); i++) {
                    bw.write(a.get(i));
                    bw.newLine();
                }

                bw.write("b) ");
                bw.newLine();
                for (int i = 0; i < b.size(); i++) {
                    bw.write(b.get(i));
                }
                bw.newLine();

                bw.write("c) ");
                bw.newLine();
                for (int i = 0; i < c.size(); i++) {
                    bw.write(c.get(i));
                    bw.newLine();
                }
                bw.newLine();
                bw.write("d) ");
                bw.newLine();
                for (int i = 0; i < d.size(); i++) {
                    bw.write(d.get(i));
                    bw.newLine();
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
