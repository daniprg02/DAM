import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PracticaBDEmbebida {
    private static final String URL = "jdbc:sqlite:BD.db";

    public static void main(String[] args) {

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {

            System.out.println("Creando la base de datos");

            String tablaClientes = "CREATE TABLE IF NOT EXISTS CLIENTES (" +
                    "ID INT PRIMARY KEY, " +
                    "NOMBRE VARCHAR(25) NOT NULL, " +
                    "EMAIL VARCHAR(25) NOT NULL, " +
                    "TELEFONO INT)";

            stmt.executeUpdate(tablaClientes);
            System.out.println("Tabla CLIENTES creada correctamente.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
