package EXAMENDEPS;

import java.util.ArrayList;
import java.util.List;

public class Ruleta extends Thread {
    List<Jugador> jugadores = new ArrayList<>();
    int numeroGanador;
    boolean casinoAbierto;


    public Ruleta(List<Jugador> jugadores) {
        this.jugadores = jugadores;
        this.numeroGanador = (int) (Math.random()*37+1);
        casinoAbierto = false;
    }

    public synchronized void esperarComienzo(Jugador jugador) throws InterruptedException {
        while(!casinoAbierto) {
            wait();
        }

        System.out.println("El casino ha abierto");
    }

    public void casinoAbierto(){
        if(casinoAbierto){
            notifyAll();
        }
    }

    public void setCasinoAbierto(boolean casinoAbierto) {
        this.casinoAbierto = casinoAbierto;
    }
//
//    @Override
//    public void run() {
//        setCasinoAbierto(true);
//        casinoAbierto();
//    }
}
