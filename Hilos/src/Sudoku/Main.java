package Sudoku;

public class Main {
    public static void main(String[] args) {
        // 1. Crear un sudoku (con valores predeterminados que tienen errores)
        Sudoku sudoku = new Sudoku();
        
        // 2. Imprimir el sudoku con índices para verlo
        sudoku.imprimirSudokuConIndices();
        System.out.println();
        
        // 3. Crear el gestor que coordinará los 27 hilos
        GestorSudoku gestor = new GestorSudoku(sudoku);
        
        // 4. Llamar al método que hace todo:
        //    - Crea 27 hilos
        //    - Los inicia
        //    - Espera a que terminen (con join)
        //    - Muestra el resumen final
        gestor.verificarSudoku();
    }
}
