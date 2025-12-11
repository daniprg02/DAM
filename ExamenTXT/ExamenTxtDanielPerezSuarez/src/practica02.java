package ExamenAD;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class practica02 {
    public static void main(String[] args) {
        ArrayList<String> a = new ArrayList<>();
        int contLibros = 0;
        float totalPrecio = 0;
        String [] arrayLinea;
        int libroMasPagina = 0;
        String autor = "";
        String titulo = "";
        float precio = 0;
        ArrayList<String> nuevaLinea = new ArrayList<>();

        Scanner sc = new Scanner(System.in);

        System.out.println("Bievenido a la libreria, digame el autor del libro que quieres buscar:");
        autor = sc.nextLine();


        try(BufferedReader br = new BufferedReader(new FileReader("ej02PruebaExamen.txt"))){

            String linea;
            while((linea = br.readLine())!=null) {
                if(linea.startsWith("[")) {
                    if(contLibros != 0) {
                        a.add("-> " + contLibros);
                        contLibros = 0;
                    }

                    a.add(linea);
                } else {
                    if(!linea.isEmpty()){
                        contLibros++;

                        arrayLinea = linea.split(";");
                        if(arrayLinea[1].equalsIgnoreCase(autor)){
                            titulo = arrayLinea[0];
                            precio = Float.parseFloat(arrayLinea[3]);
                        }
                        totalPrecio += Float.parseFloat(arrayLinea[3]);

                        float precioAux;
                        precioAux = Float.parseFloat(arrayLinea[3]);
                        if(precioAux > 18){
                            float descuento = (float) (precioAux * 0.10);
                            precioAux = precioAux - descuento;
                            arrayLinea[3] = String.valueOf(precioAux);
                        }
                        nuevaLinea.add(String.join(";", arrayLinea));

                        int paginas = Integer.parseInt(arrayLinea[2]);
                        if(paginas > libroMasPagina){
                            libroMasPagina = paginas;
                        }
                        System.out.println(linea);
                    }

                }


            }
            if(contLibros != 0){
                a.add("-> " + contLibros);
            }

            System.out.println(totalPrecio);
            System.out.println(libroMasPagina);



            try(BufferedWriter bw = new BufferedWriter(new FileWriter("ej02Solucion.txt"))){
                for(int i = 0; i < a.size(); i++){
                    bw.write(a.get(i));
                    bw.newLine();
                }
                bw.write("Total: " + totalPrecio);
                bw.newLine();
                bw.write("El libro con más páginas tiene " + libroMasPagina + " páginas");
                bw.newLine();
                bw.write(titulo + " - " + precio + " € ");
            }

            try(BufferedWriter bw2 = new BufferedWriter(new FileWriter("CatálogoRebajado.txt"))){
                for(int i = 0; i < nuevaLinea.size(); i++){
                    bw2.write(nuevaLinea.get(i));
                    bw2.newLine();
                }

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
