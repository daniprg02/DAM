package socket05;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ServidorHilo extends Thread{
    private DataInputStream in;
    private DataOutputStream out;
    private String nombreCliente;
    private Socket socket;

    public ServidorHilo(DataInputStream in, DataOutputStream out, String nombreCliente, Socket socket) {
        this.in = in;
        this.out = out;
        this.socket = socket;
        this.nombreCliente = nombreCliente;
    }

    @Override
    public void run(){

        int opcion;
        File file = new File("numeros.txt");

        try {
            while(true){

                opcion = in.readInt();

                switch(opcion){
                    case 1:
                        int numero = in.readInt();
                        try (FileWriter fw = new FileWriter(file, true)) {
                            fw.write(nombreCliente + ": " + numero + "\n");
                            fw.flush();
                            out.writeUTF("El número se ha añadido correctamente");
                        }
                        break;

                    case 2:
                        int numLineas = 0;
                        try(BufferedReader br = new BufferedReader(new FileReader(file))){
                            while(br.readLine() != null){
                                numLineas++;
                            }
                        }
                        out.writeInt(numLineas);
                        break;

                    case 3:
                        ArrayList<String> listaNumeros = new ArrayList<>();
                        try(BufferedReader br = new BufferedReader(new FileReader(file))){
                            String linea;
                            while((linea = br.readLine()) != null){
                                listaNumeros.add(linea);
                            }
                        }
                        out.writeInt(listaNumeros.size());
                        for(String l : listaNumeros){
                            out.writeUTF(l);
                        }
                        break;

                    case 4:
                        int numLin = 0;
                        try(BufferedReader br = new BufferedReader(new FileReader(file))){
                            String linea;
                            while((linea = br.readLine()) != null){
                                String [] lineas= linea.split(":");
                                String nombre = lineas[0];
                                if(nombre.equals(nombreCliente)){
                                    numLin++;
                                }
                            }
                        }
                        out.writeInt(numLin);
                        break;

                    case 5:
                        ArrayList<String> lineasCliente = new ArrayList<>();
                        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                            String linea;
                            while ((linea = br.readLine()) != null) {
                                String[] partes = linea.split(":");
                                if (partes.length >= 2) {
                                    String nombre = partes[0].trim();
                                    if (nombre.equals(nombreCliente)) {
                                        lineasCliente.add(linea);
                                    }
                                }
                            }
                        }

                        out.writeInt(lineasCliente.size());
                        for (String l : lineasCliente) {
                            out.writeUTF(l);
                        }
                        break;

                    case 6:

                        // 1) Nombre
                        out.writeUTF("Indique el nombre:");
                        String nombreP = in.readUTF();

                        // 2) Edad
                        out.writeUTF("Indique la edad:");
                        int edadP = in.readInt();

                        // 3) Descripción
                        out.writeUTF("Indique una descripción:");
                        String descP = in.readUTF();

                        // 4) Crear persona
                        Persona persona = new Persona(nombreP, edadP, descP);

                        // 5) Enviar el objeto
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        oos.writeObject(persona);
                        oos.flush();

                        out.writeUTF("Persona enviada correctamente.");
                        break;

                    case 7:
                        socket.close();
                        System.out.println("Conexión cerrada con " + nombreCliente);
                        return;

                    default:
                        out.writeUTF("Solo números del 1 al 7");
                }
            }
        } catch (IOException e) {
            System.out.println("Error en ServidorHilo con " + nombreCliente + ": " + e.getMessage());
        }
    }
}
