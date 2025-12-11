import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/*
Realiza las siguientes tareas:
    Listar por consola cada pedido con su ID,fecha, estado, nombre del cliente y número de líneas
    Calculo de totales:
        - Para cada <pedido>, si <total @calculado="false">, recalcular sumando cantidad * precio_unitario de cada linea.
        - Actualizar el contenido de <total> con 2 decimales y poner calculado="true".
    Cambio de estado
        - Pedir un pedido al usuario y cambiar su estado a
        Preparación  horno
        Horno  reparto
        Reparto  entregado.
        Si ya está en entregado, no cambiar y mostrar un aviso.
     Elimina todos los pedidos con estado=”entregado” cuya fecha sea anterior a una fecha dada.
     Guardar el DOM en un nuevo fichero.
*/
public class lecturaPizza {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document doc = db.parse(new File("pizza.xml"));
        doc.getDocumentElement().normalize();

        //Obtener la lista de pizzas
        NodeList lista = doc.getElementsByTagName("pedido");
        for (int i = 0; i < lista.getLength(); i++) {
            double total = 0 ;
            Node node = lista.item(i);

            Element pedido = (Element) node;
            String id = pedido.getAttribute("id");
            String fecha = pedido.getAttribute("fecha");
            String estado = pedido.getAttribute("estado");

            System.out.println("ID PEDIDO: "+ id +" FECHA: "+ fecha +" ESTADO: "+ estado);

            NodeList lista2 = pedido.getElementsByTagName("cliente");
            NodeList lista3 = pedido.getElementsByTagName("linea");


            for (int i1 = 0; i1 < lista2.getLength(); i1++) {
                Node node2 = lista2.item(i1);
                Element cliente = (Element) node2;
                String idCliente = cliente.getAttribute("id");
                System.out.println("ID CLIENTE " + idCliente + " NUM LINEAS: " + lista3.getLength());

            }

            NodeList lista4 = pedido.getElementsByTagName("linea");

            for (int i1 = 0; i1 < lista4.getLength(); i1++) {
                Node node3 = lista4.item(i1);
                Element linea = (Element) node3;

                String cantidad = linea.getElementsByTagName("cantidad").item(0).getTextContent();
                String precio = linea.getElementsByTagName("precio_unitario").item(0).getTextContent();

                total = total + Integer.parseInt(cantidad) * Double.parseDouble(precio);

                System.out.println("TOTAL PRECIO: " + total);


            }
            NodeList lista5 = pedido.getElementsByTagName("total");
            for(int i2 = 0; i2 < lista5.getLength(); i2++) {
                Node node5 = lista5.item(i2);
                Element total2 = (Element) node5;
                System.out.println(total);
                String valor = total2.getAttribute("calculado");
                System.out.println("TOTAL VALOR: " + valor);
                if(valor.equals("false")){
                    total2.setTextContent(String.valueOf(total));
                }
                System.out.println(total2.getTextContent());
            }


        }

    }
}
