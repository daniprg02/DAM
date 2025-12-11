import java.sql.*;
import java.util.Scanner;

public class Ej2JBDC {
    private static final String URL = "jdbc:mysql://localhost:3306/basedatos";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        //crearTablasExactas();
        //insertarDatos();
        //consultar();
        //eliminarDatos();
        //actualizarDatos();
        //nuevaTabla();
        AveriguarDep();
    }

    public static void crearTablasExactas() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            System.out.println("=== CREANDO TABLAS ===");

            // 1. Crear tabla Dep
            String crearDep = "CREATE TABLE IF NOT EXISTS Dep (" +
                    "ID INT PRIMARY KEY AUTO_INCREMENT, " +  // ID autoincremental
                    "NumDep VARCHAR(10) NOT NULL, " +        // NumDep como VARCHAR
                    "Nombre VARCHAR(50) NOT NULL, " +
                    "Ala VARCHAR(20), " +
                    "UNIQUE(NumDep))";                       // Para la clave foránea

            stmt.executeUpdate(crearDep);
            System.out.println("Tabla Dep creada: ID(auto), NumDep, Nombre, Ala");

            // 2. Crear tabla Persona
            String crearPersona = "CREATE TABLE IF NOT EXISTS Persona (" +
                    "DNI VARCHAR(10) PRIMARY KEY, " +        // DNI como clave primaria
                    "Nombre VARCHAR(50) NOT NULL, " +
                    "Apellido VARCHAR(50) NOT NULL, " +
                    "Edad INT, " +
                    "NumDep VARCHAR(10), " +                 // Mismo tipo que en Dep
                    "FOREIGN KEY (NumDep) REFERENCES Dep(NumDep))";  // Clave foránea

            stmt.executeUpdate(crearPersona);
            System.out.println("Tabla Persona creada: DNI(PK), Nombre, Apellido, Edad, NumDep(FK)");

            System.out.println("TABLAS LISTAS PARA EJERCICIO 1");

        } catch (SQLException e) {
            System.out.println("Error creando tablas: " + e.getMessage());
        }
    }

    public static void insertarDatos() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            System.out.println("=== INSERTAR DATOS ===");
            //a) Inserta un Departamento con los datos que quieras.
            String insertarDep = "INSERT INTO dep (NumDep, Nombre, Ala) VALUES ('001', 'VENTAS', 'A')";
            String insertarDep2 = "INSERT INTO dep (NumDep, Nombre, Ala) VALUES ('002', 'CONTABILIDAD', 'C')";
            stmt.executeUpdate(insertarDep);
            stmt.executeUpdate(insertarDep2);

            System.out.println("Consulta insertada" + insertarDep);
            System.out.println("Consulta insertada" + insertarDep2);
            //b) Inserta una Persona con los datos que quieras
            try {
                String insertarPer = "INSERT INTO Persona (DNI, Nombre, Apellido, Edad, NumDep) VALUES ('12345678A', 'Juan', 'Pérez', 30, '001')";
                String insertarPer2 = "INSERT INTO Persona (DNI, Nombre, Apellido, Edad, NumDep) VALUES ('33389215Z', 'Maria', 'López', 34, '002')";
                stmt.executeUpdate(insertarPer);
                stmt.executeUpdate(insertarPer2);
                System.out.println("Consulta insertada" + insertarPer);
                System.out.println("Consulta insertada" + insertarPer2);
            } catch (SQLException e) {
                System.out.println("Error insertando persona: " + e.getMessage());
            }
            //INTENTAR INSERTAR PERSONA CON NUMDEP QUE NO EXISTE
            System.out.println("=== INSERTAR PERSONA CON NUMDEP QUE NO EXISTE ===");

            try {
                String insertDepErrror = "INSERT INTO Persona (DNI, Nombre, Apellido, Edad, NumDep) VALUES ('33345678Z', 'Lara', 'Ruiz', 30, '100')";
                stmt.executeUpdate(insertDepErrror);
                System.out.println("Consulta realizada" + insertDepErrror);


            } catch (SQLException e) {
                System.out.println("Error controlado: " + e.getMessage());
                System.out.println("El departamento para insertar el empleado no existe");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //Realiza una consulta que te devuelva el nombre y apellido de las personas
    //que trabajan en el departamento Ventas.

    public static void consultar() {

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            String consultaPersona =
                    "SELECT persona.Nombre, persona.Apellido " +
                            "FROM persona " +
                            "JOIN dep ON persona.NumDep = dep.NumDep " +
                            "WHERE dep.Nombre = 'VENTAS'";

            ResultSet rs = stmt.executeQuery(consultaPersona);

            while (rs.next()) {
                System.out.println(rs.getString("Nombre") + " " + rs.getString("Apellido"));
            }


        } catch (SQLException e) {
            System.out.println("Error insertando datos: " + e.getMessage());
        }
    }

    //e) Elimina los datos con número de departamento 002.

    public static void eliminarDatos() {

        try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement stmt = conn.createStatement()){

            String eliminarDep = "DELETE FROM dep WHERE NumDep = '002'";
            stmt.executeUpdate(eliminarDep);

            System.out.println("Eliminando departamento...");


        } catch  (SQLException e) {
            System.out.println("Error insertando datos: " + e.getMessage());
        }
    }

    //f) Actualiza la edad de todas las personas que son menores de edad a 18
    //años. Y muestre por pantalla el número de filas que han sido afectadas.
    public static void actualizarDatos() {
        Scanner sc = new Scanner(System.in);
        int edad18 = 18;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.println("Vamos a introducir empleados, escriba SALIR para terminar");

            while (true) {

                System.out.println("¿Quiere salir del programa? Escriba SALIR");
                String salirString = sc.nextLine();

                if (salirString.equalsIgnoreCase("SALIR"))
                    break;

                // Insertamos menores
                String insertarMenor =
                        "INSERT INTO persona (DNI, Nombre, Apellido, Edad, NumDep) VALUES (?, ?, ?, ?, ?)";

                System.out.print("Indique DNI: ");
                String dni = sc.nextLine();

                System.out.print("Indique nombre: ");
                String nombre = sc.nextLine();

                System.out.print("Indique apellido: ");
                String apellido = sc.nextLine();

                System.out.print("Indique edad: ");
                int edad = sc.nextInt();
                sc.nextLine(); // limpiar buffer

                System.out.print("Indique num de dep: ");
                String NumDep = sc.nextLine();

                PreparedStatement pst = conn.prepareStatement(insertarMenor);
                pst.setString(1, dni);
                pst.setString(2, nombre);
                pst.setString(3, apellido);
                pst.setInt(4, edad);
                pst.setString(5, NumDep);

                pst.executeUpdate();

                System.out.println("Datos insertados correctamente.");
            }

            // Actualizamos
            try (Statement stmt = conn.createStatement()) {
                String actualizar = "UPDATE persona SET Edad = 18 WHERE Edad < 18";
                int filas = stmt.executeUpdate(actualizar);
                System.out.println("Filas afectadas: " + filas);
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /*Créate una nueva tabla que se llame Teléfonos, que tenga como
    columnas IDPersona y el Teléfono. Siendo como claves ambas columnas
    y IDPersona sea una clave foránea de la columna DNI de la tabla
    persona.*/

    public static void nuevaTabla() {

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            String crearTabla =
                    "CREATE TABLE IF NOT EXISTS telefonos (" +
                            "IDPersona VARCHAR(10)," +
                            "Telefono VARCHAR(20)," +
                            "PRIMARY KEY (IDPersona, Telefono)," +
                            "FOREIGN KEY (IDPersona) REFERENCES Persona(DNI))";

            stmt.executeUpdate(crearTabla);
            System.out.println("Tabla creada correctamente");

            // IDs QUE EXISTAN EN LA TABLA PERSONA
            String[] ids = {"12345678A", "33389215Z", "29299292Z", "203322T"}; // usa DNIs existentes

            String insertarTelefonos =
                    "INSERT INTO telefonos (IDPersona, Telefono) VALUES (?, ?)";

            PreparedStatement pst = conn.prepareStatement(insertarTelefonos);

            for (String id : ids) {

                // Generar un teléfono de 9 cifras
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < 9; i++) {
                    sb.append((int) (Math.random() * 10));
                }

                String telefono = sb.toString();

                pst.setString(1, id);
                pst.setString(2, telefono);

                pst.executeUpdate();
            }

            System.out.println("Teléfonos insertados correctamente.");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /*a) Imagínate que no conoces cuantas filas tiene la tabla Departamento, ni de qué
    tipo son cada uno de las columnas. Averigua de que tipo son, e inserta un
    nuevo valor.*/

    public static void AveriguarDep (){
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            int contFilas = 0;

            String filas = "SELECT * FROM dep";
            ResultSet rs = stmt.executeQuery(filas);
            ResultSetMetaData rsmd = rs.getMetaData();
            int numCols = rsmd.getColumnCount();

            while(rs.next()){
                contFilas++;
            }

            for(int i = 1; i <= numCols; i++){
                System.out.println(rsmd.getColumnName(i) + " tipo --> " + rsmd.getColumnTypeName(i));
            }

            System.out.println("La tabla dep tiene " + contFilas + " filas.");





        } catch (SQLException e) {
        System.out.println("Error: " + e.getMessage());
    }


    }







}