package socket04;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {
    public static void main(String[] args) {
        DataInputStream in = null;
        DataOutputStream out = null;
        String host = "localhost";
        int puerto = 5000;

        try {
            Socket socket = new Socket(host, puerto);

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            out.writeUTF("Hola desde el cliente");
            String mensaje = in.readUTF();
            System.out.println("Mensaje recibido: " + mensaje);
            socket.close();


        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
