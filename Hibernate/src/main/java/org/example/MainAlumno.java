package org.example;

import org.example.Alumno;
import jakarta.persistence.*;
import java.util.List;

public class MainAlumno {
    public static void main(String[] args) {
        // 1. Crear la fábrica de EntityManager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");

        // 2. Crear EntityManager
        EntityManager em = emf.createEntityManager();

        try {
            // 3. Iniciar transacción
            EntityTransaction tx = em.getTransaction();
            tx.begin();

            // 4. Crear y guardar un nuevo alumno
            Alumno alumno = new Alumno("Laura Gómez", "laura@example.com");
            em.persist(alumno);

            // 5. Confirmar la transacción
            tx.commit();

            // 6. Consultar todos los alumnos
            List<Alumno> lista = em.createQuery("SELECT a FROM Alumno a", Alumno.class).getResultList();
            lista.forEach(System.out::println);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 7. Cerrar recursos
            em.close();
            emf.close();
        }
    }
}
