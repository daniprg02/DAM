package socketUDP02;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 * ServidorUDP
 *
 * - Escucha en el puerto 6000.
 * - Mantiene un ArrayList<String> con mensajes recibidos.
 * - Soporta dos comandos:
 *     ENVIAR: texto...  -> guarda 'texto...' y responde "Mensaje guardado"
 *     LISTAR            -> responde con todas las entradas (cada una en una línea)
 *
 * Nota importante:
 * - UDP no tiene conexión: cada paquete contiene la IP y puerto de origen
 *   que utilizaremos para enviar la respuesta.
 * - receive() BLOQUEA hasta que llegue un paquete.
 */
public class ServidorUDP {

    public static void main(String[] args) {
        final int PUERTO = 6000;                    // puerto en el que escucha el servidor
        ArrayList<String> almacén = new ArrayList<>(); // lista donde guardaremos mensajes

        // Creamos socket en try-with-resources para cerrarlo automáticamente si hay excepción
        try (DatagramSocket socket = new DatagramSocket(PUERTO)) {
            System.out.println("Servidor UDP iniciado en puerto " + PUERTO);

            // Bucle principal: recibir y procesar paquetes indefinidamente
            while (true) {

                /*
                 * 1) PREPARAR BUFFER Y PAQUETE DE RECEPCIÓN
                 *    - Cada vez creamos un buffer nuevo para evitar que queden datos antiguos.
                 *    - long. máxima elegida 2048 bytes (ajustable según necesidad).
                 */
                byte[] buffer = new byte[2048];
                DatagramPacket paqueteRecepcion = new DatagramPacket(buffer, buffer.length);

                // 2) BLOQUEO: esperamos a que llegue un paquete UDP
                socket.receive(paqueteRecepcion); // <-- aquí el hilo se queda bloqueado hasta recibir algo

                // 3) OBTENER DATOS DEL PAQUETE
                //    paqueteRecepcion.getData() devuelve el array completo; getLength() dice cuantos bytes son realmente del mensaje
                String mensaje = new String(paqueteRecepcion.getData(), 0, paqueteRecepcion.getLength(), "UTF-8").trim();

                // Información útil del remitente (para responderle)
                InetAddress direccionCliente = paqueteRecepcion.getAddress();
                int puertoCliente = paqueteRecepcion.getPort();

                System.out.println("Recibido desde " + direccionCliente + ":" + puertoCliente + " -> " + mensaje);

                /*
                 * 4) PROCESAR MENSAJE SEGÚN PROTOCOLO SIMPLE
                 *    - ENVIAR: texto...  -> guardar texto y responder "Mensaje guardado"
                 *    - LISTAR            -> devolver todas las entradas concatenadas
                 *    - cualquier otra cosa -> responder "Comando desconocido"
                 */
                String respuesta;

                if (mensaje.toUpperCase().startsWith("ENVIAR:")) {
                    // Extraer el texto después de "ENVIAR:"
                    String contenido = mensaje.substring(7).trim(); // desde 7 porque "ENVIAR:" tiene 7 caracteres
                    if (!contenido.isEmpty()) {
                        almacén.add(contenido);               // guardar en la lista
                        respuesta = "Mensaje guardado";
                        System.out.println("Almacén actualizado. Total mensajes = " + almacén.size());
                    } else {
                        respuesta = "ERROR: No se proporcionó texto después de ENVIAR:";
                    }

                } else if (mensaje.equalsIgnoreCase("LISTAR")) {
                    // Construir respuesta con todos los mensajes, numerados y separados por saltos de línea
                    if (almacén.isEmpty()) {
                        respuesta = "NO HAY MENSAJES";
                    } else {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < almacén.size(); i++) {
                            sb.append(i + 1).append(". ").append(almacén.get(i));
                            if (i < almacén.size() - 1) sb.append("\n");
                        }
                        respuesta = sb.toString();
                    }

                } else {
                    respuesta = "ERROR: Comando desconocido. Usa ENVIAR: texto... o LISTAR";
                }

                /*
                 * 5) ENVIAR RESPUESTA AL CLIENTE
                 *    - Convertimos la respuesta a bytes y la envolvemos en un DatagramPacket dirigido
                 *      a la dirección y puerto de origen del paquete recibido.
                 */
                byte[] datosRespuesta = respuesta.getBytes("UTF-8");
                DatagramPacket paqueteEnvio = new DatagramPacket(
                        datosRespuesta,
                        datosRespuesta.length,
                        direccionCliente,
                        puertoCliente
                );

                socket.send(paqueteEnvio); // enviamos la respuesta
                // Volvemos al comienzo del while para recibir el siguiente paquete
            }

        } catch (IOException e) {
            // En producción deberías manejar errores de forma más fina
            e.printStackTrace();
            System.out.println("El servidor UDP ha terminado por error.");
        }
    }
}
