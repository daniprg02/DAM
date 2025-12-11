package EjercicioMonjes;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Monje extends Thread {
    
    private Mesa mesa;
    private int comensal;
    private int indiceComensal;
    
    public Monje(Mesa m, int comensal){
        this.mesa = m;
        this.comensal = comensal;
        this.indiceComensal = comensal - 1;
    }
    
    public void run(){
        
        while(true){
            this.rezando();
            this.mesa.cogerTenedores(this.indiceComensal);
            this.comiendo();
            System.out.println("Monje " + comensal +  " deja de comer, tenedores libres " + (this.mesa.tenedorIzquierda(this.indiceComensal) + 1) + ", " + (this.mesa.tenedorDerecha(this.indiceComensal) + 1) );
            this.mesa.dejarTenedores(this.indiceComensal);
        }
        
    }
    
    public void rezando(){
       
        System.out.println("Monje " + comensal + " esta rezando");
        try {
            sleep((long) (Math.random() * 4000));
        } catch (InterruptedException ex) { }
        
    }
    
    public void comiendo(){
        System.out.println("Monje " + comensal + " esta comiendo");
        try {
            sleep((long) (Math.random() * 4000));
        } catch (InterruptedException ex) { }
    }
    
}