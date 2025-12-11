package socket02;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {

        try{
            Scanner sc = new Scanner(System.in);
            Socket socket = new Socket("localhost", 5000);

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
