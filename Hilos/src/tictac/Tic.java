package tictac;

public class Tic extends Thread{
    Gestor g;
    String funcion;

    public Tic(Gestor g) {
        this.g = g;
        this.funcion = "tic";
    }

    @Override
    public void run(){
        while(true) {
            try {
                g.imprimirTic(this.funcion);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
