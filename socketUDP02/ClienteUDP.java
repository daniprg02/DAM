package socketUDP02;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * ClienteUDPInteractivo
 *
 * - Envía comandos al servidor UDP en localhost:6000
 * - Comandos válidos (según el ServidorUDP):
 *     ENVIAR: texto...   -> guarda el texto en el servidor
 *     LISTAR             -> solicita la lista de mensajes
 *     SALIR              -> cierra el cliente localmente
 *
 * - Después de enviar cada comando, espera (bloqueante) una respuesta del servidor
 *   y la muestra por pantalla.
 */
public class ClienteUDP {

    public static void main(String[] args) {

        final String HOST_SERVIDOR = "localhost"; // IP del servidor
        final int PUERTO_SERVIDOR = 6000;         // puerto donde el servidor escucha

        // Scanner para leer del teclado (entrada del usuario)
        Scanner scanner = new Scanner(System.in);

        /*
         * Creamos el DatagramSocket sin especificar puerto:
         * - El SO asignará un puerto libre al socket del cliente.
         * - Esto evita colisiones con el puerto del servidor.
         *
         * Try-with-resources para asegurarnos de cerrar el socket automáticamente.
         */
        try (DatagramSocket socket = new DatagramSocket()) {

            System.out.println("Cliente UDP iniciado. Enviar comandos al servidor en " +
                    HOST_SERVIDOR + ":" + PUERTO_SERVIDOR);
            System.out.println("Ejemplos: \"ENVIAR: Hola servidor\"  ó  \"LISTAR\"  ó  \"SALIR\"");

            while (true) {
                // 1) Leemos la línea que escribe el usuario
                System.out.print("> ");
                String linea = scanner.nextLine();
                if (linea == null) continue;
                linea = linea.trim();

                // 2) Comando para salir del cliente localmente
                if (linea.equalsIgnoreCase("SALIR")) {
                    System.out.println("Cerrando cliente...");
                    break; // salimos del bucle y se cierra socket por try-with-resources
                }

                // 3) Convertimos el mensaje a bytes (UTF-8 recomendado para acentos)
                byte[] datosAEnviar = linea.getBytes("UTF-8");

                // 4) Creamos el DatagramPacket de envío con destino al servidor
                InetAddress direccionServidor = InetAddress.getByName(HOST_SERVIDOR);
                DatagramPacket paqueteEnvio = new DatagramPacket(
                        datosAEnviar,                // datos
                        datosAEnviar.length,         // longitud real de datos
                        direccionServidor,           // dirección destino
                        PUERTO_SERVIDOR              // puerto destino
                );

                // 5) Enviamos el paquete al servidor (no hay confirmación en UDP)
                socket.send(paqueteEnvio);
                // Nota: send() no garantiza entrega. El servidor puede o no recibirlo.

                // 6) Preparamos un buffer para recibir la respuesta del servidor
                //    Si el servidor envía una respuesta grande, deberíamos aumentar este tamaño.
                byte[] bufferRespuesta = new byte[4096];
                DatagramPacket paqueteRespuesta = new DatagramPacket(bufferRespuesta, bufferRespuesta.length);

                /*
                 * 7) Recibir (BLOQUEANTE):
                 *    socket.receive(...) bloquea el hilo hasta que llega un paquete
                 *    dirigido a este socket (al puerto asignado automáticamente).
                 *
                 *    Si el servidor NO responde, el cliente se quedará esperando aquí.
                 *    Para no bloquear indefinidamente podrías usar socket.setSoTimeout(ms).
                 */
                try {
                    socket.receive(paqueteRespuesta);
                } catch (IOException recvEx) {
                    System.out.println("Error recibiendo respuesta: " + recvEx.getMessage());
                    // en caso de timeout o error, continuar el bucle para permitir más envíos
                    continue;
                }

                // 8) Convertimos la respuesta a String usando la longitud real recibida
                String respuesta = new String(
                        paqueteRespuesta.getData(),
                        0,
                        paqueteRespuesta.getLength(),
                        "UTF-8"
                );

                // 9) Mostramos la respuesta al usuario
                System.out.println("Respuesta del servidor:\n" + respuesta);
                // Volvemos al inicio del bucle para permitir nuevos comandos
            }

        } catch (IOException e) {
            // Manejo básico de errores: impresión de la excepción
            System.err.println("Error en cliente UDP: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cierre del Scanner (buenas prácticas)
            try {
                scanner.close();
            } catch (Exception ignored) { }
        }

        System.out.println("Cliente finalizado.");
    }
}
