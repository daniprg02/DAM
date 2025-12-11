package sistemaMatriculas;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Scanner;

public class JPQL {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int opcion;
        EntityManager em = Persistence.createEntityManagerFactory("miUnidadPersistencia").createEntityManager();

        do {


            System.out.println("""
                    1 - Cursos de un alumno por email
                    2 - Nota media de todos los alumnos sin nulos en matriculas
                    3 - Números de alumnos por curso
                    4 - Alumnos con matrículas sin nota
                    """);

            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    cursosAlumno(sc, em);
                    break;
                case 2:
                    notaMediasSinNulos(sc, em);
                    break;
                case 3:
                    alumnosPorCurso(sc, em);
                    break;
                case 4:
                    alumnosmatriculasSinNota(sc, em);
                    break;


                case 5:
                    System.out.println("Hasta pronto");
                    break;

            }

        } while (opcion != 5);

        em.close();
        sc.close();

    }

    private static void alumnosmatriculasSinNota(Scanner sc, EntityManager em) {


    }

    private static void alumnosPorCurso(Scanner sc, EntityManager em) {


    }

    private static void notaMediasSinNulos(Scanner sc, EntityManager em) {


    }

    private static void cursosAlumno(Scanner sc, EntityManager em) {
    sc.nextLine();  // Consume el salto de línea leftover de nextInt()
    System.out.print("Introduce el email del alumno: ");
    String email = sc.nextLine();

    // Query corregida: Busca directamente por email, sin necesidad de find por ID
    Query q1 = em.createQuery(
        "SELECT a.email, m.nota, c.nombre " +
        "FROM MatriculaJPA m " +  // Entidad principal corregida (case-sensitive)
        "JOIN m.alumno a " +     // Alias 'a' para alumno
        "JOIN m.curso c " +      // Alias 'c' para curso
        "WHERE a.email = :email"  // Filtro por email (más directo)
    );
    q1.setParameter("email", email);  // Setea el parámetro

    List<Object[]> resultados = q1.getResultList();

    if (resultados.isEmpty()) {
        System.out.println("No se encontraron matrículas para el email: " + email);
        return;
    }

    System.out.println("Cursos y notas para " + email + ":");
    for (Object[] row : resultados) {
        // Formatea el output: email (opcional), nota, curso
        System.out.println("Curso: " + row[2] + ", Nota: " + (row[1] != null ? row[1] : "null"));
    }
}
}
