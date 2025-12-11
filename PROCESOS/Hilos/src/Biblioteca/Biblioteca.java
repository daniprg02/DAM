package Biblioteca;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Biblioteca {
    Estudiante estudiante;
    Semaphore semaphore;
    ArrayList<Libro> libros;

    public Biblioteca() {
        semaphore = new Semaphore(3);
    }

    public ArrayList<Libro> getLibros() {
        return libros;
    }

    public void setLibros(ArrayList<Libro> libros) {
        this.libros = libros;
    }

    public synchronized Libro prestarLibro(Estudiante estudiante) throws InterruptedException {
        // Intentar adquirir 1 permiso
        if (!semaphore.tryAcquire(1, 3, java.util.concurrent.TimeUnit.SECONDS)) {
            System.out.println("El estudiante " + estudiante.getNumEstudiante() +
                    " se cansó de esperar y se va sin leer.");
            return null;
        }

        Libro libroPrestado = null;

        for (int i = 0; i < libros.size(); i++) {
            Libro libroActual = libros.get(i);

            if(!libroActual.getLibroPrestado()){
                libroActual.setLibroPrestado(true);
                System.out.println("Libro " + libroActual + " prestado a estudiante " + estudiante.getNumEstudiante());
                libroPrestado = libroActual;
                break;
            }
        }

        if(libroPrestado == null){
            System.out.println("No hay libros para prestar al estudiante " + estudiante.getNumEstudiante());
            semaphore.release();
        }

        return libroPrestado;
    }

    public synchronized void devolverLibro(Estudiante estudiante){
        Libro libroDevuelto = estudiante.libroActual;
        libroDevuelto.setLibroPrestado(false);
        semaphore.release();
        System.out.println("Libro " + libroDevuelto + " devuelto por " + estudiante.getNumEstudiante());
    }

    @Override
    public String toString() {
        return "Biblioteca{" +
                ", estudiante=" + estudiante +
                ", semaphore=" + semaphore +
                ", libros=" + libros +
                '}';
    }
}
