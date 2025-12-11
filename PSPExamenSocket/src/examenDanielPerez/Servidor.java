package examenDanielPerez;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {

        //Creamos el server socket dentro del try para que se cierre el automáticamente
        try(ServerSocket serverSocket = new ServerSocket(5000);) {
            Socket socket;
            System.out.println("Iniciando servidor");
            //El servidor va a escuchar t odo el rato peticiones

            while(true){
                //Esperamos una conexión
                socket = serverSocket.accept();
                System.out.println("Cliente conectado desde " + socket.getInetAddress());

                //Creamos para recibir y enviar datos de tipo primitivo
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                ServidorHilo hilo = new ServidorHilo(in, out, socket);
                //Iniciamos el hilo
                hilo.start();

                System.out.println("Creada la conexión con el cliente " + socket.getInetAddress());

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
