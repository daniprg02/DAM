package EXAMEN;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ArrayList<Jugador> jugadores = new ArrayList<>();
        Banca banca = new Banca(jugadores);
        ExecutorService pool = Executors.newFixedThreadPool(7);


        for (int i = 1; i <= 40; i++) {
            jugadores.add(new Jugador(banca, " nombre "));

        }


        for (int i = 0; i < jugadores.size(); i++) {

            Thread t = new Thread(jugadores.get(i));

            pool.submit(t); // Ejecuta en ThreadPool
        }

        pool.shutdown(); // No se aceptan más tareas
        pool.awaitTermination(30, TimeUnit.SECONDS);





    }
}
