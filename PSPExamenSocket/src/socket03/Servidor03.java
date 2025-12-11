package socket03;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class Servidor03 {

    public static void main(String[] args) {

        try (ServerSocket server = new ServerSocket(5000)) {

            System.out.println("Servidor funcionando en puerto 5000");

            while (true) {
                Socket socket = server.accept();
                System.out.println("Cliente conectado");

                // IMPORTANTE: SIEMPRE primero OOS y luego OIS
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

                // Pedimos datos al cliente
                oos.writeUTF("Bienvenido. Introduce un ID:");
                oos.flush();

                String id = ois.readUTF();

                oos.writeUTF("Introduce un nombre:");
                oos.flush();

                String nombre = ois.readUTF();

                // Creamos el objeto
                String horaActual = LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute();
                Persona03 persona = new Persona03(id, nombre, horaActual);

                // Confirmamos registro y enviamos el objeto
                oos.writeUTF("Persona registrada correctamente y enviada.");
                oos.writeObject(persona);
                oos.flush();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
