package SudokuSimple;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class GestorRegion {
    private Region region;
    private Set<Integer> numerosEncontrados;
    private long tiempoInicio;

    public GestorRegion(Region region) {
        this.region = region;
        this.numerosEncontrados = ConcurrentHashMap.newKeySet();
    }

    public void verificarRegion() {
        tiempoInicio = System.currentTimeMillis();

        List<VerificadorCelda> hilos = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                hilos.add(new VerificadorCelda(region, i, j, this));
            }
        }

        for (VerificadorCelda hilo : hilos) {
            hilo.start();
        }

        for (VerificadorCelda hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }

        mostrarResultado();
    }

    public synchronized void registrarNumero(int fila, int columna, int numero) {
        long tiempo = System.currentTimeMillis() - tiempoInicio;

        boolean agregado = numerosEncontrados.add(numero);

        String estado = agregado ? "✓ OK" : "✗ DUPLICADO";

        System.out.printf("[%06d ms] | Celda [%d,%d] = %d | %s%n",
                tiempo, fila, columna, numero, estado);
    }

    private void mostrarResultado() {
        System.out.println("--------------------------------------------------------------");

        if (numerosEncontrados.size() == 9) {
            System.out.println("✅ Región válida: 9 números distintos (1-9)");
        } else {
            System.out.println("❌ Región inválida: solo " + numerosEncontrados.size() +
                    " números distintos (hay duplicados)");
            System.out.println("   Números encontrados: " + numerosEncontrados);
        }
    }
}
