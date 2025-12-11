package tictacProfesore;

import java.util.concurrent.Semaphore;

public class Main {

    public static void main(String[] args) {

        // Creamos dos semáforos:
        // - ticPermiso: empieza con 1 para que TIC se ejecute primero.
        // - tacPermiso: empieza con 0 para que TAC espere.
        Semaphore ticPermiso = new Semaphore(1);
        Semaphore tacPermiso = new Semaphore(0);

        // Creamos los hilos
        Tic tic = new Tic(ticPermiso, tacPermiso);
        Tac tac = new Tac(ticPermiso, tacPermiso);

        // Iniciamos los hilos
        tic.start();
        tac.start();
    }
}