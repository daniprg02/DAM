package PreExamen01;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Conector extends Thread {
    private final DataInputStream in;
    private final DataOutputStream out;
    private final Scanner scanner = new Scanner(System.in);

    public Conector(DataInputStream in, DataOutputStream out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {
        // Hilo que se encarga de LEER del servidor y mostrar en consola del cliente
        Thread lectorServidor = new Thread(() -> {
            try {
                while (true) {
                    String mensajeServidor = in.readUTF();  // lee lo que manda el servidor
                    System.out.println("Servidor: " + mensajeServidor);
                }
            } catch (IOException e) {
                System.out.println("Conexión con el servidor cerrada.");
            }
        });

        lectorServidor.setDaemon(true); // se cierra al terminar el programa
        lectorServidor.start();

        // Este hilo (Conector) se encarga de ENVIAR lo que escriba el usuario
        try {
            while (true) {
                String mensaje = scanner.nextLine();  // lo que escribe el usuario
                out.writeUTF(mensaje);                // lo enviamos al servidor
            }
        } catch (IOException e) {
            System.out.println("Error enviando datos al servidor: " + e.getMessage());
        }
    }
}
