package EXAMENDEPS;

import java.util.Random;

public class Jugador implements Runnable {
    private final Ruleta ruleta;
    private final String nombre;
    
    private int saldo = 300;

    public Jugador(String nombre, Ruleta ruleta) {
        this.nombre = nombre;
        this.ruleta = ruleta;
    }

    @Override
    public void run() {
        try {
            ruleta.esperarComienzo(this);
            ruleta.casinoAbierto();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    // Retorna false si el jugador se queda sin dinero
    public boolean apostar(int numeroGanador) {

        return false;
    }
}
