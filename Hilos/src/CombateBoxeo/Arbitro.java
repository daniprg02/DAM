package CombateBoxeo;

public class Arbitro extends Thread{
    String nombre;
    Ring ring;

    public Arbitro(String nombre) {
        this.nombre = nombre;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Ring getRing() {
        return ring;
    }

    public void setRing(Ring ring) {
        this.ring = ring;
    }

    @Override
    public void run() {
        ring.arbitroInicia();
        try {
            ring.arbitroFinaliza();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}
