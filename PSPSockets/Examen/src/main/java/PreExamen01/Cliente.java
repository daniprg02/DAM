package PreExamen01;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {

        try(Socket socket = new Socket("localhost", 12345);){

            Scanner sc = new Scanner(System.in);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            Conector conector = new Conector(in, out);
            conector.start();
            conector.join();

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}
