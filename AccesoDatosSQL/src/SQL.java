import java.sql.*;

public class SQL {
    private static final String URL = "jdbc:mysql://localhost:3306/basedatos";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {

        try(Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement stmt = conexion.createStatement()) {

            System.out.println("Crear tablas");

            String tablaClientes = "CREATE TABLE IF NOT EXISTS CLIENTES (" +
                    "ID INT PRIMARY KEY, " +
                    "NOMBRE VARCHAR(25) NOT NULL, " +
                    "EMAIL VARCHAR(25) NOT NULL, " +
                    "TELEFONO INT)";

            stmt.executeUpdate(tablaClientes);
            System.out.println("Tabla CLIENTES creada correctamente.");

            String insertarClientes1 = "INSERT INTO CLIENTES (ID, NOMBRE, EMAIL, TELEFONO) VALUES ('001', 'Daniel Pérez', 'daniprg02@gmail.com', '6666666')";
            String insertarClientes2 = "INSERT INTO CLIENTES (ID, NOMBRE, EMAIL, TELEFONO) VALUES ('002', 'Pepito Oteiza', 'pepito1001@gmail.com', '000999999')";
            String insertarClientes3 = "INSERT INTO CLIENTES (ID, NOMBRE, EMAIL, TELEFONO) VALUES ('003', 'Lucía Martinez', 'gatubelia@gmail.com', '99911134')";
            stmt.executeUpdate(insertarClientes1);
            stmt.executeUpdate(insertarClientes2);
            stmt.executeUpdate(insertarClientes3);

            String consultaClientes =  "SELECT * FROM CLIENTES";
            ResultSet resul = stmt.executeQuery(consultaClientes);

            while (resul.next()) {
                System.out.printf("%d, %s, %s %n", resul.getInt(1),
                        resul.getString(2), resul.getString(3), resul.getInt(4));

            }
            resul.close(); // Cerrar ResultSet
            stmt.close(); // Cerrar Statement


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
