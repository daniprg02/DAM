package EXAMENDEPS;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        // Lista de jugadores (referencia compartida con la ruleta)
        List<Jugador> jugadores = new ArrayList<>();
        int jugadoresCont = 40;
        Thread[] hilosJugadores = new Thread[jugadoresCont];
        Ruleta ruleta = new Ruleta(jugadores);
        ExecutorService pool = Executors.newFixedThreadPool(7);
        // Crear 40 jugadores
        for (int i = 1; i <= 41; i++) {
            jugadores.add(new Jugador("Jugador-" + i, ruleta));
            Thread t = new Thread(jugadores.get(i-1));
            hilosJugadores[i] = t;
            pool.submit(t);
        }



        ruleta.start();


    }
}
