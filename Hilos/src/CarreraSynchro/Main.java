package CarreraSynchro;

public class Main {

        public static void main(String[] args) {

            Coche coche1 = new Coche("coche1");
            Coche coche2 = new Coche("coche2");
            Coche coche3 = new Coche("coche3");
            Coche coche4 = new Coche("coche4");
            Coche coche5 = new Coche("coche5");
            coche1.start();
            coche2.start();
            coche3.start();
            coche4.start();
            coche5.start();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }

            Coche.verClasificacion();

        }


}
