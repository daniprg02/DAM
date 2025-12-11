package examenDanielPerez;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteChat extends Thread{
    private DataInputStream dis;
    private DataOutputStream salida;
    private Socket socket;

    Scanner sc = new Scanner(System.in);

    public ClienteChat(DataInputStream in, DataOutputStream salida, Socket socket){
        this.dis = in;
        this.salida = salida;
        this.socket = socket;
    }

        @Override
        public void run() {

            System.out.println("1. Enviar mensaje\n2. Enviar archivo");


            try {
                int opcion = sc.nextInt();
                salida.writeInt(opcion);
                sc.nextLine(); // limpiar


                if (opcion == 1) {
                    salida.writeUTF("MSG");
                    System.out.print("Escribe el mensaje: ");
                    String msg = sc.nextLine();
                    salida.writeUTF(msg);
                } else if (opcion == 2) {
                    salida.writeUTF("FILE");
                    System.out.print("Ruta del archivo: ");
                    String ruta = sc.nextLine();
                    File archivo = new File(ruta);
                    salida.writeUTF(archivo.getName());
                    salida.writeLong(archivo.length());

                    FileInputStream fis = new FileInputStream(archivo);
                    byte[] buffer = new byte[4096];
                    int leido;
                    while ((leido = fis.read(buffer)) != -1) {
                        salida.write(buffer, 0, leido);
                    }
                    fis.close();
                    System.out.println("Archivo enviado correctamente.");
                }

                socket.close();

            } catch (IOException e) {
                System.out.println("Error en ClienteHilo: " + e.getMessage());
            }


        }
}
