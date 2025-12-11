package Abadia;

public class Monje extends Thread{
    String nombre;
    Mesa mesa;
    int posicion;

    public Monje(String nombre, Mesa mesa, int posicion) {
        this.nombre = nombre;
        this.mesa = mesa;
        this.posicion = posicion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void comer(){
        try {
            System.out.println("El monje " + this.nombre + " está comiendo");
            Thread.sleep(200);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
