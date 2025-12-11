//DANIEL ESTEBAN PÉREZ SUAREZ

package org.example;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String URL = "jdbc:mysql://localhost:3306/examen";
        String USER = "root";
        String PASSWORD = "";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            System.out.println("Bienvenido al examen ");

            System.out.println("""
                    1 - Apartado: A
                    2 - Apartado: B
                    3 - Apartado: C
                    4 - Apartado: D
                    5 - Apartado: E
                    
                    """);

            int opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    crearTablas(conn);
                    break;

                case 2:
                    insertarDatos(conn);
                    break;

                case 3:
                    insertarAlumnCursos(conn);
                    break;

                case 4:
                    DatosDeLaTabla(conn);
                    break;

                case 5:
                    transacciones(conn);
                    break;

                default:
                    System.out.println("Opción no válida");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private static void transacciones(Connection conn) throws SQLException {
        System.out.println("Bienvenido a las transacciones");
        Savepoint savepoint = null;

        conn.setAutoCommit(false);

        String insertarCurso = "INSERT INTO curso (idCurso, nombre, horas) VALUES (33, 'Inglés', 80)";

        try {
            try (PreparedStatement stmt = conn.prepareStatement(insertarCurso)) {
                stmt.executeUpdate();
                System.out.println("Curso insertado");
            }

            savepoint = conn.setSavepoint();

            String insertarAlumno1 = "INSERT INTO alumno (idAlumno, nombre, edad, notaMedia, idCurso) VALUES (1, 'Pepe', 20, 7.8, 1)";
            String insertarAlumno2 = "INSERT INTO alumno (idAlumno, nombre, edad, notaMedia, idCurso) VALUES (2, 'Pepa', 21, 6.1, 2)";
            String insertarAlumno3 = "INSERT INTO alumno (idAlumno, nombre, edad, notaMedia, idCurso) VALUES (3, 'Pepito', 20, 7.8, 31)";

            try (PreparedStatement stmt = conn.prepareStatement(insertarAlumno1)) {
                stmt.executeUpdate();
                System.out.println("Alumno 1 insertado");
            }
            try (PreparedStatement stmt2 = conn.prepareStatement(insertarAlumno2)) {
                stmt2.executeUpdate();
                System.out.println("Alumno 2 insertado");
            }
            try (PreparedStatement stmt3 = conn.prepareStatement(insertarAlumno3)) {
                stmt3.executeUpdate();
                System.out.println("Alumno 3 insertado");
            }

            System.out.println("Curso con los alumnos insertados correctamente");
            conn.commit();

        } catch (SQLException e) {
            System.out.println("Error al insertar alumno");
            if (savepoint != null) {
                conn.rollback(savepoint);
                conn.commit();
                System.out.println("Curso sin alumnos insertado");
            }

        } finally {
            conn.setAutoCommit(true);
        }

    }

    private static void DatosDeLaTabla(Connection conn) {
        try {
            DatabaseMetaData meta = conn.getMetaData();

            ResultSet tablas = meta.getTables(null, null, "%", new String[]{"TABLE"});

            while (tablas.next()) {
                String tabla = tablas.getString("TABLE_NAME");
                if (tabla.equalsIgnoreCase("curso") || tabla.equalsIgnoreCase("alumno")) {
                    System.out.println("Tabla: " + tabla);

                    ResultSet columnas = meta.getColumns(null, null, tabla, null);
                    while (columnas.next()) {
                        System.out.println("Columna: " + columnas.getString("COLUMN_NAME")
                                + " - Tipo: " + columnas.getString("TYPE_NAME")
                                + " - Admite NULL: " + columnas.getString("IS_NULLABLE"));
                    }

                    ResultSet pk = meta.getPrimaryKeys(null, null, tabla);
                    while (pk.next()) {
                        System.out.println("Clave primaria: " + pk.getString("COLUMN_NAME"));
                    }
                    System.out.println();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void insertarAlumnCursos(Connection conn) throws SQLException {
        String sql1 = "INSERT INTO curso (idCurso, nombre, horas) VALUES (1, 'Programación', 1000)";
        String sql2 = "INSERT INTO curso (idCurso, nombre, horas) VALUES (2, 'AccesoDatos', 750)";
        String sql3 = "INSERT INTO alumno (idAlumno, nombre, edad, notaMedia, idCurso) VALUES (91, 'Daniel', 31, 5.75, 2)";
        String sql4 = "INSERT INTO alumno (idAlumno, nombre, edad, notaMedia, idCurso) VALUES (92, 'Ana', 20, 7.5, 1)";

        try (PreparedStatement ps = conn.prepareStatement(sql1)) {
            ps.executeUpdate();
            System.out.println("Tabla curso creada");
        }
        try (PreparedStatement ps2 = conn.prepareStatement(sql2)) {
            ps2.executeUpdate();
            System.out.println("Tabla curso creada");
        }
        try (PreparedStatement ps3 = conn.prepareStatement(sql3)) {
            ps3.executeUpdate();
            System.out.println("Tabla alumno creada");
        }
        try (PreparedStatement ps4 = conn.prepareStatement(sql4)) {
            ps4.executeUpdate();
            System.out.println("Tabla alumno creada");
        }

        String consulta = "SELECT a.nombre AS nombreA, a.edad, a.notaMedia, c.nombre AS nombreC, c.idCurso " +
                "FROM alumno a JOIN curso c ON a.idCurso = c.idCurso";
        int contador = 1;

        try (PreparedStatement ps = conn.prepareStatement(consulta)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.print("[Alumno " + contador + "] Nombre: " + rs.getString("nombreA"));
                System.out.print(" (" + rs.getInt("edad") + " años ) ");
                System.out.print(" - Nota: " + rs.getDouble("notaMedia"));
                System.out.print(" - Curso: " + rs.getString("nombreC"));
                System.out.print("(id = " + rs.getInt("idCurso") + ")");
                contador++;
                System.out.println();
            }
        }
    }

    private static void insertarDatos(Connection conn) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Bienvenido al insertar datos ");
        System.out.println("Indique el IdCurso de la tabla Curso ->");
        int idCurso = sc.nextInt();
        sc.nextLine();
        System.out.println("Indique el nombre de la tabla Curso ->");
        String nombreCurso = sc.nextLine();
        System.out.println("Indique las horas de la tabla Curso ->");
        int horas = sc.nextInt();
        sc.nextLine();

        String insertar = "INSERT INTO curso (idCurso, nombre, horas) VALUES (?, ?, ?)";
        String busqueda = "SELECT * FROM curso WHERE idCurso = ?";

        try (PreparedStatement ps = conn.prepareStatement(busqueda)) {
            ps.setInt(1, idCurso);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("El idCurso ya existe");
            } else {
                try (PreparedStatement ps2 = conn.prepareStatement(insertar)) {
                    ps2.setInt(1, idCurso);
                    ps2.setString(2, nombreCurso);
                    ps2.setInt(3, horas);

                    int filasActualizadas = ps2.executeUpdate();

                    System.out.println("Filas actualizadas: " + filasActualizadas);
                }
            }
        }
    }

    private static void crearTablas(Connection conn) throws SQLException {

        String insertar1 = "CREATE TABLE if NOT EXISTS Curso(" +
                "idCurso INT PRIMARY KEY NOT NULL, " +
                "nombre VARCHAR(50) NOT NULL, " +
                "horas INT NOT NULL )";

        String insertar2 = "CREATE TABLE if NOT EXISTS Alumno(" +
                "idAlumno INT PRIMARY KEY, " +
                "nombre VARCHAR(50) NOT NULL, " +
                "edad INT, " +
                "notaMedia DOUBLE(6,2), " +
                "idCurso INT, " +
                "CONSTRAINT fk_curso FOREIGN KEY (idCurso) REFERENCES curso(idCurso))";

        try (PreparedStatement ps = conn.prepareStatement(insertar1)) {
            ps.executeUpdate();
            System.out.println("Tabla Curso creada con éxito");
        }

        try (PreparedStatement ps2 = conn.prepareStatement(insertar2)) {
            ps2.executeUpdate();
            System.out.println("Tabla Alumno creada con éxito");
        }

    }
}
