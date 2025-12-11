package tictacProfesore;

import java.util.concurrent.Semaphore;

public class Tac extends Thread {
    private Semaphore ticPermiso;
    private Semaphore tacPermiso;

    public Tac(Semaphore ticPermiso, Semaphore tacPermiso) {
        this.ticPermiso = ticPermiso;
        this.tacPermiso = tacPermiso;
    }

    @Override
    public void run() {
        try {
            while (true) {
                tacPermiso.acquire();  // Espera su turno
                System.out.println("TAC");
                Thread.sleep(500);     // Espera medio segundo
                ticPermiso.release();  // Cede el turno a TIC
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}