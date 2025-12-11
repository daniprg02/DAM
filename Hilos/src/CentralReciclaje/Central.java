package CentralReciclaje;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Central {
    String nombreCentral;
    Semaphore cintaDescarga;
    Lock lockCentral;
    boolean clasificadora;

    public Central(String nombreCentral) {
        this.nombreCentral = nombreCentral;
        this.cintaDescarga = new Semaphore(3);
        this.lockCentral = new ReentrantLock();
        this.clasificadora = false;
    }

    public void descargarResiduos(Camion camion) throws InterruptedException {
        cintaDescarga.acquire();

        int tiempoAleatorio = ThreadLocalRandom.current().nextInt(0, 2000);

        System.out.println("Camion " + camion.getMatricula() + " empieza a descargar residuos");
        Thread.sleep(tiempoAleatorio);

        cintaDescarga.release();

        System.out.println("Camion " + camion.getMatricula() + " ha terminado de descargar residuos");

    }

    public synchronized void clasificarResiduos(Camion camion) throws InterruptedException {

        while(clasificadora){
            wait();
        }

        int tiempoAleatorio = ThreadLocalRandom.current().nextInt(0, 2000);

        System.out.println("Camion " + camion.getMatricula() + " clasificando residuos...");
        Thread.sleep(tiempoAleatorio);

        System.out.println("Camion " + camion.getMatricula() + " se marcha a otro punto...");
    }

    public void compactarResiduos(Camion camion) throws InterruptedException {
        lockCentral.lock();

        try{
            synchronized(this){
                clasificadora = true;
                System.out.println("Camion " + camion.getMatricula() + " empieza a compactar residuos");
            }

            Thread.sleep(2000);

            synchronized(this){
                clasificadora = false;
                notifyAll();
            }

        lockCentral.unlock();
        System.out.println("Camion " + camion.getMatricula() + " ha dejado de compactar residuos");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
