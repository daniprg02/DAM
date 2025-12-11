package org.example;

// ALEJANDRO DANIEL SAHONERO AMPUERO

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.Scanner;

public class examenAlejandro {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        //Obtenemos el XML
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = db.parse("libros.xml");
        doc.getDocumentElement().normalize();

        int op = 0; //Elección del usuario
        do {
            //Mostramos el menu de opciones
            mostarMenu();
            op = Integer.parseInt(sc.nextLine());

            switch (op) {
                case 1:
                    pedirPrestamo(doc, sc);
                    break;
                case 2:
                    devolverLibro(doc, sc);
                    break;
                case 3:
                    anadirLibro(doc, sc);
                    break;
                case 4:
                    eliminarLibro(doc, sc);
                    break;
                case 5:
                    modificarLibro(doc, sc);
                    break;
                case 6:
                    listarLibros(doc);
                    break;
            }

        }while(op != 7); //Cuando el usuario eliga "7" el menu dejará de mostrarse

        //Creamos un nuevo archivo que tendrá toda la información actualizada
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.INDENT, "yes"); //Para que el XML nuevo no salga todo en una sola línea
        t.transform(new DOMSource(doc), new StreamResult("libros_mod.xml"));
    }

    public static void mostarMenu(){
        System.out.println("----- ELIGE UNA OPCIÓN -----");
        System.out.println("1. Pedir prestamo");
        System.out.println("2. Devolver un libro");
        System.out.println("3. Añadir un libro");
        System.out.println("4. Eliminar un libro");
        System.out.println("5. Modificar el título de un libro");
        System.out.println("6. Listar libros");
        System.out.println("7. Salir");
        System.out.print("-> ");
    }

    public static void pedirPrestamo(Document doc, Scanner sc){
        System.out.print("Dígame el ID del libro: ");
        String idBuscado = sc.nextLine();

        Element libroBuscado = buscarLibro(doc, idBuscado);
        if(libroBuscado != null){
            //Si es verdadero, significa que nadie lo ha pedido prestado
            if(libroBuscado.getAttribute("disponible").equals("true")){
                //Cambiamos la disponibilidad a falso
                libroBuscado.setAttribute("disponible", "false");
                //Creamos un elemento prestamo
                Element nuevoPrestamo = doc.createElement("prestamo");
                nuevoPrestamo.setAttribute("fecha", "2025-10-30");
                nuevoPrestamo.setAttribute("usuario", "alejandroSahonero");
                //Añadimos el nuevo prestamo a la lista de prestamos
                libroBuscado.getElementsByTagName("prestamos").item(0).appendChild(nuevoPrestamo);

                System.out.println("Se realizó el préstamo correctamente");
            }else{ //Si esta falso, significa que ya ha sido prestado
                System.out.println("El libro no esta disponible para préstamo");
            }
        }else{
            System.out.println("El libro con ID: "+idBuscado+" no existe");
        }
    }

    public static void devolverLibro(Document doc, Scanner sc){
        System.out.print("Dígame el ID del libro: ");
        String idBuscado = sc.nextLine();

        Element libroBuscado = buscarLibro(doc, idBuscado);
        if(libroBuscado != null){
            //Si no esta disponible significa que se puede devolver
            if(libroBuscado.getAttribute("disponible").equals("false")){
                //Cambiamos la disponibilidad a verdadero
                libroBuscado.setAttribute("disponible", "true");
                doc.getDocumentElement().normalize();

                System.out.println("Se realizó la devolución correctamente");
            }else{ //Si estaba disponible, significa que no ha sido prestado
                System.out.println("NO SE PUEDE DEVOLVER, libro no prestado");
            }
        }else{
            System.out.println("El libro con ID: "+idBuscado+" no existe");
        }
    }

    public static void anadirLibro(Document doc, Scanner sc){
        //Preguntamos los datos
        System.out.print("Dígame el género del libro: ");
        String nGenero = sc.nextLine();
        System.out.print("Dígame el titulo del libro: ");
        String nTitulo = sc.nextLine();
        System.out.print("Dígame el autor del libro: ");
        String nAutor = sc.nextLine();
        System.out.print("Dígame el año de publicación del libro: ");
        String nPublicacion = sc.nextLine();
        System.out.print("Dígame la editorial del libro: ");
        String nEditorial = sc.nextLine();

        //Creamos las etiquetas XML con los datos
        Element nuevoLibro = doc.createElement("libro");
        nuevoLibro.setAttribute("genero", nGenero);
        nuevoLibro.setAttribute("id", "A-"+(int)(Math.random()*100));
        nuevoLibro.setAttribute("disponible", "true");
        Element nuevoTitulo = doc.createElement("titulo");
        nuevoTitulo.setTextContent(nTitulo);
        Element nuevoAutor = doc.createElement("autor");
        nuevoAutor.setTextContent(nAutor);
        Element nuevoPublicacion = doc.createElement("anio_publicacion");
        nuevoPublicacion.setTextContent(nPublicacion);
        Element nuevoEditorial = doc.createElement("editorial");
        nuevoEditorial.setTextContent(nEditorial);
        Element prestamos = doc.createElement("prestamos");

        //Añadimos los nuevos nodos al documento
        nuevoLibro.appendChild(nuevoTitulo);
        nuevoLibro.appendChild(nuevoAutor);
        nuevoLibro.appendChild(nuevoPublicacion);
        nuevoLibro.appendChild(nuevoEditorial);
        nuevoLibro.appendChild(prestamos);
        doc.getElementsByTagName("libros").item(0).appendChild(nuevoLibro);
        doc.getDocumentElement().normalize();

        System.out.println("Libro añadido correctamente");
    }

    public static void eliminarLibro(Document doc, Scanner sc){
        System.out.print("Dígame el ID del libro: ");
        String idBuscado = sc.nextLine();

        Element libroBuscado = buscarLibro(doc, idBuscado);
        if(libroBuscado != null){
            //Eliminamos el libro buscado desde la raiz del XML
            doc.getElementsByTagName("libros").item(0).removeChild(libroBuscado);
            doc.getDocumentElement().normalize();
            System.out.println("Libro eliminado correctamente");
        }else{
            System.out.println("El libro con ID: "+idBuscado+" no existe");
        }
    }

    public static void modificarLibro(Document doc, Scanner sc){
        System.out.print("Dígame el ID del libro: ");
        String idBuscado = sc.nextLine();

        Element libroBuscado = buscarLibro(doc, idBuscado);
        if(libroBuscado != null){
            //Pedimos el titulo
            System.out.print("Dígame el nuevo título: ");
            String nTitulo = sc.nextLine();
            //Modificamos el archivo
            libroBuscado.getElementsByTagName("titulo").item(0).setTextContent(nTitulo);
            doc.getDocumentElement().normalize();

            System.out.println("Libro modificado correctamente");
        }else{
            System.out.println("El libro con ID: "+idBuscado+" no existe");
        }
    }

    public static void listarLibros(Document doc){
        //Guardamos todos los libros
        NodeList listaLibros = doc.getElementsByTagName("libro");
        for(int i = 0; i < listaLibros.getLength(); i++){
            //Por cada libro imprimimos su info
            Element libro = (Element) listaLibros.item(i);
            System.out.println("ID: "+libro.getAttribute("id"));
            System.out.println("\tTitulo: "+libro.getElementsByTagName("titulo").item(0).getTextContent());
            System.out.println("\tAutor: "+libro.getElementsByTagName("autor").item(0).getTextContent());
            System.out.println("\tAño: "+libro.getElementsByTagName("anio_publicacion").item(0).getTextContent());
            System.out.println("\tEditorial: "+libro.getElementsByTagName("editorial").item(0).getTextContent());
            System.out.println("\tGénero: "+libro.getAttribute("genero"));
            System.out.println("\tDisponible: "+libro.getAttribute("disponible"));
            System.out.println("\tPrestamos: ");
            //Guardamos todos los prestamos
            NodeList prestamos = libro.getElementsByTagName("prestamo");
            //Si tiene algun prestamo...
            if(prestamos.getLength() > 0){
                for(int j = 0; j < prestamos.getLength(); j++){
                    //Por cada prestamo imprimimos su info
                    Element prestamo = (Element) prestamos.item(j);
                    System.out.println("\t\t- "+prestamo.getAttribute("fecha")+" ("+prestamo.getAttribute("usuario")+ ")");
                }
            }else{ //Si no tiene prestamos
                System.out.println("\t\t- No tiene prestamos");
            }
            System.out.println("----------------------------");
        }
    }

    public static Element buscarLibro(Document doc, String id){
        //Obtenemos todos los libros
        NodeList listaLibros = doc.getElementsByTagName("libro");

        for(int i = 0; i < listaLibros.getLength(); i++){
            Element libro = (Element) listaLibros.item(i);
            //Si el atributo "id" del libro actual es igual al buscado, se detiene
            if(libro.getAttribute("id").equals(id))
                return libro;
        }
        return null;
    }
}

