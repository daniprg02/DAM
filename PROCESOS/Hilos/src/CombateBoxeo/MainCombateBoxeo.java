package CombateBoxeo;

public class MainCombateBoxeo {
    public static void main(String[]args) {

        Boxeador rocky = new Boxeador("Rocky");
        Boxeador apollo = new Boxeador("Apollo");
        Arbitro arbitro = new Arbitro("Arbitro");
        Ring ring = new Ring(rocky, apollo, arbitro);
        arbitro.setRing(ring);
        rocky.setRing(ring);
        apollo.setRing(ring);
        rocky.setOponente(apollo);
        apollo.setOponente(rocky);

        arbitro.start();
        rocky.start();
        apollo.start();

    }

}
