import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class lecturaXML2 {
    public static void main(String[] args) {
        try {
            //Crear el parser
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            //Leemos el documento y lo normalizamos para limpiar espacios
            Document doc = db.parse(new File("ej.xml"));
            doc.getDocumentElement().normalize();

            //  Obtener la lista de personas
            NodeList lista = doc.getElementsByTagName("Compras");
            System.out.println("Número de compras: " + lista.getLength());

            //Añadimos a un NodeList todos los elementos
            NodeList list = doc.getElementsByTagName("Compra");
            for (int i = 0; i < list.getLength(); i++) {
                Node nodo = list.item(i);

                Element compra = (Element) nodo;
                String id = compra.getAttribute("id");
                String fecha = compra.getElementsByTagName("fecha").item(0).getTextContent();
                String ubicacion = compra.getElementsByTagName("ubicacion").item(0).getTextContent();

                System.out.println("Id: " + id);
                System.out.println("Fecha: " + fecha);
                System.out.println("Ubicación: " + ubicacion);


                NodeList lista2 = compra.getElementsByTagName("articulo");

                for (int j = 0; j < lista2.getLength(); j++) {
                    Element articulos = (Element) lista2.item(j);
                    String nombre =  articulos.getAttribute("nombre");
                    String precio =  articulos.getAttribute("precio");

                    System.out.println("Nombre: " + nombre);
                    System.out.println("Precio: " + precio);
                }
            }




        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
