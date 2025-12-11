package Pistola;
class Pistola {
    private int balas = 40;
  
    // Cuando la bala llega a cero llama a wait () y libera el bloqueo
    synchronized public void disparar(int balasAdisparar)
    {
        for (int i = 1; i <= balasAdisparar; i++) {
        	System.out.println("Bala "+ i + " disparada");
            if (balas == 0) {
                System.out.println(i - 1
                                   + " balas disparadas y "
                                   + balas + " restantes");
                System.out.println(
                    "Invocando al metodo wait...");
                try {
                    wait();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(
                    "Continuando el fuego despu�s de recargar");
            }
  
            balas--;
        }
        System.out.println(
            "Tiroteo finalizado");
    }
  
    // cargar() incrementa las balas 40 y llama a notify que despierta al 
    // hilo que se le mando dormir usando wait () dentro del metodo disparar
    
    
    synchronized public void recargar()
    {
        System.out.println(
            "Pistola recargada con otras 40 balas "
            );
        balas += 40;
        notify();
    }
}