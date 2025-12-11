package sistemaMatriculas;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import org.example.Alumno;

import java.util.ArrayList;
import java.util.Scanner;

import jakarta.persistence.Query;
import java.util.List;

import static org.hibernate.FetchMode.JOIN;
import static org.hibernate.sql.ast.Clause.WHERE;

public class CRUD {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;
        EntityManager em = Persistence.createEntityManagerFactory("miUnidadPersistencia").createEntityManager();

        do {


            System.out.println("""
                    1 - Alta de nuevo alumno
                    2 - Consulta de un curso por su id
                    3 - Actualizar email de un alumno
                    4 - Eliminar curso sin matrículas
                    """);

            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    altaAlumno(sc, em);
                    break;
                case 2:
                    consultaCurso(sc, em);
                    break;
                case 3:
                    actualizarEmailAlumno(sc, em);
                    break;
                case 4:
                    eliminarCursoSinMatricula(sc, em);
                    break;


                case 5:
                    System.out.println("Hasta pronto");
                    break;

            }

        }while(opcion!=5);

        em.close();
        sc.close();

    }

    private static void eliminarCursoSinMatricula(Scanner sc, EntityManager em) {
        sc.nextLine();  // Consume salto de línea después de nextInt() en main
        System.out.print("Introduce el ID del curso a eliminar: ");
        Long idCurso = sc.nextLong();  // Usa Long, ya que el ID en CursoJPA es Long
        sc.nextLine();

        // Busca el curso
        CursoJPA curso = em.find(CursoJPA.class, idCurso);

        if (curso == null) {
            System.out.println("Error: No se encontró ningún curso con el ID " + idCurso);
            return;
        }

        // Verifica si tiene matrículas (opción robusta: query para contar)
        Query query = em.createQuery("SELECT COUNT(m) FROM MatriculaJPA m WHERE m.curso.id = :idCurso");
        query.setParameter("idCurso", idCurso);
        Long count = (Long) query.getSingleResult();

        if (count > 0) {
            System.out.println("No se puede eliminar: El curso tiene " + count + " matrículas asociadas.");
            return;
        }

        // Si no tiene matrículas, elimina
        em.getTransaction().begin();
        em.remove(curso);
        em.getTransaction().commit();

        System.out.println("Curso eliminado exitosamente (ID: " + idCurso + ", Nombre: " + curso.getNombre() + ")");
    }

    private static void actualizarEmailAlumno(Scanner sc, EntityManager em) {

        sc.nextLine();
        System.out.print("Introduce el id del alumno a actualizar: ");
        int idAlumno = sc.nextInt();
        sc.nextLine();
        AlumnoJPA alumno = em.find(AlumnoJPA.class, idAlumno);

    // Chequeo para null: Si no existe, muestra mensaje y salimos
    if (alumno == null) {
        System.out.println("Error: No se encontró ningún alumno con el ID " + idAlumno);
        return;
    }

    // Ahora es seguro obtener el email antiguo
    String emailAntiguo = alumno.getEmail();

    System.out.print("Introduce el nuevo email: ");
    String emailNuevo = sc.nextLine();
    alumno.setEmail(emailNuevo);

    // Impresiones antes de la transacción
    System.out.println("El email anterior era: " + emailAntiguo);
    System.out.println("El email nuevo es: " + emailNuevo);

    // Transacción
    em.getTransaction().begin();
    em.merge(alumno);  // Merge es correcto para actualizar entidades detached
    em.getTransaction().commit();

    // Impresiones después (opcional, pero confirma que se completó)
    System.out.println("Actualización completada. El email anterior era: " + emailAntiguo);
    System.out.println("El email nuevo es: " + alumno.getEmail());  // Usa alumno.getEmail() para confirmar el cambio
}

    private static void consultaCurso(Scanner sc, EntityManager em) {
        sc.nextLine();
        System.out.print("Introduce el id del curso a consultar: ");
        int idCurso = sc.nextInt();

        CursoJPA curso = em.find(CursoJPA.class, idCurso);
        if(curso!=null){
            System.out.println("El id correspondiente al curso es: " + curso.getNombre());
        } else {
            System.out.println("No se encontró ningún curso con ese id");
        }
    }

    private static void altaAlumno(Scanner sc, EntityManager em) {
        sc.nextLine();
        System.out.print("Introduce el email del alumno: ");
        String email = sc.nextLine();
        System.out.print("Indique el nombre del alumno: ");
        String nombre = sc.nextLine();
        AlumnoJPA alumno = new AlumnoJPA();
        alumno.setNombre(nombre);
        alumno.setEmail(email);
        em.getTransaction().begin();
        em.persist(alumno);
        em.getTransaction().commit();
        System.out.println("Alumno creado correctamente");
    }
}
