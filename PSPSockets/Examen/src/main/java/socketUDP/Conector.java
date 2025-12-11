package socketUDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Conector extends Thread {
    private final DatagramSocket socket;
    private final InetAddress servidor;
    private final int puertoServidor;
    private final Scanner sc;

    public Conector(DatagramSocket socket, InetAddress servidor, int puertoServidor, Scanner sc) {
        this.socket = socket;
        this.servidor = servidor;
        this.puertoServidor = puertoServidor;
        this.sc = sc;
    }

    @Override
    public void run() {
        System.out.println("Cliente UDP de incidencias.");
        System.out.println("Escribe MENU para ver los comandos.");
        System.out.println("Escribe SALIR para terminar.");
        System.out.println();

        try {
            while (true) {
                System.out.print("Comando> ");
                String mensaje = sc.nextLine().trim();

                if (mensaje.equalsIgnoreCase("SALIR")) {
                    System.out.println("Cerrando cliente.");
                    break;
                }

                // 1. Enviar comando al servidor
                byte[] bufferSalida = mensaje.getBytes(StandardCharsets.UTF_8);
                DatagramPacket paqueteSalida = new DatagramPacket(
                        bufferSalida,
                        bufferSalida.length,
                        servidor,
                        puertoServidor
                );
                socket.send(paqueteSalida);

                // 2. Recibir respuesta del servidor
                byte[] bufferEntrada = new byte[4096];
                DatagramPacket paqueteEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);
                socket.receive(paqueteEntrada);

                String respuesta = new String(
                        paqueteEntrada.getData(),
                        0,
                        paqueteEntrada.getLength(),
                        StandardCharsets.UTF_8
                );

                System.out.println("Servidor:\n" + respuesta);
            }
        } catch (IOException e) {
            System.out.println("Error en la comunicación UDP: " + e.getMessage());
        }
    }
}
