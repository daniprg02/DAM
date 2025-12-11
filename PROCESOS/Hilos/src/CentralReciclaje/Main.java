package CentralReciclaje;

public class Main {
    public static void main(String[] args) {
        Central homero = new Central("Homero");
        Camion c1 = new Camion("892920E", homero);
        Thread t1 = new Thread(c1);
        Camion c2 = new Camion("009221Z", homero);
        Thread t2 = new Thread(c2);
        Camion c3 = new Camion("9918311U", homero);
        Thread t3 = new Thread(c3);

        t1.start();
        t2.start();
        t3.start();
    }
}
