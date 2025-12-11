import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.text.AttributeSet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.lang.classfile.Attribute;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        File archivoXML = new File("libros.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document doc = builder.parse(archivoXML);
        doc.getDocumentElement().normalize();

        NodeList libros = doc.getElementsByTagName("Libros");
        System.out.println("Número de libros: " + libros.getLength());

        //Añadimos a un NodeList todos los elementos
        NodeList libro = doc.getElementsByTagName("libro");
        for (int i = 0; i < libro.getLength(); i++) {
            Node nodo = libro.item(i);

            Element lib = (Element) nodo;
            String id = lib.getAttribute("id");
            String genero = lib.getAttribute("genero");
            String disponible = lib.getAttribute("disponible");

            System.out.println("Id: " + id);
            System.out.println("Genero: " + genero);
            System.out.println("Disponible: " + disponible);

            String titulo = lib.getElementsByTagName("titulo").item(0).getTextContent();
            String autor = lib.getElementsByTagName("autor").item(0).getTextContent();
            String anio = lib.getElementsByTagName("anio_publicacion").item(0).getTextContent();
            String editorial = lib.getElementsByTagName("editorial").item(0).getTextContent();

            System.out.println("Titulo: " + titulo);
            System.out.println("Autor: " + autor);
            System.out.println("Editorial: " + editorial);
            System.out.println("Anio: " + anio);
            System.out.println("Editorial: " + anio);

            NodeList prestamo = lib.getElementsByTagName("prestamos");

            for (int j = 0; j < prestamo.getLength(); j++) {
                Node nodoPrestamo = prestamo.item(j);

                //String fecha =
                //String usuario = nodoPrestamo.getTextContent();
            }



        }

//            AttributeSet set = libro.getAttributes();
//
//            for (int j = 0; j < set.getAttributeCount(); j++) {
//
//                System.out.println(set.getAttribute(j).toString());
//
//            }


//        TransformerFactory transformerFactory =
//                TransformerFactory.newInstance();
//        Transformer transformer = transformerFactory.newTransformer();
//        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//
//        DOMSource source = new DOMSource(doc);
//        StreamResult result = new StreamResult(new File("empleados_modificado.xml"));
//
//        transformer.transform(source, result);

        }
}
