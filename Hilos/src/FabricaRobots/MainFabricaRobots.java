package FabricaRobots;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainFabricaRobots {
    public static void main(String[] args) throws InterruptedException {

        Fabrica Robocity = new Fabrica("Robocity");

        ExecutorService generandoRobots = Executors.newFixedThreadPool(6);
        int cont = 6;

        //enviamos robots al pool
        for (int i = 0; i < cont; i++) {
            int idAleatorio = (int) (Math.random() * 1000) + 1;
            generandoRobots.execute(new Robot(idAleatorio, Robocity));

        }
        //Cerramos el pool
        generandoRobots.shutdown();

        //esperamos a que todos terminen
        if (generandoRobots.awaitTermination(10, TimeUnit.SECONDS)) {
            System.out.println("Lote completado");
        } else {
            System.out.println("Tiempo agotado, cerramos fábrica");
        }
    }
}


//        Robot robot1 = new Robot(2289, Robocity);
//        Robot robot2 = new Robot(1287, Robocity);
//        Robot robot3 = new Robot(9392, Robocity);
//        Robot robot4 = new Robot(1100, Robocity);
//        Robot robot5 = new Robot(9991, Robocity);
//        Robot robot6 = new Robot(2939, Robocity);
//
//        robot1.start();
//        robot2.start();
//        robot3.start();
//        robot4.start();
//        robot5.start();
//        robot6.start();


/*
        Fabrica fabrica = new Fabrica("MiFabrica");

        Robot r1 = new Robot(1, fabrica);
        Robot r2 = new Robot(2, fabrica);

// Robot 1 empieza a empaquetar y tarda mucho
        new Thread(() -> {
            try { fabrica.empaquetar(r1); } catch (InterruptedException e) {}
        }).start();

// Pequeño retraso para garantizar que r1 entre primero
        Thread.sleep(50);

// Robot 2 intenta pintar mientras r1 aún empaqueta
        new Thread(() -> {
            try { fabrica.pintar(r2); } catch (InterruptedException e) {}
        }).start();


    }
}
*/