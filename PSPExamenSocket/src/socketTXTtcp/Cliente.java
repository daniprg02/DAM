package socketTXTtcp;

import java.io.*;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {

        try (Socket socket = new Socket("localhost", 6000)) {

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            File archivo = new File("mensaje.txt");

            FileInputStream fis = new FileInputStream(archivo);

            // Enviamos el nombre
            out.writeUTF(archivo.getName());

            // Enviamos tamaño
            out.writeLong(archivo.length());

            // Enviamos contenido
            byte[] buffer = new byte[4096];
            int leidos;

            while ((leidos = fis.read(buffer)) != -1) {
                out.write(buffer, 0, leidos);
            }

            out.flush();
            System.out.println("Archivo enviado correctamente.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
