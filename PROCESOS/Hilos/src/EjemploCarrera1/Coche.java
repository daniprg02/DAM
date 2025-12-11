package EjemploCarrera1;

import java.util.Random;

public class Coche extends Thread {

    private Random random = new Random();
   
    private String nombre;

    public Coche(String nombre) {
        this.nombre = nombre;
        
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
        System.out.println("Ha ganado el  "+this.getNombre());
       
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