package BoxeoProfesor;

public class Arbitro extends Thread {
    private String nombre;
    private Ring ring;
    private Boxeador b1, b2;

    public Arbitro(String nombre, Ring ring, Boxeador b1, Boxeador b2) {
        this.nombre = nombre;
        this.ring = ring;
        this.b1 = b1;
        this.b2 = b2;
    }

    @Override
    public void run() {
        System.out.println("🧑‍⚖️ Árbitro " + nombre + ": Preparados los boxeadores...");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ring.comenzarPelea(); // da la señal de inicio
        ring.esperarFinal();  // espera hasta que el combate termine

        System.out.println("\n🧑‍⚖️ Árbitro " + nombre + ": El combate ha concluido. ¡Enhorabuena al ganador!");
    }
}
