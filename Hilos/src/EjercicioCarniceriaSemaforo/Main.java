package EjercicioCarniceriaSemaforo;
public class Main {
    public static void main(String[] args) {
        Carniceria carniceria = new Carniceria();
        int nombre = 1;
/*
        Thread[] clientes = new Thread[8];
        for (int i = 1; i <= 8; i++) {
            clientes[i-1] = new Thread(new Cliente(carniceria, "Cliente " + i));
            clientes[i-1].start();
        }
        */
        for(int i = 8; i>0;i--) {
        	
        	Cliente c = new Cliente(carniceria, "Cliente " + nombre,i);
        	c.start();
        	nombre++;
        }
        
    }
}