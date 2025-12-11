package socketArchivo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerArchivo {

    public static void main(String[] args){
        int puerto = 54321;

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(puerto);
            System.out.println("Servidor iniciado en el puerto " + puerto);

            //hasta que no se conecte un cliente, no pasa de aquí

            Socket socket = serverSocket.accept();
            System.out.println("Cliente conectado desde " + socket.getInetAddress());

            DataInputStream dis = new DataInputStream(socket.getInputStream());

            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            //Recibir nombre del archivo solicitado

            String nombreArchivo = dis.readUTF();

            System.out.println("El cliente solicita el archivo: " + nombreArchivo);

            File archivo = new File(nombreArchivo);
            //compruebo si existe, sino no se lo puedo pasar
            if(!archivo.exists()){
                dos.writeLong(-1);
                //el cliente me manda el tamaño del archivo
                //si no existe: es decir -1 , avisamos que no ha sido encontrado
                System.out.println("El archivo no existe");
                socket.close();

            }

            long  tamano = archivo.length();
            dos.writeLong(tamano);
            dos.flush();
            FileInputStream fis = new FileInputStream(archivo);
            byte[] buffer = new byte[4096];
            int bytesLeidos = 0;

            while((bytesLeidos = fis.read(buffer)) != -1){
                dos.write(buffer, 0, bytesLeidos);
                //3 parámetros: desde donde (lo que dice el buffer), desde donde en el buffer, hasta donde (bytesLeidos)
            }
            fis.close();
            socket.close();
            System.out.println("Archivo enviado al cliente");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


     }
}
