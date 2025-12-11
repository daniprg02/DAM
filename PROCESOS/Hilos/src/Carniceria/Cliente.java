package Carniceria;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cliente implements Runnable {
    private final Semaphore semaforo; // controla cuántos pueden pedir a la vez
    private final Lock caja;          // controla el acceso exclusivo a la caja
    private final int id;

    public Cliente(Semaphore semaforo, Lock caja, int id) {
        this.semaforo = semaforo;
        this.caja = caja;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            // Espera un mostrador libre
            semaforo.acquire();
            System.out.println("El cliente "+ id +" ha pedido 2kg de chuletas de cordero");
            Thread.sleep(500); // tiempo de pedido

            // 2Pasa a pagar (zona crítica exclusiva)
            caja.lock();
            try {
                System.out.println("El cliente "+ id + " ha pagado su pedido");
                Thread.sleep(300); // tiempo de pago
            } finally {
                caja.unlock();
                System.out.println("Cliente " + id + ": Hasta luego");
            }

            //  Se va, libera el mostrador
            semaforo.release();


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
