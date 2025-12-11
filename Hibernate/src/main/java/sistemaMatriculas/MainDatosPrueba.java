package sistemaMatriculas;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.time.LocalDate;
import java.util.ArrayList;

public class MainDatosPrueba {
    public static void main(String[] args) {
        // Paso 1: Crear la fábrica de EntityManager (usa tu unidad de persistencia)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");

        // Paso 2: Crear EntityManager
        EntityManager em = emf.createEntityManager();

        // Paso 3: Iniciar transacción
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // Paso 4: Crear Alumnos (sin ID, se autogenera)
        AlumnoJPA ana = new AlumnoJPA();
        ana.setNombre("Ana López");
        ana.setEmail("ana.lopez@example.com");
        ana.setMatriculas(new ArrayList<>());  // Inicializa la lista de matrículas

        AlumnoJPA carlos = new AlumnoJPA();
        carlos.setNombre("Carlos Pérez");
        carlos.setEmail("carlos.perez@example.com");
        carlos.setMatriculas(new ArrayList<>());

        AlumnoJPA maria = new AlumnoJPA();
        maria.setNombre("María Sánchez");
        maria.setEmail("maria.sanchez@example.com");
        maria.setMatriculas(new ArrayList<>());

        // Paso 5: Crear Cursos (sin ID, se autogenera)
        CursoJPA java = new CursoJPA();
        java.setNombre("Programación Java");
        java.setCreditos(6);

        CursoJPA basesDatos = new CursoJPA();
        basesDatos.setNombre("Bases de Datos");
        basesDatos.setCreditos(4);

        CursoJPA web = new CursoJPA();
        web.setNombre("Desarrollo Web");
        web.setCreditos(5);

        // Paso 6: Crear Matrículas y asociar (maneja bidireccionalidad)
        // Ana -> Programación Java
        MatriculaJPA mat1 = new MatriculaJPA();
        mat1.setAlumno(ana);
        mat1.setCurso(java);
        mat1.setFechaAlta(LocalDate.of(2024, 9, 1));
        mat1.setNota(8.5);
        ana.getMatriculas().add(mat1);  // Cambia getMatricula a getMatriculas (consistencia)

        // (Haz lo mismo para las otras adiciones: cambia getMatricula() a getMatriculas())

        // Carlos -> Programación Java
        MatriculaJPA mat3 = new MatriculaJPA();
        mat3.setAlumno(carlos);
        mat3.setCurso(java);
        mat3.setFechaAlta(LocalDate.of(2024, 9, 2));
        mat3.setNota(6.0);
        carlos.getMatriculas().add(mat3);

        // Carlos -> Desarrollo Web
        MatriculaJPA mat4 = new MatriculaJPA();
        mat4.setAlumno(carlos);
        mat4.setCurso(web);
        mat4.setFechaAlta(LocalDate.of(2024, 9, 5));
        mat4.setNota(7.5);
        carlos.getMatriculas().add(mat4);

        // María -> Desarrollo Web
        MatriculaJPA mat5 = new MatriculaJPA();
        mat5.setAlumno(maria);
        mat5.setCurso(web);
        mat5.setFechaAlta(LocalDate.of(2024, 9, 4));
        mat5.setNota(9.0);
        maria.getMatriculas().add(mat5);

        // María -> Bases de Datos (nota null)
        MatriculaJPA mat6 = new MatriculaJPA();
        mat6.setAlumno(maria);
        mat6.setCurso(basesDatos);
        mat6.setFechaAlta(LocalDate.of(2024, 9, 6));
        mat6.setNota(null);  // Nota null como pide el enunciado
        maria.getMatriculas().add(mat6);

        // Paso 7: Persistir (persiste alumnos, que cascadearán cursos y matrículas si es necesario)
        // Nota: Persistimos cursos primero porque no tienen cascade desde matrícula
        em.persist(java);
        em.persist(basesDatos);
        em.persist(web);
        em.persist(ana);  // Cascade persistirá sus matrículas
        em.persist(carlos);
        em.persist(maria);

        // Paso 8: Confirmar la transacción
        tx.commit();

        // Paso 9: (Opcional) Verificar en consola con una consulta simple
        System.out.println("Datos insertados exitosamente!");

        // Paso 10: Cerrar recursos
        em.close();
        emf.close();
    }
}
