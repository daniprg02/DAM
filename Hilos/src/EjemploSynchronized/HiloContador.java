package EjemploSynchronized;
public class HiloContador extends Thread {
 
    private int id;
    private Counter counter;
    private int n;
 
    public HiloContador(int id, int n, Counter counter) {
        this.id = id;
        this.n = n;
        this.counter = counter;
    }
 
    public void run() {
        if (this.counter.isSincronizado()) {
            this.counter.mostrarNumerosSincronizado(this.id, this.n);
        } else {
            this.counter.mostrarNumerosNoSincronizado(this.id, this.n);
        }
    }
 
}