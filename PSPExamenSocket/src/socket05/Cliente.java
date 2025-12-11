package socket05;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try(Socket socket = new Socket("localhost", 5000)){

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            String mensaje = in.readUTF();
            System.out.println(mensaje);

            out.writeUTF(sc.nextLine());

            ClienteHilo hilo = new ClienteHilo(in, out, socket);
            hilo.start();
            hilo.join();

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
