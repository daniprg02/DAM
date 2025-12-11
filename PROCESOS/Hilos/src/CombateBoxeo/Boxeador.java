package CombateBoxeo;

public class Boxeador extends Thread{
    String nombre;
    Ring ring;
    int salud;
    Boxeador oponente;


    public Boxeador(String nombre) {
        this.nombre = nombre;
        this.salud = 100;
    }

    public void setRing(Ring ring) {
        this.ring = ring;
    }

    public String getNombre() {
        return nombre;
    }

    public int getSalud() {
        return salud;
    }

    public void setSalud(int salud) {
        this.salud = salud;
    }

    public void setOponente(Boxeador oponente) {
        this.oponente = oponente;
    }

    @Override
    public void run(){

        try {
            ring.comenzarCombate(this, oponente);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}
