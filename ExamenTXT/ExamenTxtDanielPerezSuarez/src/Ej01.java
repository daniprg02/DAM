import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Ej01 {
    public static void main(String[] args) {

        try(BufferedReader br = new BufferedReader(new FileReader("libros.txt"))) {
            String paginas;
            ArrayList<String> libros = new ArrayList<>();
            int libroMasPagina = 0;
            String nombreMasPagina = "";
            float precioPromedio = 0;
            int limiteFecha = 1950;
            ArrayList<String> librosFechas = new ArrayList<>();

            String linea = "";
            while((linea = br.readLine()) != null){
                libros.add(linea);

            }

            for(int i = 0; i < libros.size(); i++){
                String[] palabras = libros.get(i).split(";");
                paginas = palabras[4];
                precioPromedio += Float.parseFloat(palabras[3]);
                System.out.println(paginas);
                if (Integer.parseInt(paginas) > libroMasPagina){
                    libroMasPagina = Integer.parseInt(paginas);
                    nombreMasPagina = palabras[0];
                }
                if (Integer.parseInt(palabras[2]) > limiteFecha){
                    librosFechas.add(libros.get(i));
                }

            }

            System.out.println("El libro con más páginas es " +nombreMasPagina + " y tiene " + libroMasPagina );
            System.out.println("El precio promedio de los libros es " + precioPromedio / libros.size());
            System.out.println("Los libros con fecha mayor a 1950 son: " + librosFechas);



        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}
