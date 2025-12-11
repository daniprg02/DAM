package socket05;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {

        try(ServerSocket server = new ServerSocket(5000)){
            Socket socket;

            while(true){
                socket = server.accept();
                System.out.println("Cliente conectado");

                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                out.writeUTF("Hola indica tu nombre");
                String nombre = in.readUTF();

                ServidorHilo hilo = new ServidorHilo(in, out, nombre, socket);
                hilo.start();

                System.out.println("Creada la conexión con el cliente " + nombre);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
