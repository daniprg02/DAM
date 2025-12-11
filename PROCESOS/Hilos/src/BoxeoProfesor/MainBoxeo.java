package BoxeoProfesor;


public class MainBoxeo {

    public static void main(String[] args) {

        Ring ring = new Ring();

        // Creamos los boxeadores
        Boxeador b1 = new Boxeador("Rocky", ring);
        Boxeador b2 = new Boxeador("Apollo", ring);

        // Registramos los boxeadores en el ring
        ring.setBoxeadores(b1, b2);

        // Creamos el árbitro
        Arbitro arbitro = new Arbitro("Mills Lane", ring, b1, b2);
        ring.setArbitro(arbitro);

        // Iniciamos los hilos
        arbitro.start();
        b1.start();
        b2.start();
    }
}
