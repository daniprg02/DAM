import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.System.exit;

public class Ej02 {
    public static void main(String[] args) {
        System.out.println("Bienvenido al sistema de la biblioteca");
        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        ArrayList<String> libros = new ArrayList<>();

        System.out.println("1 - Mostrar todos los libros y su estado\n" +
                "\n" +
                "2 - Buscar libro por título\n" +
                "\n" +
                "3 - Prestar libro: cambiar estado a \"Prestado\"\n" +
                "\n" +
                "4 - Devolver libro: cambiar estado a \"Disponible\"\n" +
                "\n" +
                "5 - Añadir nuevo libro a la biblioteca\n" +
                "\n" +
                "6 - Salir");

        opcion = sc.nextInt();

        switch (opcion){
            case 1:
                libros = cargarLibros(new File("libros.txt"));

                for (int i = 0; i < libros.size(); i++) {
                    System.out.println(libros.get(i));
                }

                break;
            case 2:
                sc.nextLine();
                System.out.println("Indica el libro que quieres buscar: ");
                String libro = sc.nextLine();
                buscarLibroTitulo(libro, new File("libros.txt"));
                break;

            case 3:
                sc.nextLine();
                System.out.println("Indica el libro que quieres tomar prestado: ");
                String libroPrestado = sc.nextLine();
                prestrarLibro(libroPrestado, new File("libros.txt"));

            case 4:
                sc.nextLine();
                System.out.println("Indica el libro que quieres devolver");
                String libroDevuelto = sc.nextLine();
                devolverLibro(libroDevuelto, new File("libros.txt"));

            case 5:
                sc.nextLine();
                System.out.println("Indica el libro");
                String libroAnadido = sc.nextLine();
                System.out.println("Indica el estado (Disponible/Prestado)");
                String estado = sc.nextLine();
                anadirLibro(libroAnadido, estado, new File("libros.txt"));

            case 6:
                System.out.println("Hasta luego");
                exit(0);
        }



    }

    public static ArrayList<String> cargarLibros(File fichero) {
        ArrayList<String> datos = new ArrayList<>();


        try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {

            String linea = "";
            while ((linea = br.readLine()) != null) {
                datos.add(linea);
            }


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return datos;
    }

    public static void buscarLibroTitulo(String libro, File fichero) {
        ArrayList<String> datos = new ArrayList<>();
        datos = cargarLibros(fichero);

        for(int i = 0; i < datos.size(); i++){
            String [] palabras = datos.get(i).split(";");
            String titulo = palabras[0];

            if(titulo.equalsIgnoreCase(libro)) {
                System.out.println("Libro encontrado: ");
                System.out.println(datos.get(i));

                break;
            }
        }

    }

    public static void prestrarLibro(String libro, File fichero){
        ArrayList<String> datos = new ArrayList<>();
        datos = cargarLibros(fichero);

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fichero))) {

            for (int i = 0; i < datos.size(); i++) {
                String[] palabras = datos.get(i).split(";");
                String titulo = palabras[0];
                String prestado = palabras[1];

                if (titulo.equalsIgnoreCase(libro) && prestado.equalsIgnoreCase("Disponible")) {
                    datos.set(i, datos.get(i).replace("Disponible", "Prestado"));
                    System.out.println("Libro prestado -- " + titulo);
                    break;
                } else {
                    System.out.println("Libro ya prestado -- " + titulo);
                    break;
                }

            }

            for(int i = 0; i < datos.size(); i++){
                if (i>0) {
                    bw.newLine();
                }
                bw.write(datos.get(i));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void devolverLibro(String libro, File fichero){
        ArrayList<String> datos = new ArrayList<>();
        datos = cargarLibros(fichero);

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fichero))) {

            for (int i = 0; i < datos.size(); i++) {
                String[] palabras = datos.get(i).split(";");
                String titulo = palabras[0];
                String prestado = palabras[1];

                if (titulo.equalsIgnoreCase(libro) && prestado.equalsIgnoreCase("Prestado")) {
                    datos.set(i, datos.get(i).replace("Prestado", "Disponible"));
                    System.out.println("Libro devuelto -- " + titulo);
                    break;
                } else {
                    System.out.println("Libro ya devuelto -- " + titulo);
                    break;
                }

            }

            for(int i = 0; i < datos.size(); i++){
                if (i>0) {
                    bw.newLine();
                }
                bw.write(datos.get(i));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void anadirLibro(String libro, String prestado, File fichero){

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fichero, true))){
            bw.newLine();
            bw.write(libro + ";" + prestado);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
