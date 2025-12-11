package socketTXTudp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class Servidor {

    public static void main(String[] args) {

        int puerto = 5000;

        try (DatagramSocket socket = new DatagramSocket(puerto)) {

            System.out.println("Servidor UDP esperando archivo...");

            byte[] buffer = new byte[2048];

            // 1️⃣ Recibir nombre del archivo
            DatagramPacket paqueteNombre = new DatagramPacket(buffer, buffer.length);
            socket.receive(paqueteNombre);
            String nombreArchivo = new String(paqueteNombre.getData(), 0, paqueteNombre.getLength());
            System.out.println("Nombre archivo: " + nombreArchivo);

            // 2️⃣ Recibir número de paquetes
            DatagramPacket paqueteInfo = new DatagramPacket(buffer, buffer.length);
            socket.receive(paqueteInfo);
            int numPaquetes = Integer.parseInt(new String(paqueteInfo.getData(), 0, paqueteInfo.getLength()));

            System.out.println("Esperando " + numPaquetes + " paquetes...");

            // donde guardamos los bytes del archivo completo
            ByteArrayOutputStreamCompleto acumulador = new ByteArrayOutputStreamCompleto();

            // 3️⃣ Recibir cada paquete de datos
            for (int i = 0; i < numPaquetes; i++) {

                DatagramPacket paqueteDatos = new DatagramPacket(buffer, buffer.length);
                socket.receive(paqueteDatos);

                // Recortar a los bytes exactos del paquete
                byte[] datos = Arrays.copyOfRange(paqueteDatos.getData(), 0, paqueteDatos.getLength());
                acumulador.addFragmento(datos);

                System.out.println("Paquete " + (i + 1) + "/" + numPaquetes + " recibido.");
            }

            // 4️⃣ Archivo reconstruido
            byte[] archivoCompleto = acumulador.getBytes();

            // Guardar archivo recibido
            FileOutputStream fos = new FileOutputStream("RECIBIDO_" + nombreArchivo);
            fos.write(archivoCompleto);
            fos.close();

            System.out.println("Archivo reconstruido correctamente.");

            // 5️⃣ Mostrar contenido
            System.out.println("\n--- CONTENIDO DEL ARCHIVO ---");
            System.out.println(new String(archivoCompleto, "UTF-8"));
            System.out.println("-----------------------------\n");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

/**
 * Clase auxiliar simple para ir acumulando los bytes recibidos
 */
class ByteArrayOutputStreamCompleto {
    private byte[] data = new byte[0];

    public void addFragmento(byte[] frag) {
        byte[] nuevo = new byte[data.length + frag.length];
        System.arraycopy(data, 0, nuevo, 0, data.length);
        System.arraycopy(frag, 0, nuevo, data.length, frag.length);
        data = nuevo;
    }

    public byte[] getBytes() {
        return data;
    }
}
