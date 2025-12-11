package socketUDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Servidor {

    public static void main(String[] args) {
        ArrayList<Incidencia> incidencias = new ArrayList<>();
        ArrayList<Empleado> empleados = new ArrayList<>();
        int puerto = 5000;

        try (DatagramSocket socket = new DatagramSocket(puerto)) {
            System.out.println("Servidor UDP de incidencias en funcionamiento (puerto " + puerto + ")");

            byte[] bufferEntrada = new byte[2048];

            while (true) {
                // 1. Recibir paquete del cliente
                DatagramPacket paqueteEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);
                socket.receive(paqueteEntrada);

                String mensaje = new String(
                        paqueteEntrada.getData(),
                        0,
                        paqueteEntrada.getLength(),
                        StandardCharsets.UTF_8
                ).trim();

                System.out.println("Recibido de " + paqueteEntrada.getAddress() + ":" + paqueteEntrada.getPort() +
                        " -> " + mensaje);

                // 2. Procesar comando
                String respuesta = procesarComando(mensaje, incidencias, empleados);

                // 3. Enviar respuesta al cliente
                byte[] bufferSalida = respuesta.getBytes(StandardCharsets.UTF_8);
                DatagramPacket paqueteSalida = new DatagramPacket(
                        bufferSalida,
                        bufferSalida.length,
                        paqueteEntrada.getAddress(),
                        paqueteEntrada.getPort()
                );
                socket.send(paqueteSalida);
            }

        } catch (SocketException e) {
            System.out.println("Error creando socket UDP: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error de E/S en el servidor UDP: " + e.getMessage());
        }
    }

    private static String procesarComando(String mensaje,
                                          ArrayList<Incidencia> incidencias,
                                          ArrayList<Empleado> empleados) {

        if (mensaje.equalsIgnoreCase("MENU")) {
            return """
                    Bienvenido al servidor UDP de incidencias
                    Comandos:
                    MENU
                    REGISTRAR;nombre;departamento;titulo;prioridad(1-5);dd/MM/yyyy
                    LISTAR
                    BUSCAR;departamento
                    CERRAR;tituloIncidencia
                    """;
        }

        if (mensaje.equalsIgnoreCase("LISTAR")) {
            if (incidencias.isEmpty()) {
                return "No hay incidencias registradas";
            }
            StringBuilder sb = new StringBuilder();
            for (Incidencia incidencia : incidencias) {
                sb.append(incidencia.toString()).append("\n");
            }
            return sb.toString();
        }

        if (mensaje.toUpperCase().startsWith("REGISTRAR;")) {
            // Formato: REGISTRAR;nombre;departamento;titulo;prioridad;fecha(dd/MM/yyyy)
            String[] partes = mensaje.split(";", 6);
            if (partes.length < 6) {
                return "ERROR: Formato REGISTRAR incorrecto. " +
                        "Usa: REGISTRAR;nombre;departamento;titulo;prioridad;dd/MM/yyyy";
            }
            String nombreEmpleado = partes[1];
            String departamento = partes[2];
            String titulo = partes[3];
            int prioridad;
            try {
                prioridad = Integer.parseInt(partes[4]);
            } catch (NumberFormatException e) {
                return "ERROR: prioridad no válida. Debe ser un número entre 1 y 5.";
            }
            if (prioridad < 1 || prioridad > 5) {
                return "ERROR: prioridad fuera de rango (1-5).";
            }

            String fechaStr = partes[5];
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            Date fechaIncidencia;
            try {
                fechaIncidencia = sdf.parse(fechaStr);
            } catch (ParseException e) {
                return "ERROR: fecha no válida. Usa formato dd/MM/yyyy.";
            }

            String estado = "ABIERTA";
            Empleado empleado = new Empleado(nombreEmpleado, departamento);
            empleados.add(empleado);
            Incidencia incidencia = new Incidencia(empleado, titulo, prioridad, fechaIncidencia, estado);
            incidencias.add(incidencia);

            return "Incidencia registrada correctamente";
        }

        if (mensaje.toUpperCase().startsWith("BUSCAR;")) {
            // Formato: BUSCAR;departamento
            String[] partes = mensaje.split(";", 2);
            if (partes.length < 2) {
                return "ERROR: Formato BUSCAR incorrecto. Usa: BUSCAR;departamento";
            }
            String departamentoBuscar = partes[1];
            StringBuilder sb = new StringBuilder();
            for (Incidencia incidencia : incidencias) {
                if (incidencia.getEmpleado().getDepartamento().equals(departamentoBuscar)) {
                    sb.append(incidencia.toString()).append("\n");
                }
            }
            if (sb.length() == 0) {
                return "No se encontraron incidencias para el departamento: " + departamentoBuscar;
            }
            return sb.toString();
        }

        if (mensaje.toUpperCase().startsWith("CERRAR;")) {
            // Formato: CERRAR;tituloIncidencia
            String[] partes = mensaje.split(";", 2);
            if (partes.length < 2) {
                return "ERROR: Formato CERRAR incorrecto. Usa: CERRAR;tituloIncidencia";
            }
            String titulo = partes[1].trim();
            boolean encontrada = false;
            for (Incidencia incidencia : incidencias) {
                if (incidencia.getTitulo().equals(titulo)) {
                    incidencia.setEstado("CERRADA");
                    encontrada = true;
                    break;
                }
            }
            if (!encontrada) {
                return "Error: Incidencia no encontrada";
            }
            return "Incidencia cerrada correctamente";
        }

        return "COMANDO NO RECONOCIDO. Usa MENU para ver los comandos disponibles.";
    }
}