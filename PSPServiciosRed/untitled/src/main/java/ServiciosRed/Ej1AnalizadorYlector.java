package ServiciosRed;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class Ej1AnalizadorYlector {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        String url = "";

        System.out.print("Bienvenido, escribe tu URL: ");
        url = sc.nextLine();

        //validar si es una URL válida
        if(!url.startsWith("http://") && !url.startsWith("https://")) {
            System.out.println("No es una URL válida");
            while (true) {
                System.out.print("Vuelve a escribir tu URL: ");
                url = sc.nextLine();
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    break;
                }
            }
        }
            URL urlUsuario = new URL(url);

            System.out.println("Protocolo = " + urlUsuario.getProtocol());
            System.out.println("Host = " + urlUsuario.getHost());
            System.out.println("Puerto = " + urlUsuario.getDefaultPort());
            System.out.println("Ruta = " + urlUsuario.getPath());
            System.out.println("Archivo = " + urlUsuario.getFile());
            System.out.println("Consulta = " + urlUsuario.getQuery());


            URLConnection conexion = urlUsuario.openConnection();

            conexion.setConnectTimeout(5000);
            conexion.setReadTimeout(5000);
            conexion.setRequestProperty("User-Agent", "EjemploRedCompleto/1.0");

            System.out.println("Tipo de contenido: " + conexion.getContentType());
            System.out.println("Longitud del contenido: " + conexion.getContentLength());

            try(BufferedReader br = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                BufferedWriter bw = new BufferedWriter(new FileWriter("resultado.html"));) {


                String linea;
                int contador = 0;
                System.out.println("\n--- Guardando contenido en resultado.html ---");
                while ((linea = br.readLine()) != null && contador < 10) {
                    bw.write(linea);
                    bw.newLine();
                    contador++;
                }
            }

            sc.close();
            
            System.out.println("\nContenido guardado en 'resultado.html'");
        }
        
}
