package tictac;

public class Tac extends Thread{
    Gestor g;
    String funcion;

    public Tac(Gestor g) {
        this.g = g;
        this.funcion = "tac";
    }

    @Override
    public void run(){
        while(true) {
            try {
                g.imprimirTac(this.funcion);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
