package EjercicioCarniceriaSemaforo;
public class Cliente extends Thread {
    private Carniceria carniceria;
    private String nombre;
    private int prioridad;

    public Cliente(Carniceria carniceria, String nombre, int prioridad) {
        this.carniceria = carniceria;
        this.nombre = nombre;
        this.prioridad = prioridad;
    }

    public void run() {
    	Thread.currentThread().setPriority(prioridad); // Establecer la prioridad del hilo
        System.out.println("Cliente " + nombre + " ha llegado a la carnicer�a");
        carniceria.atenderCliente(nombre);
    }
}
