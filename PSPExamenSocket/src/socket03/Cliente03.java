package socket03;

import java.io.*;
import java.net.Socket;

public class Cliente03 {

    public static void main(String[] args) {

        try (Socket socket = new Socket("localhost", 5000)) {

            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

            // IMPORTANTE: primero OOS, luego OIS
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            // Recibo mensaje del servidor
            System.out.println(ois.readUTF());
            String id = teclado.readLine();
            oos.writeUTF(id);
            oos.flush();

            System.out.println(ois.readUTF());
            String nombre = teclado.readLine();
            oos.writeUTF(nombre);
            oos.flush();

            // Confirmación
            System.out.println(ois.readUTF());

            // Recibo objeto Persona03
            Persona03 persona = (Persona03) ois.readObject();

            // Muestro los datos del objeto recibido
            System.out.println("Objeto recibido del servidor:");
            System.out.println(persona);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
