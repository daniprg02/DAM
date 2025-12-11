import java.util.concurrent.*;
import java.util.concurrent.locks.*;

// Clase Restaurante
class Restaurante {
    private final int numMesas;
    private int mesasDisponibles;
    private final Lock lock = new ReentrantLock(); // Protege acceso a mesasDisponibles

    public Restaurante(int numMesas) {
        this.numMesas = numMesas;
        this.mesasDisponibles = numMesas;
    }

    // Mtodo para que un cliente entre al restaurante
    public void entrar(String nombre) throws InterruptedException {
        synchronized (this) {
            // Espera mientras no haya mesas disponibles
            while (mesasDisponibles == 0) {
                System.out.println(nombre + " espera mesa...");
                wait(); // Se bloquea hasta que alguien haga notifyAll
            }
            mesasDisponibles--;
            System.out.println(nombre + " consigue una mesa. Mesas libres: " + mesasDisponibles);
        }
    }

    // Mtodo para que un cliente salga del restaurante
    public void salir(String nombre) {
        lock.lock(); // Protege la actualización de mesasDisponibles
        try {
            mesasDisponibles++;
            System.out.println(nombre + " se va. Mesas libres: " + mesasDisponibles);
            synchronized (this) {
                notifyAll(); // Notifica a clientes que esperan
            }
        } finally {
            lock.unlock();
        }
    }
}

// Clase Cliente implementando Runnable
class Cliente implements Runnable {
    private final Restaurante restaurante;
    private final String nombre;

    public Cliente(Restaurante restaurante, String nombre) {
        this.restaurante = restaurante;
        this.nombre = nombre;
    }

    @Override
    public void run() {
        try {
            restaurante.entrar(nombre); // Intenta entrar al restaurante
            // Simula tiempo comiendo
            Thread.sleep((long)(Math.random() * 2000));
            restaurante.salir(nombre); // Sale del restaurante
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class prueba {
    public static void main(String[] args) throws InterruptedException {
        Restaurante restaurante = new Restaurante(3); // 3 mesas
        int numClientes = 10;

        // Usamos ThreadPool para ejecutar los clientes
        ExecutorService pool = Executors.newFixedThreadPool(5);

        // Array de hilos para usar join
        Thread[] hilosClientes = new Thread[numClientes];

        for (int i = 0; i < numClientes; i++) {
            Cliente cliente = new Cliente(restaurante, "Cliente-" + (i+1));
            Thread t = new Thread(cliente);
            hilosClientes[i] = t;
            pool.submit(t); // Ejecuta en ThreadPool
        }

        pool.shutdown(); // No se aceptan más tareas
        pool.awaitTermination(30, TimeUnit.SECONDS); // Espera que todos terminen

        // Usamos join para asegurarnos que todos los hilos han terminado
        for (Thread t : hilosClientes) {
            t.join();
        }

        System.out.println("Todos los clientes han sido atendidos.");
    }
}
