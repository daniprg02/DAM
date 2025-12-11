package org.example;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class DOMDanielPerezSuarez {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        //Obtenemos el XML
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = db.parse("vehiculos.xml");
        doc.getDocumentElement().normalize();

        int opcion = 0;

        do {
            System.out.println();
            System.out.println("Indique que desea hacer");

            System.out.println("""
                    1 - ALQUILER VEHICULOS
                    2 - DEVOLVER UN VEHICULO
                    3 - AÑADIR UN VEHICULO
                    4 - ELIMINAR UN VEHICULO
                    5 - MODIFICAR UN VEHICULO
                    6 - LISTAR VEHICULOS
                    7 - SALIR
                    """);
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    alquilarVehiculo(doc, sc);
                    break;

                case 2:
                    devolverVehiculo(doc, sc);
                    break;

                case 3:
                    anadirVehiculo(doc, sc);
                    break;

                case 4:
                    eliminarVehiculo(doc, sc);
                    break;

                case 5:
                    modificarModelo(doc, sc);
                    break;

                case 6:
                    listarVehiculos(doc);
                    break;

                case 7:
                    System.out.println("Adiós");
                    break;
            }


        }while(opcion!=7);

        //Creamos un nuevo archivo que tendrá toda la información actualizada
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.INDENT, "yes"); //Para que el XML nuevo no salga t odo en una sola línea
        t.transform(new DOMSource(doc), new StreamResult("vehiculosModificado.xml"));
    }

    private static void modificarModelo(Document doc, Scanner sc) {
        sc.nextLine();
        System.out.println("Indique el ID para modificar el modelo del vehiculo");
        String id = sc.nextLine();
        Element vehiculoBuscado = buscarVehiculo(doc, id);

        if(vehiculoBuscado!=null){
            System.out.println("Vehiculo encontrado");
            System.out.println("Indique el nuevo modelo del vehiculo");
            String modelo = sc.nextLine();
            vehiculoBuscado.getElementsByTagName("modelo").item(0).setTextContent(modelo);
            System.out.println("Cambio realizado");
        } else {
            System.out.println("Vehiculo no encontrado");
        }


    }

    private static void listarVehiculos(Document doc) {

        NodeList vehiculos =  doc.getElementsByTagName("vehiculo");

        for(int i = 0; i < vehiculos.getLength(); i++) {
            Element vehiculo = (Element) vehiculos.item(i);
            String id = vehiculo.getAttribute("id");
            String marca = vehiculo.getElementsByTagName("marca").item(0).getTextContent();
            String modelo = vehiculo.getElementsByTagName("modelo").item(0).getTextContent();
            String tipo = vehiculo.getAttribute("tipo");
            String anio =  vehiculo.getElementsByTagName("anio").item(0).getTextContent();
            String precio =  vehiculo.getElementsByTagName("precio").item(0).getTextContent();
            String disponible = vehiculo.getAttribute("disponible");

            NodeList alquileres =  vehiculo.getElementsByTagName("alquiler");

            String numAlquiler = String.valueOf(alquileres.getLength());

            System.out.println(id + " - " + marca + " " + modelo + " (" + tipo + ") ");
            System.out.println("Año: " + anio + " | " + "Precio: "+ precio + " € | " + " Disponible: " + disponible);
            System.out.println("Alquileres: " + numAlquiler);
            System.out.println("--------------------------------------------------------------------");
        }
    }

    private static void eliminarVehiculo(Document doc, Scanner sc) {

        sc.nextLine();
        System.out.println("Indique el ID del vehículo a eliminar");
        String id = sc.nextLine();

        Element vehiculo = buscarVehiculo(doc, id);
        if(vehiculo!=null){
            vehiculo.getParentNode().removeChild(vehiculo);
            System.out.println("Vehiculo eliminado correctamente");
        } else {
            System.out.println("Vehiculo no encontrado");
        }


    }

    private static void anadirVehiculo(Document doc, Scanner sc) {
        sc.nextLine();
        System.out.println("Ingrese el tipo del vehiculo");
        String tipoVehiculo = sc.nextLine();
        System.out.println("Ingrese la marca del vehículo");
        String marca = sc.nextLine();
        System.out.println("Indique el modelo del vehiculo");
        String modelo = sc.nextLine();
        System.out.println("Ingrese el año del vehiculo");
        String anio = sc.nextLine();
        System.out.println("Indique el precio del vehiculo");
        String precio = sc.nextLine();

        Element nuevoVehiculo = doc.createElement("vehiculo");
        nuevoVehiculo.setAttribute("id", "V-800");
        nuevoVehiculo.setAttribute("tipo", tipoVehiculo);
        nuevoVehiculo.setAttribute("disponible", "true");

        Element marcaNueva = doc.createElement("marca");
        marcaNueva.setTextContent(marca);
        Element modeloNueva = doc.createElement("modelo");
        modeloNueva.setTextContent(modelo);
        Element anioNueva = doc.createElement("anio");
        anioNueva.setTextContent(anio);
        Element precioNueva = doc.createElement("precio");
        precioNueva.setTextContent(precio);
        Element alquileres = doc.createElement("alquileres");

        nuevoVehiculo.appendChild(marcaNueva);
        nuevoVehiculo.appendChild(modeloNueva);
        nuevoVehiculo.appendChild(anioNueva);
        nuevoVehiculo.appendChild(precioNueva);
        nuevoVehiculo.appendChild(alquileres);

        doc.getDocumentElement().appendChild(nuevoVehiculo);

        System.out.println("El vehículo se ha creado correctamente");

    }

    private static void devolverVehiculo(Document doc, Scanner sc) {
        sc.nextLine();
        System.out.println("Indique el ID del vehiculo que quieres devolver");
        String id = sc.nextLine();
        Element vehiculo = buscarVehiculo(doc, id);

        if (vehiculo != null) {
            if (vehiculo.getAttribute("disponible").equals("false")) {
                vehiculo.setAttribute("disponible", "true");

                System.out.println("Vehiculo devuelto, gracias");
            } else {
                System.out.println("NO SE PUEDE DEVOLVER, vehículo no prestado");
            }
        }
    }

    private static void alquilarVehiculo(Document doc, Scanner sc) {

        sc.nextLine();//limpiamos el buffer después de un int
        System.out.println("Escriba el ID del vehiculo");
        String id = sc.nextLine();
        Element vehiculo = buscarVehiculo(doc, id);

        if(vehiculo!=null){
            if(vehiculo.getAttribute("disponible").equals("true")){
                vehiculo.setAttribute("disponible", "false");

                NodeList alquiler = vehiculo.getElementsByTagName("alquileres");
                Element cont =  (Element) alquiler.item(0);
                Element nuevo = doc.createElement("alquiler");
                nuevo.setAttribute("fecha", LocalDate.now().toString());
                nuevo.setAttribute("usuario","Usuario600");
                cont.appendChild(nuevo);
                System.out.println("Vehiculo alquilado");
            } else {
                System.out.println("Vehiculo ya alquilado");
            }
        } else {
            System.out.println("Vehiculo no encontrado");
        }
    }

    //Consejo de Oscar, me voy a crear un buscar vehículos
    private static Element buscarVehiculo(Document doc, String id) {
        NodeList vehiculo = doc.getElementsByTagName("vehiculo");
        for (int i = 0; i < vehiculo.getLength(); i++) {
            Element vehiculoActual = (Element) vehiculo.item(i);
            if (vehiculoActual.getAttribute("id").equals(id)) {
                return vehiculoActual;
            }
        }
        return null;
    }

}
