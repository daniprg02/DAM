package socketTXTudp;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.nio.file.Files;

public class Cliente {

    public static void main(String[] args) throws IOException {

        InetAddress servidor = InetAddress.getByName("localhost");
        int puerto = 5000;

        DatagramSocket socket = new DatagramSocket();

        File archivo = new File("mensaje.txt");

        if (!archivo.exists()) {
            System.out.println("ERROR: mensaje.txt no existe");
            return;
        }

        System.out.println("Enviando archivo: " + archivo.getName());

        // Leer fichero entero
        byte[] datosArchivo = Files.readAllBytes(archivo.toPath());

        // Tamaño del fragmento UDP
        int fragmento = 1024;
        int numPaquetes = (int) Math.ceil((double) datosArchivo.length / fragmento);

        // 1️⃣ Enviar nombre del archivo
        byte[] nombreBytes = archivo.getName().getBytes();
        DatagramPacket pNombre = new DatagramPacket(
                nombreBytes, nombreBytes.length, servidor, puerto);
        socket.send(pNombre);

        // 2️⃣ Enviar número de paquetes
        byte[] info = String.valueOf(numPaquetes).getBytes();
        DatagramPacket pInfo = new DatagramPacket(info, info.length, servidor, puerto);
        socket.send(pInfo);

        // 3️⃣ Envío de todos los fragmentos
        int offset = 0;
        for (int i = 0; i < numPaquetes; i++) {

            int tamaño = Math.min(fragmento, datosArchivo.length - offset);

            byte[] parte = new byte[tamaño];
            System.arraycopy(datosArchivo, offset, parte, 0, tamaño);

            DatagramPacket pDatos = new DatagramPacket(parte, parte.length, servidor, puerto);
            socket.send(pDatos);

            offset += tamaño;

            System.out.println("Enviado paquete " + (i + 1) + "/" + numPaquetes);
        }

        System.out.println("Archivo enviado correctamente.");
        socket.close();
    }
}

