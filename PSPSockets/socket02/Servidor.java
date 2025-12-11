package socket02;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Servidor {
    public static void main(String[] args) {
        ArrayList<Incidencia> incidencias = new ArrayList<>();
        ArrayList<Empleado> empleados = new ArrayList<>();
        int puerto = 5000;

        try{
            ServerSocket server = new ServerSocket(puerto);
            Socket sc;

            System.out.println("Servidor en funcionamiento");
            while(true){
                sc = server.accept();
                System.out.println("Cliente conectado");

                DataInputStream dataIn = new DataInputStream(sc.getInputStream());
                DataOutputStream dataOut = new DataOutputStream(sc.getOutputStream());

                while(!sc.isClosed()) {
                    dataOut.writeUTF("Bienvenido al servidor");
                    dataOut.writeUTF("""
                            Opciones del servidor:
                            1 - Registrar incidencia
                            2 - Listar todas las incidencias
                            3 - Buscar incidencia por departamento
                            4 - Cambiar estado de una incidencia
                            5 - Desconectar
                            
                            """);
                    String opcion = dataIn.readUTF();
                    opcion = opcion.trim();


                    switch (opcion) {
                        case "1":
                            dataOut.writeUTF("Ingrese el nombre del empleado");
                            String nombreEmpleado = dataIn.readUTF();
                            dataOut.writeUTF("Ingrese el departamento");
                            String departamento = dataIn.readUTF();
                            dataOut.writeUTF("Ingrese el título de la incidencia");
                            String titulo = dataIn.readUTF();
                            dataOut.writeUTF("Ingrese la prioridad de la incidencia 1 - 5");
                            int prioridad = Integer.parseInt(dataIn.readUTF());

                            while (prioridad < 1 || prioridad > 5) {
                                dataOut.writeUTF("Prioridad no valida");
                                dataOut.writeUTF("Ingrese la prioridad de la incidencia 1 - 5");
                                prioridad = Integer.parseInt(dataIn.readUTF());

                            }

                            String estado = "ABIERTA";
                            dataOut.writeUTF("Ingrese la fecha de la incidencia");
                            String fecha = dataIn.readUTF();

                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            sdf.setLenient(false); //para que no se acepten fechas incorrectas

                            Date fechaIncidencia = sdf.parse(fecha);


                            Empleado empleado = new Empleado(nombreEmpleado, departamento);
                            empleados.add(empleado);
                            Incidencia incidencia = new Incidencia(empleado, titulo, prioridad, fechaIncidencia, estado);

                            incidencias.add(incidencia);
                            dataOut.writeUTF("Incidencia registrada");

                            break;

                        case "2":
                            if (incidencias.isEmpty()) {
                                dataOut.writeUTF("No hay incidencias registradas");
                            } else {
                                for (int i = 0; i < incidencias.size(); i++) {
                                    dataOut.writeUTF(incidencias.get(i).toString());
                                }
                            }
                            break;

                        case "3":
                            dataOut.writeUTF("Ingrese el departamento");
                            String departamentoBuscar = dataIn.readUTF();

                            for (int i = 0; i < empleados.size(); i++) {
                                if (empleados.get(i).getDepartamento().equals(departamentoBuscar)) {
                                    dataOut.writeUTF(empleados.get(i).toString());
                                }
                            }

                            break;

                        case "4":
                            dataOut.writeUTF("Indique el nombre de la incidencia");
                            String nombreIncidencia = dataIn.readUTF();
                            nombreIncidencia = nombreIncidencia.trim();
                            boolean encontrada = false;
                            for (int i = 0; i < incidencias.size(); i++) {
                                if (incidencias.get(i).getTitulo().equals(nombreIncidencia)) {
                                    incidencias.get(i).setEstado("CERRADA");
                                    encontrada = true;
                                    break;
                                }
                            }
                            if (!encontrada) {
                                dataOut.writeUTF("Error: Incidencia no encontrada");
                            }


                            break;

                        case "5":
                            try {
                                sc.close();
                            } catch (IOException ignored) {}
                            System.out.println("Cliente desconectado");
                            break;

                        default:
                            System.out.println("Opción no valida");
                            break;
                    }
                    if (sc.isClosed()) {
                        break;
                    }
                }
            }

        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

    }

}
