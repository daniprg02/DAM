package Sudoku;

import java.util.HashSet;

// Esta clase representa un hilo que verifica una fila, columna o región
public class Verificador extends Thread {
    // Atributos
    Sudoku sudoku;           // El sudoku que vamos a verificar
    String tipo;             // "FILA", "COLUMNA" o "REGION"
    int indice;              // Qué fila/columna/región verificar (0-8)
    boolean resultado;       // true si es válido, false si hay error
    GestorSudoku gestor;     // Referencia al gestor para registrar resultados

    // Constructor: recibe toda la información necesaria
    public Verificador(Sudoku sudoku, String tipo, int indice, GestorSudoku gestor) {
        this.sudoku = sudoku;
        this.tipo = tipo;
        this.indice = indice;
        this.gestor = gestor;
        this.resultado = false;  // Por defecto: no válido
    }

    // Verificar una FILA específica (la fila this.indice)
    public void verificarFila(HashSet<Integer> numeros) {
        // Limpiar el HashSet por si tuviera algo
        numeros.clear();
        
        // Obtener la matriz del sudoku
        int[][] aux = this.sudoku.getSudoku();

        // Recorrer las 9 columnas de la fila this.indice
        for (int columna = 0; columna < 9; columna++) {
            // Agregar cada número al HashSet
            // Si hay duplicados, el HashSet no los guarda dos veces
            numeros.add(aux[this.indice][columna]);
        }

        // Si el HashSet tiene 9 elementos → todos distintos → VÁLIDO
        // Si tiene menos de 9 → hay duplicados → INVÁLIDO
        this.resultado = (numeros.size() == 9);
    }

    // Verificar una COLUMNA específica (la columna this.indice)
    private void verificarColumna(HashSet<Integer> numeros) {
        // Limpiar el HashSet
        numeros.clear();
        
        // Obtener la matriz del sudoku
        int[][] aux = this.sudoku.getSudoku();

        // Recorrer las 9 filas de la columna this.indice
        for (int fila = 0; fila < 9; fila++) {
            // Agregar cada número al HashSet
            numeros.add(aux[fila][this.indice]);
        }
        
        // Verificar si todos son distintos (tamaño = 9)
        this.resultado = (numeros.size() == 9);
    }

    // Verificar una REGIÓN específica (la región this.indice)
    private void verificarRegion(HashSet<Integer> numeros) {
        // Limpiar el HashSet
        numeros.clear();
        
        // Obtener la matriz del sudoku
        int[][] aux = this.sudoku.getSudoku();

        // Calcular dónde empieza la región (esquina superior izquierda)
        // Ejemplo: región 5 → fila inicial = 3, columna inicial = 3
        int filaInicial = (this.indice / 3) * 3;
        int columnaInicial = (this.indice % 3) * 3;

        // Recorrer 3 filas y 3 columnas desde la posición inicial
        for (int fila = filaInicial; fila < filaInicial + 3; fila++) {
            for (int columna = columnaInicial; columna < columnaInicial + 3; columna++) {
                // Agregar cada número de la región al HashSet
                numeros.add(aux[fila][columna]);
            }
        }

        // Verificar si todos son distintos (tamaño = 9)
        this.resultado = (numeros.size() == 9);
    }

    // Método run(): lo que ejecuta el hilo cuando hacemos .start()
    @Override
    public void run() {
        // Crear un HashSet vacío para guardar los números
        HashSet<Integer> numeros = new HashSet<>();

        // Según el tipo, llamar al método correspondiente
        switch (tipo) {
            case "FILA":
                verificarFila(numeros);
                break;
            case "COLUMNA":
                verificarColumna(numeros);
                break;
            case "REGION":
                verificarRegion(numeros);
                break;
        }
        
        // Cuando terminamos la verificación, registrar el resultado en el gestor
        // El gestor imprimirá el mensaje y guardará el resultado
        gestor.registrarResultado(tipo, indice, resultado);
    }
}