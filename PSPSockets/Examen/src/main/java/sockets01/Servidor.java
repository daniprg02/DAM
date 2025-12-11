package sockets01;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {
    public static void main(String[] args) {
        ArrayList <Tarea> tareas = new ArrayList<>();
        int puerto = 5000;

        try{
            ServerSocket server = new ServerSocket(puerto);
            Socket sc;

            System.out.println("Servidor iniciado");
            while(true){
                sc = server.accept();
                System.out.println("Cliente conectado");

                DataInputStream data = new DataInputStream(sc.getInputStream());
                DataOutputStream dataOut = new DataOutputStream(sc.getOutputStream());

                dataOut.writeUTF("Bienvenido al servidor");
                dataOut.writeUTF("""
                        Para listar las tareas ingrese LISTAR
                        Para agregar una tarea ingrese AGREGAR
                        """);
                String comando = data.readUTF();

                switch(comando.toLowerCase()){
                    case "listar":
                        for (int i = 0; i < tareas.size(); i++) {
                            dataOut.writeUTF(tareas.get(i).toString());
                        }
                        break;

                        case "agregar":
                            dataOut.writeUTF("Ingrese el nombre de la persona");
                            String nombre = data.readUTF();
                            dataOut.writeUTF("Ingrese la edad de la persona");
                            String edad = data.readUTF();
                            Persona persona = new Persona(nombre, Integer.parseInt(edad));
                            dataOut.writeUTF("Ingrese la descripcion de la tarea");
                            String descripcion = data.readUTF();
                            dataOut.writeUTF("Ingrese la fecha de la tarea");
                            String fecha = data.readUTF();
                            Tarea tarea = new Tarea(persona, descripcion, fecha);
                            tareas.add(tarea);
                            dataOut.writeUTF("Tarea agregada");
                            break;

                    default:
                        throw new IllegalStateException("Unexpected value: " + comando.toLowerCase());
                }
            }


        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }




}
