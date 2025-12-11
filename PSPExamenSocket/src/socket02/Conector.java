package socket02;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Conector extends Thread{
    private final DataInputStream in;
    private final DataOutputStream out;
    private final Scanner sc = new Scanner(System.in);

    public Conector(DataInputStream in, DataOutputStream out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {

        //leemos del servidor para mostrar al cliente
        Thread lectorServidor = new Thread(() -> {

            while (true) {
                String mensajeServidor = null; //lee lo que manda el servidor
                try {
                    mensajeServidor = in.readUTF();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Servidor: " + mensajeServidor);
            }
        });

        lectorServidor.setDaemon(true);
        lectorServidor.start();

        //enviamos lo que escriba el usuario
        try {
            while (true) {
                String mensaje = sc.nextLine();
                out.writeUTF(mensaje);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
