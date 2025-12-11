package Carniceria;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainCarniceria {
    public static void main(String[] args) {
        Semaphore semaforo = new Semaphore(4, true); // 4 mostradores
        Lock caja = new ReentrantLock(true);         // 1 caja, acceso justo

        //8 clientes
        for (int i = 1; i <= 8; i++) {
            new Thread(new Cliente(semaforo, caja, i)).start();
        }
    }
}

