package socketTXTtcp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    public static void main(String[] args) {

        int puerto = 6000;

        try (ServerSocket server = new ServerSocket(puerto)) {

            System.out.println("Servidor TCP esperando archivo...");

            // Espera a que un cliente se conecte (bloqueante)
            Socket cliente = server.accept();
            System.out.println("Cliente conectado.");

            // Flujos para recibir datos
            DataInputStream dis = new DataInputStream(cliente.getInputStream());

            // 1. Primero recibimos el nombre del archivo
            String nombreArchivo = dis.readUTF();
            System.out.println("Recibiendo archivo: " + nombreArchivo);

            // 2. Luego recibimos el tamaño del archivo
            long tamaño = dis.readLong();

            // 3. Preparamos un array de bytes para almacenarlo
            byte[] buffer = new byte[(int) tamaño];

            // 4. Leemos todos los bytes del archivo
            dis.readFully(buffer);

            // 5. Guardamos el archivo (opcional)
            FileOutputStream fos = new FileOutputStream(nombreArchivo);
            fos.write(buffer);
            fos.close();

            System.out.println("Archivo recibido correctamente.");

            // 6. MOSTRAR EL CONTENIDO EN EL SERVIDOR
            System.out.println("\n--- CONTENIDO DEL ARCHIVO ---");
            String contenido = new String(buffer, "UTF-8");
            System.out.println(contenido);
            System.out.println("-----------------------------\n");

            cliente.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
