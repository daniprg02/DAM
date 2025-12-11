package socketUDP;


import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {

        try (DatagramSocket socket = new DatagramSocket();
             Scanner sc = new Scanner(System.in)) {

            InetAddress servidor = InetAddress.getByName("localhost");
            int puertoServidor = 5000;

            Conector conector = new Conector(socket, servidor, puertoServidor, sc);
            conector.start();
            conector.join();

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
