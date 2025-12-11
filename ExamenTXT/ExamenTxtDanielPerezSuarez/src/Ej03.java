import java.io.*;
import java.util.Scanner;

public class Ej03 {
    public static void main(String[] args) {

        File fichero = new File("datosMixtos.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {

            String linea;
            int sumaCantidad = 0;
            Scanner sc = new Scanner(System.in);
            System.out.println("Hola, escribe un número de linea y te la imprimo por pantalla");
            int num = sc.nextInt();
            int contador = 1;
            int vecesLetra = 0;
            char [] letras;

            while ((linea = br.readLine()) != null) {

                linea = linea.trim(); // quitar espacios al principio y final

                // Saltar líneas vacías o comentarios
                if (linea.isEmpty() || linea.startsWith("#")) {
                    continue; // pasa a la siguiente línea
                }
                if(contador == num){
                    System.out.println("Esta es la linea que has pedido -- " + linea);
                }
                contador++;

                // Separar por ;
                String[] palabras = linea.split(";");

                // Si la línea no tiene suficientes columnas → saltar
                if (palabras.length < 5) {
                    continue;
                }

                String codigo = palabras[0];
                String nombre = palabras[1];
                String cantidad = palabras[2];
                String precio = palabras[3];
                String ciudad = palabras[4];

                for (char c : linea.toCharArray()) {
                    if (c == 'a' || c == 'A') {
                        vecesLetra++;
                    }
                }

                try(BufferedWriter bw = new BufferedWriter(new FileWriter("informeVentas.txt", true))) {

                    if (ciudad.equalsIgnoreCase("Madrid")) {
                        int cantidadInt = Integer.parseInt(cantidad);
                        float precioFloat = Float.parseFloat(precio);

                        int total = (int) (cantidadInt * precioFloat);

                        String lineaF = codigo + " " + nombre + " " + cantidad + " " + precio + " " + " total vendido hoy = " + total + " " + ciudad;
                        bw.write(lineaF);
                        bw.newLine();
                    } else {
                        String lineaF2 = codigo + " " + nombre + " " + cantidad + " " + precio + " " + ciudad;
                        bw.write(lineaF2);
                        bw.newLine();
                    }

                    if (nombre.equalsIgnoreCase("Manzanas")) {
                        sumaCantidad += Integer.parseInt(cantidad);
                        bw.write("Sumando cantidad de manzanas vendidas en cada ciudad: " + sumaCantidad);
                        bw.newLine();
                    }

                }


            }
            System.out.println("Sumando cantidad de manzanas vendidas en cada ciudad: " + sumaCantidad);
            System.out.println("Cantidad de veces que aparece la letra a en el fichero: " + vecesLetra);



        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }

    }
}
