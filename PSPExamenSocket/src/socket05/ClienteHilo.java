package socket05;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClienteHilo extends Thread{
    private DataInputStream in;
    private DataOutputStream out;
    private Socket socket;

    private ArrayList<Persona> personas = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);

    public ClienteHilo(DataInputStream in, DataOutputStream out, Socket socket) {
        this.in = in;
        this.out = out;
        this.socket = socket;
    }

    @Override
    public void run(){
        int opcion;
        boolean salir = false;

        try {
            while(!salir){

                System.out.println("""
                        1. Almacenar número en el archivo
                        2. Números almacenados en el archivo
                        3. Lista de números almacenados
                        4. Números totales escritos por ti
                        5. Archivo con tus números
                        6. Crear Persona (OBJETO)
                        7. Salir
                        
                        """);

                opcion = sc.nextInt();
                out.writeInt(opcion);
                out.flush();

                switch(opcion){
                    case 1:
                        System.out.println("Indique un número:");
                        int numero = sc.nextInt();
                        out.writeInt(numero);
                        System.out.println(in.readUTF());
                        break;

                    case 2:
                        System.out.println("Hay " + in.readInt() + " números en total.");
                        break;

                    case 3:
                        int limit = in.readInt();
                        for(int i = 0; i < limit; i++)
                            System.out.println(in.readUTF());
                        break;

                    case 4:
                        System.out.println("Has escrito " + in.readInt() + " líneas.");
                        break;

                    case 5:
                        int count = in.readInt();
                        ArrayList<String> lineas = new ArrayList<>();
                        for(int i = 0; i < count; i++)
                            lineas.add(in.readUTF());

                        try (BufferedWriter bw = new BufferedWriter(new FileWriter("numeros_cliente.txt"))) {
                            for(String l : lineas) bw.write(l + "\n");
                        }
                        System.out.println("Archivo generado.");
                        break;

                    // 🔥🔥🔥 NUEVO CASE: CREAR Y RECIBIR PERSONA
                    case 6:

                        sc.nextLine(); // limpiar buffer

                        // Nombre
                        System.out.println(in.readUTF());
                        String nombre = sc.nextLine();
                        out.writeUTF(nombre);

                        // Edad
                        System.out.println(in.readUTF());
                        int edad = sc.nextInt();
                        out.writeInt(edad);
                        sc.nextLine();

                        // Descripción
                        System.out.println(in.readUTF());
                        String desc = sc.nextLine();
                        out.writeUTF(desc);

                        // Recibir el objeto Persona
                        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                        Persona p = (Persona) ois.readObject();
                        personas.add(p);

                        System.out.println("Persona recibida:");
                        System.out.println(p);

                        System.out.println(in.readUTF());
                        break;

                    case 7:
                        salir = true;
                        break;

                    default:
                        System.out.println(in.readUTF());
                }
            }

            System.out.println("Cerrando conexión...");
            sc.close();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error en ClienteHilo: " + e.getMessage());
        }
    }
}
