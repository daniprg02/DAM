package SudokuSimple;


public class VerificadorCelda extends Thread {
    private Region region;
    private int fila;
    private int columna;
    private int numeroEncontrado;
    private GestorRegion gestor;

    public VerificadorCelda(Region region, int fila, int columna, GestorRegion gestor) {
        this.region = region;
        this.fila = fila;
        this.columna = columna;
        this.gestor = gestor;
    }

    @Override
    public void run() {
        numeroEncontrado = region.getRegion()[fila][columna];
        gestor.registrarNumero(fila, columna, numeroEncontrado);
    }
}
