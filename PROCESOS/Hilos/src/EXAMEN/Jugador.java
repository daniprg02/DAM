package EXAMEN;

public class Jugador extends Thread{
    int apuesta;
    int numero;
    int dinero;
    Banca banca;
    int numeroJugador;
    String nombre;

    public Jugador(Banca banca, String nombre) {
        this.dinero = 300;
        this.banca = banca;
        this.apuesta = 10;

    }

    public Jugador(int apuesta, int numero, int dinero) {
        this.apuesta = apuesta;
        this.numero = numero;
        this.dinero = dinero;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDinero() {
        return dinero;
    }

    public void setDinero(int dinero) {
        this.dinero = dinero;
    }

    public int numeroApuesta() {

        this.numeroJugador = (int) (Math.random()*37+1);

        return numeroJugador;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "apuesta=" + apuesta +
                ", numero=" + numero +
                ", dinero=" + dinero +
                ", banca=" + banca +
                ", numeroJugador=" + numeroJugador +
                '}';
    }

    @Override
    public void run() {
        try {
            banca.apostar(this);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
