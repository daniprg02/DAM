package sockets01;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class mainCliente {
    public static void main(String[] args) {

        try{
            Scanner sc = new Scanner(System.in);
            sc.useDelimiter("\n");

            Socket socket = new Socket("localhost", 5000);

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            Conector conector = new Conector(in, out);
            conector.start();
            conector.join();


        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(mainCliente.class.getName()).log(Level.SEVERE, null, ex);
        }

}

}
