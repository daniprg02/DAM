package Sudoku;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GestorSudoku {
    // Atributo: el sudoku que vamos a verificar
    private Sudoku sudoku;
    
    // Map para guardar resultados: clave = "FILA 0", valor = true/false
    // ConcurrentHashMap permite que varios hilos escriban a la vez sin problemas
    private Map<String, Boolean> resultados;
    
    // Tiempo en el que empezó la verificación (para calcular tiempos transcurridos)
    private long tiempoInicio;

    // Constructor: recibe el sudoku y prepara el Map vacío
    public GestorSudoku(Sudoku sudoku) {
        this.sudoku = sudoku;
        this.resultados = new ConcurrentHashMap<>();
    }

    // Método principal: crea 27 hilos, los ejecuta y espera resultados
    public void verificarSudoku() {
        // Guardar el momento exacto en que empezamos (en milisegundos)
        tiempoInicio = System.currentTimeMillis();
        
        // Lista para guardar los 27 hilos que vamos a crear
        List<Verificador> verificadores = new ArrayList<>();
        
        // ===== CREAR 9 HILOS PARA VERIFICAR LAS 9 FILAS =====
        for (int i = 0; i < 9; i++) {
            // Crear hilo: (sudoku, tipo "FILA", índice 0-8, este gestor)
            verificadores.add(new Verificador(sudoku, "FILA", i, this));
        }
        
        // ===== CREAR 9 HILOS PARA VERIFICAR LAS 9 COLUMNAS =====
        for (int i = 0; i < 9; i++) {
            // Crear hilo: (sudoku, tipo "COLUMNA", índice 0-8, este gestor)
            verificadores.add(new Verificador(sudoku, "COLUMNA", i, this));
        }
        
        // ===== CREAR 9 HILOS PARA VERIFICAR LAS 9 REGIONES =====
        for (int i = 0; i < 9; i++) {
            // Crear hilo: (sudoku, tipo "REGION", índice 0-8, este gestor)
            verificadores.add(new Verificador(sudoku, "REGION", i, this));
        }
        
        // ===== INICIAR TODOS LOS HILOS =====
        // Cada hilo empieza a ejecutarse en paralelo
        for (Verificador v : verificadores) {
            v.start();  // Llama al método run() de cada hilo
        }
        
        // ===== ESPERAR A QUE TODOS LOS HILOS TERMINEN =====
        // join() bloquea el hilo principal hasta que el hilo termine
        for (Verificador v : verificadores) {
            try {
                v.join();  // Espera a que este hilo termine
            } catch (InterruptedException e) {
                System.err.println("Error esperando hilo: " + e.getMessage());
            }
        }
        
        // Cuando llegamos aquí, todos los hilos han terminado
        // Ahora mostramos el resumen final
        mostrarResultados();
    }

    // Método que llaman los hilos cuando terminan su verificación
    // "synchronized" hace que solo un hilo pueda ejecutar este método a la vez
    // (evita que los println se mezclen)
    public synchronized void registrarResultado(String tipo, int indice, boolean resultado) {
        // Calcular cuántos milisegundos han pasado desde que empezamos
        long tiempoTranscurrido = System.currentTimeMillis() - tiempoInicio;
        
        // Crear una clave única para guardar en el Map
        // Ejemplo: "FILA 0", "COLUMNA 5", "REGION 3"
        String clave = tipo + " " + indice;
        
        // Guardar el resultado en el Map
        resultados.put(clave, resultado);
        
        // ===== FORMATEAR EL MENSAJE SEGÚN EL TIPO =====
        String mensaje;
        
        if (tipo.equals("REGION")) {
            // Para regiones, convertir índice a coordenadas (fila, columna)
            // Ejemplo: región 5 → (1, 2)
            int filaRegion = indice / 3;      // 5 / 3 = 1
            int columnaRegion = indice % 3;   // 5 % 3 = 2
            
            // Crear mensaje: [000123 ms] | Hilo Verificador REGION (1,2) | Resultado: VÁLIDO
            mensaje = String.format("[%06d ms] | Hilo Verificador REGION (%d,%d) | Resultado: %s",
                    tiempoTranscurrido, filaRegion, columnaRegion, 
                    resultado ? "VÁLIDO" : "INVÁLIDO");
        } else {
            // Para filas y columnas: [000120 ms] | Hilo Verificador FILA 0 | Resultado: VÁLIDO
            mensaje = String.format("[%06d ms] | Hilo Verificador %s %d | Resultado: %s",
                    tiempoTranscurrido, tipo, indice, 
                    resultado ? "VÁLIDO" : "INVÁLIDO");
        }
        
        // Imprimir el mensaje (sincronizado para que no se mezcle con otros hilos)
        System.out.println(mensaje);
    }

    // Método privado: mostrar resumen final después de que todos terminen
    private void mostrarResultados() {
        // Línea separadora bonita
        System.out.println("--------------------------------------------------------------");
        
        // Lista para guardar los errores encontrados
        List<String> errores = new ArrayList<>();
        
        // ===== RECORRER TODO EL MAP BUSCANDO ERRORES (false) =====
        for (Map.Entry<String, Boolean> entry : resultados.entrySet()) {
            // Si el resultado es false, hay un error
            if (!entry.getValue()) {
                // Extraer información de la clave
                String clave = entry.getKey();  // Ejemplo: "REGION 5"
                String[] partes = clave.split(" ");  // ["REGION", "5"]
                String tipo = partes[0];             // "REGION"
                int indice = Integer.parseInt(partes[1]);  // 5
                
                // Formatear el error según el tipo
                if (tipo.equals("REGION")) {
                    // Convertir índice a coordenadas
                    int filaRegion = indice / 3;
                    int columnaRegion = indice % 3;
                    errores.add(String.format("región (%d,%d)", filaRegion, columnaRegion));
                } else {
                    // Para filas y columnas: "fila 0", "columna 3"
                    errores.add(tipo.toLowerCase() + " " + indice);
                }
            }
        }
        
        // ===== MOSTRAR EL RESULTADO FINAL =====
        if (errores.isEmpty()) {
            // No hay errores: sudoku válido
            System.out.println("✅ Sudoku válido");
        } else {
            // Hay errores: mostrar el primero
            System.out.println("❌ Sudoku inválido: error en " + errores.get(0));
            
            // Si hay más de un error, mostrar los demás también
            if (errores.size() > 1) {
                System.out.println("   Otros errores encontrados: " + 
                        String.join(", ", errores.subList(1, errores.size())));
            }
        }
    }
}
