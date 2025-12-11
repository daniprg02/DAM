import java.io.*;
import java.util.HashMap;

public class examenDANIELPEREZ {
    public static void main(String[] args) {
        HashMap<String, Integer> pedidosPorCliente = new HashMap<>();
        int pedidosValidos = 0;
        int pedidosInvalidos = 0;
        int pedidosEnero = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("pedidos.txt"));
             BufferedWriter bw = new BufferedWriter(new FileWriter("SolDani.txt"))) {

            String linea;

            while ((linea = br.readLine()) != null) {

                linea = linea.trim();
                if (linea.isEmpty() || linea.startsWith("#")) {
                    continue;
                }

                String [] partes =  linea.split(";", -1);
                //Si es distinto de 6 valores, es incorrecto
                if(partes.length != 6){
                    pedidosInvalidos++;
                    continue;
                }

                String fecha = partes[1];
                String cliente = partes[2];
                String cantidadStr =  partes[4];
                String precioStr = partes[5];

                if(!fecha.matches("\\d{4}-\\d{2}-\\d{2}")){
                    pedidosInvalidos++;
                    continue;
                }

                int cantidad;
                double precio;

                try {
                    cantidad = Integer.parseInt(cantidadStr);
                    precio = Double.parseDouble(precioStr);
                } catch (Exception e) {
                    pedidosInvalidos++;
                    continue;
                }

                if(cantidad <= 0 || precio <= 0){
                    pedidosInvalidos++;
                    continue;
                }

                pedidosValidos++;

                pedidosPorCliente.put(cliente, pedidosPorCliente.getOrDefault(cliente, 0) + 1);

                //Se coge la fecha para que tome sólo el mes por si coincide con enero
                if(fecha.startsWith("01", 5)){
                    pedidosEnero++;
                }
            }
            bw.write("SOLUCIÓN - Estadísticas de pedidos");
            bw.newLine();
            bw.write("a) Número de pedidos válidos: " + pedidosValidos);
            bw.newLine();
            bw.write("b) Número de líneas inválidas: " + pedidosInvalidos);
            bw.newLine();
            bw.write("c) Pedidos por cliente:");
            bw.newLine();

            for(String cliente : pedidosPorCliente.keySet()){
                bw.write("- " +cliente + ": " + pedidosPorCliente.get(cliente));
                bw.newLine();
            }

            bw.write("d) Número de pedidos (válidos) en Enero: " + pedidosEnero);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
