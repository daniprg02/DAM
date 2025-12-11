package tictacProfesore;

import java.util.concurrent.Semaphore;

public class Tic extends Thread {
    private Semaphore ticPermiso;
    private Semaphore tacPermiso;

    public Tic(Semaphore ticPermiso, Semaphore tacPermiso) {
        this.ticPermiso = ticPermiso;
        this.tacPermiso = tacPermiso;
    }

    @Override
    public void run() {
        try {
            while (true) {
                ticPermiso.acquire(); // Espera su turno
                System.out.println("TIC");
                Thread.sleep(500);     // Espera medio segundo
                tacPermiso.release();  // Cede el turno a TAC
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}