package ExamenAD;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Ej04 {
    public static void main(String[] args) {

        ArrayList<String> a = new ArrayList<>();
        HashMap<String, Integer> productoMasVendido = new HashMap<>();
        int numPedidos = 0;
        int numPedidosTotal = 0;
        double totalEntregados = 0;
        double importeMedio = 0;
        String producto = "";
        double total = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("registroVentas.txt"))) {

            String linea = "";

            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("SUCURSAL: ")) {
                    if (numPedidos != 0) {
                        a.add(String.valueOf(numPedidos));
                        numPedidos = 0;
                    }

                    a.add(linea.substring(10));
                } else {
                    if (!linea.isEmpty() && !linea.startsWith("SUCURSAL: ")) {
                        numPedidos++;
                        numPedidosTotal++;

                        String[] datos = linea.split(";");

                        if (linea.endsWith("Entregado")) {

                            double totalCalculado = Double.parseDouble(datos[3]) * Double.parseDouble(datos[2]);
                            totalEntregados += totalCalculado;
                        }

                        int cantidad = Integer.parseInt(datos[2]);
                        double precio = Double.parseDouble(datos[3]);

                        importeMedio += cantidad * precio;
                        //Si el producto no existe en el HashMap lo introduce con su valor de cantidad
                        if (!productoMasVendido.containsKey(datos[1])) {
                            productoMasVendido.put(datos[1], Integer.parseInt(datos[2]));
                        } else {
                            //Si ya existe, generamos un nuevo valor con el antiguo más el nuevo y se lo introducimos
                            int nuevoValor = productoMasVendido.get(datos[1]) + Integer.parseInt(datos[2]);
                            productoMasVendido.put(datos[1], nuevoValor);
                            producto = datos[1] + "(" + nuevoValor + " unidades) ";
                        }
                    }
                }
            }
            if (numPedidos != 0) {
                a.add(String.valueOf(numPedidos));
            }

            System.out.println("Total de euros de pedidos entregados: " + totalEntregados + " €");

            for (int i = 0; i < a.size(); i += 2) {
                System.out.println(a.get(i) + " -> " + a.get(i + 1));
            }

            System.out.println("==== PRODUCTOS MÁS VENDIDOS  ====");

            for (String productos : productoMasVendido.keySet()) {
                System.out.println(productos + " -> " + productoMasVendido.get(productos));
            }
            System.out.println("Numero total de pedidos: " + numPedidosTotal);
            total = importeMedio / numPedidosTotal;
            System.out.println("El precio medio es " + String.format("%.2f €", total));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File("informeVentasFinal.txt")))) {

            bw.write("a) ");
            bw.newLine();
            for (int i = 0; i < a.size(); i+= 2) {
                bw.write(a.get(i) + " -> " + a.get(i + 1));
                bw.newLine();
            }
            bw.newLine();
            bw.write("b) ");
            bw.newLine();
            bw.write("Total global entregado = " + totalEntregados + " €");
            bw.newLine();
            bw.write("c) ");
            bw.newLine();
            bw.write("Producto más vendido: "+ producto);
            bw.newLine();
            bw.write("d) ");
            bw.newLine();
            bw.write("Importe medio por pedido = " + String.format("%.2f €",total ));

        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }
}



