package SudokuSimple;


public class Region {
    private int[][] region;

    public Region() {
        region = new int[][] {
                {5, 3, 4},
                {6, 3, 2},
                {1, 9, 8}
        };
    }

    public int[][] getRegion() {
        return region;
    }

    public void imprimirRegion() {
        System.out.println("Región 3x3:");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(region[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}