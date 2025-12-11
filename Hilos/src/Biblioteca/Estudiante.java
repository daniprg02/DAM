package Biblioteca;

import java.util.concurrent.ThreadLocalRandom;

public class Estudiante extends Thread {
    int numEstudiante;
    Biblioteca biblio;
    Libro libroActual;

    public Estudiante(int numEstudiante, Biblioteca biblio) {
        this.numEstudiante = numEstudiante;
        this.biblio = biblio;
    }

    public int getNumEstudiante() {
        return numEstudiante;
    }

    public void setNumEstudiante(int numEstudiante) {
        this.numEstudiante = numEstudiante;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // 1. Pedir libro en la biblioteca
                this.libroActual = biblio.prestarLibro(this);
                
                if (this.libroActual != null) {
                    // 2. SALIR de la biblioteca y leer FUERA
                    int tiempoLectura = ThreadLocalRandom.current().nextInt(1000, 3000);
                    System.out.println("📖 Estudiante " + numEstudiante +
                            " está leyendo \"" + libroActual.getNombreLibro() +
                            "\" durante " + (tiempoLectura / 1000.0) + "s");
                    
                    Thread.sleep(tiempoLectura);  // ← FUERA del synchronized
                    
                    // 3. Volver a devolver el libro
                    biblio.devolverLibro(this);
                }
                
                // Pequeña pausa antes de volver a pedir otro libro
                Thread.sleep(500);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("El estudiante " + numEstudiante + " fue interrumpido.");
                break;
            }
        }
    }
}