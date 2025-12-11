package org.example;

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

public class bibliotecaDOM {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int opcion = 0;

        //Obtenemos el XML
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = db.parse("biblioteca.xml");
        doc.getDocumentElement().normalize();


        do {

            System.out.println("Bienvenido a la biblioteca, ¿Qué desea?");
            System.out.println("""
                    1. Listar libros
                    2. Buscar Libro por categoría
                    3. Añadir libro a la biblioteca
                    4. Eliminar libro de la biblioteca
                    5. Buscar libros por fecha publicada
                    6. Añadir un nuevo prestamo a un libro
                    7. Salir
                    """);
            opcion = sc.nextInt();
            switch (opcion) {
                case 1:
                    listarLibros(doc, sc);
                    break;

                case 2:
                    buscarLibroPorCategoria(doc, sc);
                    break;

                case 3:
                    anadirLibroABiblio(doc, sc);
                    break;

                case 4:
                    eliminarLibroBiblio(doc, sc);
                    break;

                case 5:
                    buscarLibroPorFecha(doc, sc);
                    break;

                case 6:
                    prestarUnLibro(doc, sc);
                    break;

                case 7:
                    break;

                default:
                    System.out.println("Opción no válida");
            }


        } while (opcion != 7);


        //Creamos un nuevo archivo que tendrá toda la información actualizada
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.INDENT, "yes"); //Para que el XML nuevo no salga todo en una sola línea
        t.transform(new DOMSource(doc), new StreamResult("biblioModificado.xml"));
    }

    private static void prestarUnLibro(Document doc, Scanner sc) {
        sc.nextLine();
        System.out.println("Indique el código del libro a prestar:");
        String codigo = sc.nextLine();

        NodeList libros = doc.getElementsByTagName("libro");

        for (int i = 0; i < libros.getLength(); i++) {
            Element libro = (Element) libros.item(i);

            if (libro.getAttribute("codigo").equals(codigo)) {

                // Obtener o crear el contenedor <prestamos>
                NodeList listaPrestamos = libro.getElementsByTagName("prestamos");
                Element prestamos;

                if (listaPrestamos.getLength() == 0) {
                    prestamos = doc.createElement("prestamos");
                    libro.appendChild(prestamos);
                } else {
                    prestamos = (Element) listaPrestamos.item(0);
                }

                // Crear préstamo
                Element prestamo = doc.createElement("prestamo");

                String fechaHoy = java.time.LocalDate.now().toString();

                prestamo.setAttribute("fecha_salida", fechaHoy);
                prestamo.setAttribute("fecha_devolucion", ""); // vacío → no devuelto

                prestamos.appendChild(prestamo);

                System.out.println("Préstamo registrado correctamente.");
            }
        }
    }


    private static void buscarLibroPorFecha(Document doc, Scanner sc) {
        sc.nextLine();
        System.out.println("Indique el año de publicación:");
        String anio = sc.nextLine();

        NodeList libros = doc.getElementsByTagName("libro");
        boolean encontrado = false;

        for (int i = 0; i < libros.getLength(); i++) {
            Element libro = (Element) libros.item(i);
            Element publi = (Element) libro.getElementsByTagName("publicacion").item(0);

            String anioPub = publi.getElementsByTagName("anio").item(0).getTextContent();

            if (anioPub.equals(anio)) {
                encontrado = true;
                System.out.println("Código: " + libro.getAttribute("codigo"));
                System.out.println("Título: " + libro.getElementsByTagName("titulo").item(0).getTextContent());
                System.out.println("----------------------------------");
            }
        }

        if (!encontrado) System.out.println("No se encontraron libros en ese año.");
    }


    private static void eliminarLibroBiblio(Document doc, Scanner sc) {
        sc.nextLine();
        System.out.println("Indique el código del libro que desea eliminar:");
        String codigo = sc.nextLine();

        NodeList libros = doc.getElementsByTagName("libro");

        for (int i = 0; i < libros.getLength(); i++) {
            Element libro = (Element) libros.item(i);

            if (libro.getAttribute("codigo").equals(codigo)) {
                libro.getParentNode().removeChild(libro);
                System.out.println("Libro eliminado correctamente.");
                return;
            }
        }

        System.out.println("No se encontró ningún libro con ese código.");
    }


    private static void anadirLibroABiblio(Document doc, Scanner sc) {

        sc.nextLine(); // limpiar buffer

        System.out.println("Indique la categoría");
        String categoria = sc.nextLine();

        System.out.println("Indique el código del libro");
        String codigo = sc.nextLine();

        System.out.println("Indique el título del libro");
        String titulo = sc.nextLine();

        System.out.println("Indique el autor del libro");
        String autor = sc.nextLine();

        System.out.println("Indique el año de publicación del libro");
        String anio = sc.nextLine();

        System.out.println("Indique la editorial del libro");
        String editorial = sc.nextLine();


        // ==========================================================
        // 1. Buscar sección existente con esa categoría
        // ==========================================================
        NodeList secciones = doc.getElementsByTagName("seccion");
        Element seccionEncontrada = null;

        for (int i = 0; i < secciones.getLength(); i++) {
            Element sec = (Element) secciones.item(i);
            if (sec.getAttribute("categoria").equalsIgnoreCase(categoria)) {
                seccionEncontrada = sec;
                break;
            }
        }

        // Si NO existe la sección, la creamos
        if (seccionEncontrada == null) {
            seccionEncontrada = doc.createElement("seccion");
            seccionEncontrada.setAttribute("categoria", categoria);
            doc.getDocumentElement().appendChild(seccionEncontrada);
        }


        // ==========================================================
        // 2. Crear nodo <libro>
        // ==========================================================
        Element libro = doc.createElement("libro");
        libro.setAttribute("codigo", codigo);


        // titulo
        Element tituloLibro = doc.createElement("titulo");
        tituloLibro.setTextContent(titulo);

        // autor
        Element autorLibro = doc.createElement("autor");
        autorLibro.setTextContent(autor);

        // publicacion -> anio + editorial
        Element publicacion = doc.createElement("publicacion");

        Element anioPub = doc.createElement("anio");
        anioPub.setTextContent(anio);

        Element editorialLibro = doc.createElement("editorial");
        editorialLibro.setTextContent(editorial);

        publicacion.appendChild(anioPub);
        publicacion.appendChild(editorialLibro);


        // ==========================================================
        // 3. Añadir elementos al libro
        // ==========================================================
        libro.appendChild(tituloLibro);
        libro.appendChild(autorLibro);
        libro.appendChild(publicacion);


        // ==========================================================
        // 4. Añadir <libro> a la seccion correcta
        // ==========================================================
        seccionEncontrada.appendChild(libro);

        System.out.println("Libro añadido correctamente a la categoría '" + categoria + "'");
    }


    private static void buscarLibroPorCategoria(Document doc, Scanner sc) {
        sc.nextLine(); // Limpia buffer
        System.out.println("Indique la categoría de los libros que desea buscar:");
        String categoria = sc.nextLine();

        NodeList secciones = doc.getElementsByTagName("seccion");
        boolean encontrado = false;

        for (int i = 0; i < secciones.getLength(); i++) {
            Element seccionActual = (Element) secciones.item(i);

            if (seccionActual.getAttribute("categoria").equalsIgnoreCase(categoria)) {

                NodeList libros = seccionActual.getElementsByTagName("libro");

                System.out.println("\nLibros de la categoría '" + categoria + "':");
                System.out.println("------------------------------------------");

                for (int j = 0; j < libros.getLength(); j++) {
                    Element libro = (Element) libros.item(j);

                    System.out.println("Código: " + libro.getAttribute("codigo"));
                    System.out.println("Título: " + libro.getElementsByTagName("titulo").item(0).getTextContent());
                    System.out.println("Autor: " + libro.getElementsByTagName("autor").item(0).getTextContent());
                    System.out.println("------------------------------------------");
                }

                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("No se encontraron libros en la categoría indicada.");
        }
    }


    private static void listarLibros(Document doc, Scanner sc) {

        NodeList libros = doc.getElementsByTagName("libro");

        for (int i = 0; i < libros.getLength(); i++) {

            Element libro = (Element) libros.item(i);
            Element seccion = (Element) libro.getParentNode(); // Sección del libro

            System.out.println("Código: " + libro.getAttribute("codigo"));
            System.out.println("Título: " + libro.getElementsByTagName("titulo").item(0).getTextContent());
            System.out.println("Autor: " + libro.getElementsByTagName("autor").item(0).getTextContent());

            // Año de publicación
            Element publi = (Element) libro.getElementsByTagName("publicacion").item(0);
            System.out.println("Año publicación: " + publi.getElementsByTagName("anio").item(0).getTextContent());

            // Categoría
            System.out.println("Categoría: " + seccion.getAttribute("categoria"));

            // Prestamos
            NodeList prestamos = libro.getElementsByTagName("prestamo");
            int contadorPrestamos = prestamos.getLength();
            boolean disponible = true; // Un libro está disponible si no hay un préstamo sin devolver

            // Recorremos préstamos para ver si alguno NO tiene fecha_devolucion
            for (int j = 0; j < prestamos.getLength(); j++) {
                Element prestamo = (Element) prestamos.item(j);
                String fechaDevolucion = prestamo.getAttribute("fecha_devolucion");

                // Si NO tiene fecha_devolucion → libro no disponible
                if (fechaDevolucion == null || fechaDevolucion.isEmpty()) {
                    disponible = false;
                }
            }

            System.out.println("Disponible: " + disponible);
            System.out.println("Número de préstamos realizados: " + contadorPrestamos);
            System.out.println("==================================");
        }
    }
}
