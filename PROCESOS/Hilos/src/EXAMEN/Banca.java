package EXAMEN;

import java.util.ArrayList;

public class Banca {
    boolean abrePuertas;
    int dinero;
    Ruleta ruleta;
    ArrayList<Jugador> jugadores;
    Jugador jugador;

    public Banca(ArrayList<Jugador> jugadores) {
        this.abrePuertas = true;
        this.dinero = 3000;
        this.jugadores = new ArrayList<>();
    }

    public void abrirPuertas(){
        this.abrePuertas = true;
        notifyAll();
    }


    public int getDinero() {
        return dinero;
    }

    public void setDinero(int dinero) {
        this.dinero = dinero;
    }

    public void apostar(Jugador jugador) throws InterruptedException {
        while(!abrePuertas){
            System.out.println("Esperando a jugar");
            wait();
        }

        while(abrePuertas) {

            if (jugador.getDinero() < 10) {
                System.out.println("No tienes dinero suficiente");
                break;
            }

            System.out.println("Empezamos a jugar");
            int numeroActual = 0;
            int numeroApuestaJugador = 0;
            numeroActual = ruleta.girarRuleta();
            numeroApuestaJugador = jugador.numeroApuesta();



            if (numeroApuestaJugador == numeroActual) {
                jugador.setDinero(jugador.getDinero() + 360);
                System.out.println (" El jugador " + jugador.getNombre() + " ha apostado" + numeroApuestaJugador + " ganó. Nuevo saldo: " + jugador.getDinero());
                this.setDinero(this.getDinero() - 360);
            } else {
                jugador.setDinero(jugador.getDinero() - 10);
                System.out.println (" El jugador " + jugador.getNombre() + " ha apostado" + numeroApuestaJugador + " perdió. Nuevo saldo: " + jugador.getDinero());
                this.setDinero(this.getDinero() + 10);
            }

            abrePuertas = false;

            System.out.println("ha salido el número " + numeroActual);
            System.out.println("Saldo de la banca: " + this.getDinero());



        }
    }

    @Override
    public String toString() {
        return "Banca{" +
                "abrePuertas=" + abrePuertas +
                ", dinero=" + dinero +
                ", ruleta=" + ruleta +
                ", jugadores=" + jugadores +
                '}';
    }
}
