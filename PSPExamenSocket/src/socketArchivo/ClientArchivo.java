package socketArchivo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientArchivo {

    public static void main(String[] args) {

        String HOST = "localhost";
        int PORT = 54321;
        String nombreArchivo = "prueba.txt";

        Socket socket = null;
        try {
            socket = new Socket(HOST, PORT);

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            //envio nombre del archivo solicitado
              dos.writeUTF(nombreArchivo);
              long tamano = dis.readLong();
              if (tamano == -1){
                  System.out.println("El servidor indica que el archivo no existe");
              }

              System.out.println("Recibiendo archivo del tamaño: " + tamano + " bytes");

            FileOutputStream fos = new FileOutputStream("copia" + nombreArchivo);

            byte[] buffer = new byte[4096];
            long bytesRecibidos = 0;
            int bytesLeidos = 0;

            while(bytesRecibidos < tamano && (bytesLeidos = dis.read(buffer)) != -1){
                fos.write(buffer, 0, bytesLeidos);
                bytesRecibidos += bytesLeidos;

            }
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }
}
