package CarreraSynchro;

import java.util.ArrayList;
import java.util.Random;

public class Coche extends Thread {

    private Random random = new Random();

    private String nombre;
    private static ArrayList<String> clasificacion = new ArrayList<>();


    public Coche(String nombre) {
        this.nombre = nombre;

    }

    public static ArrayList<String> getClasificacion() {
        return clasificacion;
    }

    public static void setClasificacion(ArrayList<String> clasificacion) {
        Coche.clasificacion = clasificacion;
    }

    public synchronized void  aadirClasificacion(String nombre) {
        System.out.println("Ha ganado el  "+nombre);
        System.out.println("Enhorabuena "+nombre);
        clasificacion.add(nombre);
    }
    public static void verClasificacion() {

        for(String nombre : clasificacion) {
            System.out.println(nombre);
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            try {
                sleep(random.nextInt(1) * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        aadirClasificacion(this.getNombre());

    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Nombre del coche = " + nombre;
    }
}
