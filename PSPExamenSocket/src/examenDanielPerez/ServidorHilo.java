package examenDanielPerez;

import java.io.*;
import java.net.Socket;

public class ServidorHilo extends Thread{
    private DataInputStream in;
    private DataOutputStream out;
    private Socket socket;

    //Constructor de servidorHilo
    public ServidorHilo(DataInputStream in, DataOutputStream out, Socket socket) {
        this.in = in;
        this.out = out;
        this.socket = socket;

    }
    //Creamos un hilo independiente que se va a encargar de escuchar el mét odo run del cliente
    @Override
    public void run() {

        int opcion;
        File file = new File("mensaje.txt");

        try {
            while (true) {
                opcion = in.readInt();
                //Si la opcion es igual a 1 entra
                if (opcion == 1) {
                    String msg = in.readUTF();
                    //Si el mensaje es igual a MSG, vamos a guardar su nombre en un fichero de texto
                        String nombreCliente = in.readUTF();
                        System.out.println("El nombre del cliente es: " + nombreCliente);
                        //Con append true, concatena en el archivo el resto de nombres que
                        //introduzcamos posteriormente
                        try (FileWriter fw = new FileWriter(file, true)) {
                            fw.write(msg + ": " + nombreCliente + "\n");
                            fw.flush();

                            System.out.println("Archivo escrito con éxito");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                } else if (opcion == 2) {
                    String mensaje2 = in.readUTF();

                    System.out.println(mensaje2+ " enviado desde el cliente");
                    String rutaArchivo = in.readUTF();

                    //1. Recibimos el nombre
                    System.out.println("Recibiendo archivo: " + rutaArchivo);

                    // 2. Luego recibimos el tamaño del archivo
                    long tamanio = in.readLong();

                    // 3. Preparamos un array de bytes para almacenarlo
                    byte[] buffer = new byte[(int) tamanio];

                    // 4. Leemos todos los bytes del archivo
                    in.readFully(buffer);

                    // 5. Guardamos el archivo
                    FileOutputStream fos = new FileOutputStream(rutaArchivo);
                    fos.write(buffer);
                    fos.close();

                    System.out.println("Archivo recibido correctamente.");

                    // 6. MOSTRAR EL CONTENIDO EN EL SERVIDOR
                    System.out.println("\n--- CONTENIDO DEL ARCHIVO ---");
                    String contenido = new String(buffer, "UTF-8");
                    System.out.println(contenido);
                    System.out.println("-----------------------------\n");


                }
            }

            //Cerramos el socket del cliente
        } catch (IOException e) {
            System.out.println("Error en ServidorHilo con " + ": " + e.getMessage());

        }
    }
}




