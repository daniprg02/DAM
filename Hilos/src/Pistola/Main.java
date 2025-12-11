package Pistola;
public class Main extends Thread {
    public static void main(String[] args)
    {
  
        Pistola p1 = new Pistola();
  
        // Creando un nuevo hilo y llamando al metodo disparar en el
        
        new Thread() {
            @Override public void run() { p1.disparar(60); }
        }.start();
  
        // Creando un nuevo hilo y llamando al metodo recargar en el
        new Thread() {
            @Override public void run() { p1.recargar(); }
        }.start();
    }
}