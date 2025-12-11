package tictac;

public class MainTicTac {
    public static void main(String[]args) {

        Gestor gestor = new Gestor();
        Tic t1 = new Tic(gestor);
        Tac t2 = new Tac(gestor);

        t1.start();
        t2.start();

    }
}
