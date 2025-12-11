package PreExamen01;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Servidor {
    public static void main(String[] args) {
        ArrayList<Persona> personas = new ArrayList<>();

        try(ServerSocket serverSocket = new ServerSocket(12345);){
            Socket socket;

            System.out.println("Iniciando servidor");
            while(true){
                socket = serverSocket.accept();
                System.out.println("Servidor conectado");

                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());


                out.writeUTF("Bienvenido al servidor");
                int horaDelServidor = LocalDateTime.now().getHour();
                int minutoDelServidor = LocalDateTime.now().getMinute();
                int segundoDelServidor = LocalDateTime.now().getSecond();
                out.writeUTF("La hora del servidor es : " + horaDelServidor + ":" + minutoDelServidor + ":" + segundoDelServidor);
                String hora = horaDelServidor + ":" + minutoDelServidor + ":" + segundoDelServidor;

                out.writeUTF("Indique su ID");
                String id = in.readUTF();
                out.writeUTF("Indique su nombre");
                String nombre = in.readUTF();
                Persona persona = new Persona(id, nombre, hora);
                personas.add(persona);
                out.writeUTF("para listar las personas, escriba LISTAR");
                String comando =  in.readUTF();
                if(comando.equalsIgnoreCase("listar")){
                    for(Persona p : personas){
                        out.writeUTF(p.toString());
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
