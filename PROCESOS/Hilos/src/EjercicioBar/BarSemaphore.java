package EjercicioBar;

import java.util.concurrent.Semaphore;
import java.util.Random;


public class BarSemaphore {

    public static void main(String[] args) {
        int camareros = 3;
        int clientes = 40;
        Random random = new Random();

        // Semáforo con 3 permisos (uno por camarero)
        Semaphore semaforo = new Semaphore(camareros);

        System.out.println("🍺 El bar abre sus puertas. Aforo: " + clientes + " personas. Camareros: " + camareros);

        for (int i = 1; i <= clientes; i++) {
            int clienteId = i;

            // Crear un hilo para cada cliente
            Thread cliente = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("Cliente " + clienteId + " entra al bar y espera camarero...");
                        semaforo.acquire(); // Espera si no hay camarero libre

                        System.out.println("Cliente " + clienteId + " está siendo atendido por " + Thread.currentThread().getName());
                        Thread.sleep(1000 + random.nextInt(3000)); // Atiende 1–4 segundos
                        System.out.println("Cliente " + clienteId + " ha terminado y se va del bar.");

                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        semaforo.release(); // Libera al camarero
                    }
                }
            }, "Camarero-" + ((i % camareros) + 1));

            cliente.start();
        }
    }
}
