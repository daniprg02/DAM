package Biblioteca;

import java.util.ArrayList;

public class MainBiblio {
    public static void main(String[]args) {
        ArrayList<Libro> libros = new ArrayList<>();
        Biblioteca biblioteca = new Biblioteca();
        Estudiante e1 = new Estudiante(1, biblioteca);
        Estudiante e2 = new Estudiante(2, biblioteca);
        Estudiante e3 = new Estudiante(3, biblioteca);
        Estudiante e4 = new Estudiante(4, biblioteca);
        Libro l1 = new Libro("Caperucita");
        Libro l2 = new Libro("Blancanieves");
        Libro l3 = new Libro("Los tres cerditos");

        libros.add(l1);
        libros.add(l2);
        libros.add(l3);

        biblioteca.setLibros(libros);

        e1.start();
        e2.start();
        e3.start();
        e4.start();

    }

}
