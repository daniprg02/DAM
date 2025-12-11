package HQL;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import java.util.List;

public class EjPruebaProf {
    public static void main(String[] args) {
        // Crea la EntityManagerFactory (usa el nombre de tu persistence-unit)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
        EntityManager em = emf.createEntityManager();

        // Inicia una transacción para insertar datos
        em.getTransaction().begin();

        // Crea 3 productos distintos
        ProductoHQL productoHQL1 = new ProductoHQL("Laptop", 999.99, 10);
        ProductoHQL productoHQL2 = new ProductoHQL("Smartphone", 499.99, 25);
        ProductoHQL productoHQL3 = new ProductoHQL("Auriculares", 49.99, 50);

        // Persiste los productos en la DB
        em.persist(productoHQL1);
        em.persist(productoHQL2);
        em.persist(productoHQL3);

        // Commitea la transacción (guarda los cambios)
        em.getTransaction().commit();

        // Consulta y recorre los productos
        List<ProductoHQL> productoHQLS = consultarProductos(em);

        // For-each para recorrer e imprimir
        System.out.println("Lista de productos:");
        for (ProductoHQL p : productoHQLS) {
            System.out.println(p);
        }

        // Update

        em.getTransaction().begin();
        ProductoHQL primerProducto = (ProductoHQL) consultarProductos(em).get(0);
        primerProducto.setStock(100);
        em.merge(primerProducto);
        em.getTransaction().commit();

        System.out.println("DESPUÉS DEL UPDATE: ");
        consultarProductos(em).forEach(System.out::println);

        //DELETE
        em.getTransaction().begin();
        ProductoHQL segundoProducto = (ProductoHQL) consultarProductos(em).get(1);
        em.remove(segundoProducto);
        em.getTransaction().commit();
        System.out.println("DESPUÉS DEL DELETE:");

        consultarProductos(em).forEach(System.out::println);

        // Cierra recursos
        em.close();
        emf.close();
    }

    // Método con consulta HQL para obtener todos los productos
    private static List consultarProductos(EntityManager em) {
        String hql = "FROM ProductoHQL";  // Consulta HQL simple
        Query query = em.createQuery(hql);
        return query.getResultList();
    }
}
