package tictac;

import java.util.concurrent.Semaphore;

public class Gestor {
    Semaphore semaforoTic;
    Semaphore semaforoTac;

    public Gestor() {
        semaforoTic = new Semaphore(1);  // TIC empieza primero (1 permiso)
        semaforoTac = new Semaphore(0);  // TAC debe esperar (0 permisos)
    }

    public void imprimirTic(String tic) throws InterruptedException {
        semaforoTic.acquire();  // Espera su turno
        System.out.println(tic);
        semaforoTac.release();  // Da el turno a TAC
    }

    public void imprimirTac(String tac) throws InterruptedException {
        semaforoTac.acquire();  // Espera su turno
        System.out.println(tac);
        semaforoTic.release();  // Da el turno a TIC
    }
}
