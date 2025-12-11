package Sudoku;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Sudoku {
    int [][] sudoku;

    public Sudoku() {
        //sudoku = new int[][];

        sudoku = new int[][] {
                {5,7,4,6,7,8,9,1,2},
                {6,7,7,1,9,5,3,4,8},
                {1,9,8,3,4,2,5,6,7},
                {8,5,9,7,6,1,4,2,3},
                {4,2,6,8,5,3,7,9,1},
                {7,1,3,9,2,4,8,5,6},
                {9,6,1,5,3,7,2,8,4},
                {2,8,7,4,1,9,6,3,5},
                {3,4,5,2,8,6,1,7,9}
        };

//
//        for (int i = 0; i < 9; i++) {
//            for (int j = 0; j < 9; j++) {
//                //Con math random
//                //sudoku[i][j] = 1 + (int)(Math.random() * 9);
//                sudoku[i][j] = ThreadLocalRandom.current().nextInt(1, 10); // 1 inclusive, 10 exclusive
//
//            }
//        }
    }

    public int[][] getSudoku() {
        return sudoku;
    }

    public void setSudoku(int[][] sudoku) {
        this.sudoku = sudoku;
    }

    public void imprimirSudoku() {
        for (int i = 0; i < 9; i++) {
            //FILAS
            if (i % 3 == 0 && i != 0) {
                System.out.println("------+-------+------");
            }
            //COLUMNAS
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0 && j != 0) {
                    System.out.print("| ");
                }
                System.out.print(sudoku[i][j] + " ");
            }
            System.out.println();
        }
    }


    public void imprimirSudokuConIndices() {
        System.out.println("   C0 C1 C2 C3 C4 C5 C6 C7 C8");
        System.out.println("   +------ +------ +------ +");

        for (int i = 0; i < 9; i++) {
            System.out.print("F" + i + " | "); // etiqueta de fila

            for (int j = 0; j < 9; j++) {
                System.out.print(sudoku[i][j] + " ");

                // separador vertical cada 3 columnas
                if ((j + 1) % 3 == 0) {
                    System.out.print("| ");
                }
            }
            System.out.println(); // salto de línea

            // separador horizontal cada 3 filas
            if ((i + 1) % 3 == 0) {
                System.out.println("   +------ +------ +------ +");
            }
        }
    }


    @Override
    public String toString() {
        return "Sudoku{" +
                "sudoku=" + Arrays.deepToString(sudoku) +
                '}';
    }
}
