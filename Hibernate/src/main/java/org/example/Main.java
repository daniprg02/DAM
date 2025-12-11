package org.example;

import jakarta.persistence.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // 1. CREAR EL ENTITY MANAGER FACTORY
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
        EntityManager em = emf.createEntityManager();

        try {
            System.out.println("¡HOLA MUNDO HIBERNATE JPA!");

            // ========== CREAR PRODUCTOS ==========
            System.out.println("\nCREANDO PRODUCTOS...");
            EntityTransaction tx = em.getTransaction();
            tx.begin();

            // Crear algunos productos
            Producto p1 = new Producto("Laptop Gaming", 999.99);
            Producto p2 = new Producto("Mouse Inalámbrico", 25.50);
            Producto p3 = new Producto("Teclado Mecánico", 89.99);

            // Guardar en la base de datos
            em.persist(p1);
            em.persist(p2);
            em.persist(p3);

            tx.commit();
            System.out.println("Productos guardados!");

            // ========== LEER TODOS LOS PRODUCTOS ==========
            System.out.println("\nLEYENDO TODOS LOS PRODUCTOS...");
            List<Producto> productos = em.createQuery("SELECT p FROM ProductoHQL p", Producto.class).getResultList();

            System.out.println("PRODUCTOS EN LA BD:");
            for (Producto p : productos) {
                System.out.println("   - " + p);
            }

            // ========== BUSCAR POR ID ==========
            System.out.println("\nBUSCANDO PRODUCTO POR ID...");
            Producto productoEncontrado = em.find(Producto.class, p1.getId());
            System.out.println("Producto encontrado: " + productoEncontrado);

            // ========== ACTUALIZAR PRODUCTO ==========
            System.out.println("\nACTUALIZANDO PRODUCTO...");
            tx = em.getTransaction();  // IMPORTANTE: crear nueva transacción
            tx.begin();
            productoEncontrado.setPrecio(899.99);
            tx.commit();
            System.out.println("Precio actualizado!");

            // ========== ELIMINAR UN PRODUCTO ==========
            System.out.println("\nELIMINANDO PRODUCTO...");
            tx = em.getTransaction();  //IMPORTANTE: crear nueva transacción
            tx.begin();
            em.remove(productoEncontrado);
            tx.commit();
            System.out.println("Producto eliminado!");

            // ========== MOSTRAR PRODUCTOS FINALES ==========
            System.out.println("\nPRODUCTOS FINALES:");
            List<Producto> productosFinales = em.createQuery("SELECT p FROM ProductoHQL p", Producto.class).getResultList();
            for (Producto p : productosFinales) {
                System.out.println("   - " + p);
            }

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // CERRAR RECURSOS
            em.close();
            emf.close();
            System.out.println("\nRecursos cerrados. ¡Programa terminado!");
        }
    }
}