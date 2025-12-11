package socket04;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {
    public static void main(String[] args) {
        ServerSocket servidor = null;
        Socket sc = null;
        final int PUERTO = 5000;
        DataInputStream in = null;
        DataOutputStream out = null;

        try {
            servidor = new ServerSocket(PUERTO);
            System.out.println("Servidor funcionando en puerto " + PUERTO);

            while(true){
                //Se queda esperando un cliente y cogemos el Socket del cliente
                sc = servidor.accept();
                System.out.println("Cliente conectado");

                in = new DataInputStream(sc.getInputStream());
                out = new DataOutputStream(sc.getOutputStream());

                String mensaje = in.readUTF();
                out.writeUTF("Mensaje recibido " + mensaje);
                System.out.println("Mensaje recibido: " + mensaje);

                //Cerramos el cliente
                sc.close();
                System.out.println("Cliente desconectado");
            }


        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
