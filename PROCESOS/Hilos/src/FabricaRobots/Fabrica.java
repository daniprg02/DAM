package FabricaRobots;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Fabrica {
    Semaphore brazoMecanico;
    String nombreFabrica;
    private final long inicio = System.currentTimeMillis();
    public Lock zonaEmpaquetado = new ReentrantLock();
    private boolean empaquetando;


    public Fabrica(String nombreFabrica) {
        this.nombreFabrica = nombreFabrica;
        this.brazoMecanico = new Semaphore(3);
        this.empaquetando = false;
    }



    private void imprimirEstado(Robot robot, String accion) {
        synchronized (System.out) {
            //%06d → imprime con ceros a la izquierda (000123)
            //
            //%-2d → deja ancho fijo (alineado)
            //
            //%-10s → ancho fijo para texto (por ejemplo, “ENSAMBLA”, “SUELTA”)
            //
            //%n → salto de línea limpio según el sistema operativo
            long tiempo = System.currentTimeMillis() - inicio;


            switch (accion) {
                case "ENSAMBLAR", "DESAMBLAR" ->
                    System.out.printf("[%06d ms] | Robot %-2d | %-12s | Brazos libres: %d%n",
                            tiempo, robot.getId(), accion, brazoMecanico.availablePermits());

                case "EMPAQUETA", "DESEMPAQUETA" -> System.out.printf("[%06d ms] | Robot %-2d | %-12s%n",
                        tiempo, robot.getId(), accion);

                default -> {
                    System.out.printf("[%06d ms] | Robot %-2d | %-12s%n",
                            tiempo, robot.getId(), accion);
                }
            }
        }
    }

    public void empaquetar(Robot robot) throws InterruptedException {
        zonaEmpaquetado.lock();  // nadie más puede empaquetar
        try {
            synchronized (this) {  // bloquea comunicación con los pintores
                empaquetando = true;
                imprimirEstado(robot, "EMPAQUETA");
            }

            Thread.sleep(ThreadLocalRandom.current().nextInt(500, 1500));

            synchronized (this) {
                empaquetando = false;
                imprimirEstado(robot, "DESEMPAQUETA");
                notifyAll(); // avisa a los que estaban esperando pintar
            }

        } finally {
            zonaEmpaquetado.unlock(); // libera el lock
        }
    }



    public synchronized void pintar(Robot robot) throws InterruptedException {

        while(empaquetando){
            System.out.println(robot.getId() + " espera a pintar porque alguien está empaquetando");
            wait();

        }
        imprimirEstado(robot, "PINTAR");
    }


    public void ensamblar(Robot robot) throws InterruptedException {

        brazoMecanico.acquire();
        imprimirEstado(robot, "ENSAMBLAR");

        try {
            int tiempoAleatorio = ThreadLocalRandom.current().nextInt(0, 2000);
            //trabajar
            Thread.sleep(tiempoAleatorio);

        } finally {
            brazoMecanico.release();
            imprimirEstado(robot, "DESAMBLAR");
        }

    }




}
