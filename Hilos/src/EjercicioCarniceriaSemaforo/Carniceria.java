package EjercicioCarniceriaSemaforo;
import java.util.concurrent.Semaphore;

public class Carniceria {
    private Semaphore semaphore;
    private int contadorLlegada;

    public Carniceria() {
        semaphore = new Semaphore(1); // M�ximo 1 clientes atendidos en la carnicer�a
        contadorLlegada = 1;
    }

    public void atenderCliente(String cliente) {
        try {
            semaphore.acquire(); // El cliente adquiere el sem�foro y ocupa un lugar en la carnicer�a

            System.out.println("Cliente " + cliente + " est� siendo atendido");
            Thread.sleep(2000); // Simulaci�n de tiempo de atenci�n
            System.out.println("Cliente " + cliente + " ha sido atendido");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release(); // El cliente libera el sem�foro y deja un lugar disponible en la carnicer�a
            System.out.println("Cliente " + cliente + " abandona la tienda");
        }
    }

    
}
