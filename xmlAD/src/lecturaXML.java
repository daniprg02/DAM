import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class lecturaXML {
    public static void main(String[] args) {
        try {
            // Crear el parser
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            // Leer el archivo XML existente
            Document doc = db.parse(new File("Personas.xml"));
            doc.getDocumentElement().normalize();

            //  Obtener la lista de personas
            NodeList lista = doc.getElementsByTagName("Persona");
            System.out.println("Número de personas: " + lista.getLength());

            // Recorrer los nodos
            for (int i = 0; i < lista.getLength(); i++) {
                Node nodo = lista.item(i);

                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element persona = (Element) nodo;
                    String id = persona.getAttribute("id");
                    String nombre = persona.getElementsByTagName("nombre").item(0).getTextContent();
                    String edad = persona.getElementsByTagName("edad").item(0).getTextContent();

                    System.out.println("Persona ID: " + id);
                    System.out.println("Nombre: " + nombre);
                    System.out.println("Edad: " + edad);
                    System.out.println("---------------------------");
                }
            }

            // Crear una nueva persona
            Element nuevaPersona = doc.createElement("Persona");
            nuevaPersona.setAttribute("id", "3");

            Element nuevoNombre = doc.createElement("nombre");
            nuevoNombre.appendChild(doc.createTextNode("Carlos"));
            nuevaPersona.appendChild(nuevoNombre);

            Element nuevaEdad = doc.createElement("edad");
            nuevaEdad.appendChild(doc.createTextNode("28"));
            nuevaPersona.appendChild(nuevaEdad);

            // Agregarla al documento
            doc.getDocumentElement().appendChild(nuevaPersona);

            // Guardar el nuevo XML
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("personas_nuevo.xml"));
            transformer.transform(source, result);

            System.out.println("Archivo 'personas_nuevo.xml' creado correctamente.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
