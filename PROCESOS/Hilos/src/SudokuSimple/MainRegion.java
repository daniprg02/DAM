package SudokuSimple;

public class MainRegion {
    public static void main(String[] args) {
        Region region = new Region();
        region.imprimirRegion();

        GestorRegion gestor = new GestorRegion(region);
        gestor.verificarRegion();
    }
}